package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.AddToCartRequestVO;
import com.shopwell.api.model.VOs.request.CartItemVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
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
    public ResponseEntity<ApiResponseVO<?>> removeProductFromCart(@RequestParam("productId") final Long productId,
                                                                  @RequestParam("customerId") final Long customerId) {
        log.info(String.format("Removing product %d from cart: ", productId));
        productService.removeProductFromCart(productId, customerId);
        ApiResponseVO<?> response = new ApiResponseVO<>("Product removed from cart successfully", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseVO<List<CartItemVO>>> getCartItems(@RequestParam("customerId") final Long customerId) {
        log.info(String.format("Fetching cart items of customerID: %d: ", customerId));
        ApiResponseVO<List<CartItemVO>> response = new ApiResponseVO<>("", productService.getCartItems(customerId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/total-price")
    public ApiResponseVO<BigDecimal> calculateTotalPrice(@PathVariable("customerId") Long customerId) {
        log.info(String.format("Calculating total price of cart for customer %d: ", customerId));
        BigDecimal totalPrice = productService.calculateTotalPrice(customerId);

        return ApiResponseVO.<BigDecimal>builder()
                .message("Total price calculated successfully")
                .payload(totalPrice)
                .build();
    }
}
