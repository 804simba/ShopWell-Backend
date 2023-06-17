package com.shopwell.api.controller;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.VOs.request.ProductSearchRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductResponseVO;
import com.shopwell.api.model.VOs.response.ProductSearchResponseVO;
import com.shopwell.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public final class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponseVO<?>> saveProduct(final ProductRegistrationVO registrationVO) {
        ApiResponseVO<?> response = new ApiResponseVO<>("Saved successfully", productService.saveProduct(registrationVO));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponseVO<?>> editProduct(final Long productId, final ProductRegistrationVO registrationVO) throws ProductNotFoundException {
        ApiResponseVO<?> response = new ApiResponseVO<>("Saved successfully", productService.editProduct(productId, registrationVO));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseVO<?>> deleteProduct(@PathVariable("productId") final Long productId) throws ProductNotFoundException {
        ApiResponseVO<?> response = new ApiResponseVO<>("Deleted successfully", productService.deleteProduct(productId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseVO<?>> searchProducts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                           @RequestBody ProductSearchRequestVO productSearchRequestVO) {
        ApiResponseVO<ProductSearchResponseVO> response = productService.searchProductsByCriteria(pageNumber, pageSize, productSearchRequestVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
