package com.shopwell.api.controller;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.VOs.request.ProductRegistrationVO;
import com.shopwell.api.model.VOs.request.ProductSearchRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.ProductSearchResponseVO;
import com.shopwell.api.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
@Tag(name = "Product")
@SecurityRequirement(name = "Bearer Authentication")
public final class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "This is an endpoint for saving a product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product saved",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid / malformed request"),
            @ApiResponse(responseCode = "403", description = "Invalid / expired token"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)

    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseVO<?>> saveProduct(@Parameter(description = "Product to save") @Validated @RequestBody final ProductRegistrationVO registrationVO) {
        log.info("Saving product: " + registrationVO);
        ApiResponseVO<?> response = new ApiResponseVO<>(productService.saveProduct(registrationVO));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management GET endpoint",
            responses = {
                    @ApiResponse(description = "Product saved successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Product not found", responseCode = "404")
            }
    )
    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseVO<?>> editProduct(@Parameter(description = "Product to edit") @PathVariable("productId") final Long productId, @RequestBody final ProductRegistrationVO registrationVO) throws ProductNotFoundException {
        log.info("Editing product: " + registrationVO);
        ApiResponseVO<?> response = new ApiResponseVO<>(productService.editProduct(productId, registrationVO));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "This is an endpoint for deleting a product.",
            responses = {
                    @ApiResponse(description = "Product deleted successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Product not found", responseCode = "404")
            }
    )
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseVO<?>> deleteProduct(@Parameter(description = "Product to delete") @PathVariable("productId") final Long productId) throws ProductNotFoundException {
        log.info("Deleting product with id: " + productId);
        ApiResponseVO<?> response = new ApiResponseVO<>(productService.deleteProduct(productId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "This is an endpoint for getting a product by ID.",
            responses = {
                    @ApiResponse(description = "Product found", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Product not found", responseCode = "404")
            }
    )
    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponseVO<?>> getProductById(@Parameter(description = "Product to get by ID") @PathVariable("productId") final Long productId) throws ProductNotFoundException {
        log.info("Getting product with id: " + productId);
        ApiResponseVO<?> response = new ApiResponseVO<>(productService.getProduct(productId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "This is an endpoint for getting a page of products.",
            responses = {
                    @ApiResponse(description = "Products found", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Product not found", responseCode = "404")
            }
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponseVO<?>> getProducts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                        @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        log.info("Getting products");
        ApiResponseVO<?> response = new ApiResponseVO<>(productService.getProducts(pageNumber, pageSize));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "This is an endpoint for searching products."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductSearchResponseVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid / malformed request"),
            @ApiResponse(responseCode = "403", description = "Invalid / expired token"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)

    })
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponseVO<?>> searchProducts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                           @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                           @RequestBody ProductSearchRequestVO productSearchRequestVO) {
        log.info("Searching for product: " + productSearchRequestVO);
        ApiResponseVO<ProductSearchResponseVO> response = productService.searchProductsByCriteria(pageNumber, pageSize, productSearchRequestVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
