package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.VOs.request.*;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductResponseVO;
import com.shopwell.api.model.VOs.response.ProductSearchResponseVO;
import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.repository.BrandRepository;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.service.CartService;
import com.shopwell.api.service.ProductService;
import com.shopwell.api.utils.search.ProductSpecificationBuilder;
import com.shopwell.api.utils.search.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CartService cartService;

    @Override
    public ApiResponseVO<?> saveProduct(ProductRegistrationVO productRegistrationVO) {
        try {
            productRepository.save(mapProductRegistrationVOToProduct(productRegistrationVO));
            return ApiResponseVO.builder()
                    .message("Product saved successfully")
                    .payload(productRegistrationVO)
                    .build();
        } catch (Exception e) {
            return ApiResponseVO.builder()
                    .message("Product not saved successfully")
                    .build();
        }
    }

    @Override
    public ApiResponseVO<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) {
        try {
            Product foundProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            foundProduct.setProductName(productRegistrationVO.getProductName());
            foundProduct.setProductDescription(productRegistrationVO.getProductDescription());
            foundProduct.setProductPrice(productRegistrationVO.getProductPrice());

            Brand brand = brandRepository.findByBrandName(productRegistrationVO.getBrandName());

            foundProduct.setBrand(brand);

            var savedProduct = productRepository.save(foundProduct);

            return ApiResponseVO.builder()
                    .message("Product saved successfully")
                    .payload(mapProductToVO(savedProduct))
                    .build();
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ApiResponseVO<?> getProduct(Long id) throws ProductNotFoundException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ApiResponseVO.builder()
                .message("Product saved successfully")
                .payload(mapProductToVO(foundProduct))
                .build();
    }

    @Override
    public ApiResponseVO<?> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return ApiResponseVO.builder()
                .message("Product deleted!")
                .build();
    }

    @Override
    public ApiResponseVO<ProductSearchResponseVO> searchProductsByCriteria(int pageNumber, int pageSize, ProductSearchRequestVO searchRequestVO) {

        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();

        List<SearchCriteria> criteriaList = searchRequestVO.getSearchCriteriaList();

        if (criteriaList != null) {
            criteriaList.forEach(criteria -> {
                criteria.setDataOption(searchRequestVO.getDataOption());
                builder.with(criteria);
            });
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by("productPrice").ascending()
                        .and(Sort.by("productName")).ascending()
                        .and(Sort.by("brandName")).ascending()
                        .and(Sort.by("categoryName")).ascending());

        Page<Product> productPage = productRepository.findAll(builder.build(), pageable);

        List<ProductResponseVO> products = productPage.getContent().stream()
                .map(this::mapProductToVO)
                .collect(Collectors.toList());

        ProductSearchResponseVO searchResponseVO = new ProductSearchResponseVO(new PageImpl<>(products, pageable, productPage.getTotalPages()));

        return new ApiResponseVO<>("Search results", searchResponseVO);
    }

    @Override
    public String addProductToCart(AddToCartRequestVO addToCartRequestVO) {
        Product foundProduct = productRepository.findById(addToCartRequestVO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return cartService.addProductToCart(foundProduct, addToCartRequestVO.getCustomerId(), addToCartRequestVO.getQuantity());
    }

    @Override
    public String removeProductFromCart(Long productId, Long customerId) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return cartService.removeProductFromCart(foundProduct, customerId);
    }

    @Override
    public List<CartItemVO> getCartItems(Long customerId) {
        return cartService.getCartItems(customerId);
    }

    @Override
    public BigDecimal calculateTotalPrice(Long customerId) {
        return cartService.calculateTotalPrice(customerId);
    }

    private Product mapProductRegistrationVOToProduct(ProductRegistrationVO productRegistrationVO) {
        Brand brand = brandRepository.findByBrandName(productRegistrationVO.getBrandName());
        return Product.builder()
                .productName(productRegistrationVO.getProductName())
                .productDescription(productRegistrationVO.getProductDescription())
                .productPrice(productRegistrationVO.getProductPrice())
                .brand(brand)
                .build();
    }

    private ProductResponseVO mapProductToVO(Product product) {
        return ProductResponseVO.builder()
                .productId(product.getProductNumber())
                .productName(product.getProductName())
                .brandName(product.getBrand().getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .productDescription(product.getProductDescription())
                .productPrice(String.valueOf(product.getProductPrice()))
                .quantityAvailable(product.getQuantityAvailable())
                .build();
    }
}
