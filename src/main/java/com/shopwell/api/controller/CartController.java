package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.AddToCartRequestVO;
import com.shopwell.api.model.VOs.request.CartItemVO;
import com.shopwell.api.model.VOs.request.RemoveFromCartRequest;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CartItemResponseVO;
import com.shopwell.api.service.ProductService;
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
public class CartController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseVO<?>> addProductToCart(@RequestBody final AddToCartRequestVO addToCartRequestVO) {
        log.info(String.format("Adding product to cart %s: ", addToCartRequestVO.toString()));
        ApiResponseVO<?> response = new ApiResponseVO<>("Product added to cart successfully", productService.addProductToCart(addToCartRequestVO));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponseVO<?>> removeProductFromCart(@RequestBody final RemoveFromCartRequest removeFromCartRequest) {
        log.info("Removing product from cart: ");
        productService.removeProductFromCart(removeFromCartRequest.getProductId(), removeFromCartRequest.getCustomerId());
        ApiResponseVO<?> response = new ApiResponseVO<>("Product removed from cart successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseVO<List<CartItemResponseVO>>> getCartItems(@RequestParam("customerId") final Long customerId) {
        log.info(String.format("Fetching cart items of customerID: %d: ", customerId));
        ApiResponseVO<List<CartItemResponseVO>> response = new ApiResponseVO<>(productService.getCartItems(customerId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/total-price")
    public ApiResponseVO<String> calculateTotalPrice(@PathVariable("customerId") Long customerId) {
        log.info(String.format("Calculating total price of cart for customer %d: ", customerId));
        String totalPrice = String.valueOf(productService.calculateTotalPrice(customerId));

        return ApiResponseVO.<String>builder()
                .message("Total price calculated successfully")
                .payload(totalPrice)
                .build();
    }
}
