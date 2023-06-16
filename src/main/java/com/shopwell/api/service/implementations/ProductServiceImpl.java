package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.DTOs.request.CartItemVO;
import com.shopwell.api.model.DTOs.request.ProductRegistrationVO;
import com.shopwell.api.model.DTOs.response.ApiResponseVO;
import com.shopwell.api.model.entity.Brand;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.repository.BrandRepository;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.service.CartService;
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

    private final CartService cartService;

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
    public String addProductToCart(Long productId, Long customerId, int quantity) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartService.addProductToCart(foundProduct, customerId, quantity);
        return "Product added to cart successfully.";
    }

    @Override
    public String removeProductFromCart(Long productId, Long customerId) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartService.removeProductFromCart(foundProduct, customerId);
        return "Product removed from cart successfully";
    }

    @Override
    public List<CartItemVO> getCartItems(Long customerId) {
        return cartService.getCartItems(customerId);
    }

    @Override
    public BigDecimal calculateTotalPrice(Long customerId) {
        return cartService.calculateTotalPrice(customerId);
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
