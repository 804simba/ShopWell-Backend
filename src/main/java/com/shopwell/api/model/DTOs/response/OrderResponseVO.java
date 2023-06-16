package com.shopwell.api.model.DTOs.response;

import com.shopwell.api.model.DTOs.request.OrderRequestVO.OrderItemVO;
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

    private List<OrderItemVO> orderItems;

    private String orderStatus;
}
