package com.shopwell.api.model.VOs.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class ResponseVO {
    private String message;
    private LocalDateTime localDateTime;
}
