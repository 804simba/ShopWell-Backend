package com.shopwell.api.service.implementations;

import com.simba.shopwell.exceptions.ProductNotFoundException;
import com.simba.shopwell.model.DTOs.request.ProductVO;
import com.simba.shopwell.model.DTOs.response.ApiResponse;
import com.simba.shopwell.model.entity.Product;
import com.simba.shopwell.repository.ProductRepository;
import com.simba.shopwell.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public ApiResponse<?> saveProduct(ProductVO productVO) {
        try {
            productRepository.save(toProductEntity(productVO));
            return ApiResponse.builder()
                    .message("Product saved successfully")
                    .payload(productVO)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Product not saved successfully")
                    .error(e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse<?> editProduct(Long id, ProductVO productVO) throws ProductNotFoundException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        foundProduct.setProductName(productVO.getProductName());
        foundProduct.setProductDescription(productVO.getProductDescription());
        foundProduct.setProductPrice(productVO.getProductPrice());
        foundProduct.setBrandName(productVO.getBrandName());

        var savedProduct = productRepository.save(foundProduct);

        return ApiResponse.builder()
                .message("Product saved successfully")
                .payload(toProductVO(savedProduct))
                .build();
    }

    @Override
    public ApiResponse<?> getProduct(Long id) throws ProductNotFoundException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ApiResponse.builder()
                .message("Product saved successfully")
                .payload(toProductVO(foundProduct))
                .build();
    }

    @Override
    public ApiResponse<?> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return ApiResponse.builder()
                .message("Product deleted!")
                .build();
    }

    @Override
    public ApiResponse<List<ProductVO>> searchProducts(String keyword) {
        return null;
    }

    private Product toProductEntity(ProductVO productVO) {
        return Product.builder()
                .productName(productVO.getProductName())
                .productDescription(productVO.getProductDescription())
                .productPrice(productVO.getProductPrice())
                .brandName(productVO.getBrandName())
                .build();
    }

    private ProductVO toProductVO(Product product) {
        return ProductVO.builder()
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .brandName(product.getBrandName())
                .build();
    }
}
