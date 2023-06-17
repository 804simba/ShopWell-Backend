package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessageVO {
    private HttpStatus status;
    private String message;
}
