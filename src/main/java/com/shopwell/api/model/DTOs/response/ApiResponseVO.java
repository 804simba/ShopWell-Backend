package com.shopwell.api.model.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponseVO<T> {

    private String message;

    private String error;

    private T payload;
}
