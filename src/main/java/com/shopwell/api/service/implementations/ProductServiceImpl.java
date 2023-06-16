//package com.shopwell.api.service.implementations;
//
//import com.shopwell.api.exceptions.ProductNotFoundException;
//import com.shopwell.api.model.DTOs.request.ProductRegistrationVO;
//import com.shopwell.api.model.DTOs.response.ApiResponse;
//import com.shopwell.api.model.entity.Product;
//import com.shopwell.api.repository.ProductRepository;
//import com.shopwell.api.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ProductServiceImpl implements ProductService {
//    private final ProductRepository productRepository;
//    @Override
//    public ApiResponse<?> saveProduct(ProductRegistrationVO productRegistrationVO) {
//        try {
//            productRepository.save(toProductEntity(productRegistrationVO));
//            return ApiResponse.builder()
//                    .message("Product saved successfully")
//                    .payload(productRegistrationVO)
//                    .build();
//        } catch (Exception e) {
//            return ApiResponse.builder()
//                    .message("Product not saved successfully")
//                    .error(e.getMessage())
//                    .build();
//        }
//    }
//
//    @Override
//    public ApiResponse<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) throws ProductNotFoundException {
//        Product foundProduct = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//
//        foundProduct.setProductName(productRegistrationVO.getProductName());
//        foundProduct.setProductDescription(productRegistrationVO.getProductDescription());
//        foundProduct.setProductPrice(productRegistrationVO.getProductPrice());
//        foundProduct.setBrandName(productRegistrationVO.getBrandName());
//
//        var savedProduct = productRepository.save(foundProduct);
//
//        return ApiResponse.builder()
//                .message("Product saved successfully")
//                .payload(toProductVO(savedProduct))
//                .build();
//    }
//
//    @Override
//    public ApiResponse<?> getProduct(Long id) throws ProductNotFoundException {
//        Product foundProduct = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//        return ApiResponse.builder()
//                .message("Product saved successfully")
//                .payload(toProductVO(foundProduct))
//                .build();
//    }
//
//    @Override
//    public ApiResponse<?> deleteProduct(Long id) {
//        productRepository.deleteById(id);
//        return ApiResponse.builder()
//                .message("Product deleted!")
//                .build();
//    }
//
//    @Override
//    public ApiResponse<List<ProductRegistrationVO>> searchProducts(String keyword) {
//        return null;
//    }
//
//    private Product toProductEntity(ProductRegistrationVO productRegistrationVO) {
//        return Product.builder()
//                .productName(productRegistrationVO.getProductName())
//                .productDescription(productRegistrationVO.getProductDescription())
//                .productPrice(productRegistrationVO.getProductPrice())
//                .brandName(productRegistrationVO.getBrandName())
//                .build();
//    }
//
//    private ProductRegistrationVO toProductVO(Product product) {
//        return ProductRegistrationVO.builder()
//                .productName(product.getProductName())
//                .productDescription(product.getProductDescription())
//                .productPrice(product.getProductPrice())
//                .brandName(product.getBrandName())
//                .build();
//    }
//}
