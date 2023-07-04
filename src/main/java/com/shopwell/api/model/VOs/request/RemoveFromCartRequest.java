package com.shopwell.api.model.VOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveFromCartRequest {

    private Long productId;
}
