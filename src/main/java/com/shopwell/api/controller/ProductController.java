package com.shopwell.api.controller;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.VOs.request.ProductSearchRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductSearchResponseVO;
import com.shopwell.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
public final class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseVO<?>> saveProduct(@Valid @ModelAttribute final ProductRegistrationVO registrationVO) {
        log.info("Saving product: " + registrationVO);
        ApiResponseVO<?> response = new ApiResponseVO<>("null", productService.saveProduct(registrationVO));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponseVO<?>> editProduct(final Long productId, @RequestPart final ProductRegistrationVO registrationVO) throws ProductNotFoundException {
        log.info("Editing product: " + registrationVO);
        ApiResponseVO<?> response = new ApiResponseVO<>("null", productService.editProduct(productId, registrationVO));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseVO<?>> deleteProduct(@PathVariable("productId") final Long productId) throws ProductNotFoundException {
        log.info("Deleting product with id: " + productId);
        ApiResponseVO<?> response = new ApiResponseVO<>("null", productService.deleteProduct(productId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseVO<?>> searchProducts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                           @RequestBody ProductSearchRequestVO productSearchRequestVO) {
        log.info("Searching for product: " + productSearchRequestVO);
        ApiResponseVO<ProductSearchResponseVO> response = productService.searchProductsByCriteria(pageNumber, pageSize, productSearchRequestVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
