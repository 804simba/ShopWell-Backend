package com.shopwell.api.model.VOs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderRequestVO {

    private Long orderId;

    private Long customerId;

    private String shippingAddress;

    private String paymentMethod;

    private List<OrderItemVO> orderItems;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemVO {
        private Long productId;

        private Integer quantity;

        private BigDecimal productPrice;

        private String deliveryDate;
    }
}
