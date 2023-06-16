package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.DTOs.request.ProductRegistrationVO;
import com.shopwell.api.model.DTOs.response.ApiResponseVO;
import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.model.entity.CartItem;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.repository.BrandRepository;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    @Override
    public ApiResponseVO<?> saveProduct(ProductRegistrationVO productRegistrationVO) {
        try {
            productRepository.save(toProductEntity(productRegistrationVO));
            return ApiResponseVO.builder()
                    .message("Product saved successfully")
                    .payload(productRegistrationVO)
                    .build();
        } catch (Exception e) {
            return ApiResponseVO.builder()
                    .message("Product not saved successfully")
                    .error(e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponseVO<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) throws ProductNotFoundException {
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
                .payload(toProductVO(savedProduct))
                .build();
    }

    @Override
    public ApiResponseVO<?> getProduct(Long id) throws ProductNotFoundException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ApiResponseVO.builder()
                .message("Product saved successfully")
                .payload(toProductVO(foundProduct))
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
    public ApiResponseVO<List<ProductRegistrationVO>> searchProducts(String keyword) {
        return null;
    }

    @Override
    public String addProductToCart(Long productId, Long customerId) {
        return null;
    }

    @Override
    public String removeProductFromCart(Long productId, Long customerId) {
        return null;
    }

    @Override
    public List<CartItem> getCartItems(Long customerId) {
        return null;
    }

    @Override
    public BigDecimal calculateTotalPrice(Long customerId) {
        return null;
    }

    private Product toProductEntity(ProductRegistrationVO productRegistrationVO) {
        Brand brand = brandRepository.findByBrandName(productRegistrationVO.getBrandName());
        return Product.builder()
                .productName(productRegistrationVO.getProductName())
                .productDescription(productRegistrationVO.getProductDescription())
                .productPrice(productRegistrationVO.getProductPrice())
                .brand(brand)
                .build();
    }

    private ProductRegistrationVO toProductVO(Product product) {
        return ProductRegistrationVO.builder()
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .brandName(product.getBrand().getBrandName())
                .build();
    }
}
