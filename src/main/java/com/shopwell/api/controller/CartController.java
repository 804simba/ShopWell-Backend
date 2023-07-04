package com.shopwell.api.controller;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.AddToCartRequestVO;
import com.shopwell.api.model.VOs.request.CartItemVO;
import com.shopwell.api.model.VOs.request.RemoveFromCartRequest;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CartItemResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.service.CartService;
import com.shopwell.api.service.ProductService;
import com.shopwell.api.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
@Tag(name = "Cart")
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "This is an endpoint for adding a product to cart.",
            responses = {
                    @ApiResponse(description = "Product added to cart", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @PostMapping("/add")
    public ResponseEntity<ApiResponseVO<?>> addProductToCart(@RequestBody final AddToCartRequestVO addToCartRequestVO) throws CustomerNotFoundException {
        log.info(String.format("Adding product to cart %s: ", addToCartRequestVO.toString()));
        ApiResponseVO<?> response = new ApiResponseVO<>("Product added to cart successfully", cartService.addProductToCart(addToCartRequestVO));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "This is an endpoint for removing a product from cart.",
            responses = {
                    @ApiResponse(description = "Product removed from cart", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponseVO<?>> removeProductFromCart(@RequestBody final RemoveFromCartRequest removeFromCartRequest) throws CustomerNotFoundException {
        log.info("Removing product from cart: ");
        cartService.removeProductFromCart(removeFromCartRequest.getProductId());
        ApiResponseVO<?> response = new ApiResponseVO<>("Product removed from cart successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "This is an endpoint for getting products in cart.",
            responses = {
                    @ApiResponse(description = "Products found", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponseVO<List<CartItemResponseVO>>> getCartItems() throws CustomerNotFoundException {
        ApiResponseVO<List<CartItemResponseVO>> response = new ApiResponseVO<>(cartService.getCartItems());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
