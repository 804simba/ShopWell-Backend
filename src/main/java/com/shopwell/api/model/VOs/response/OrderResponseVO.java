package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseVO {

    private Long orderId;

    private Long customerId;

    private String orderDate;

    private List<OrderItemVO> orderItems;

    private String orderStatus;
}
