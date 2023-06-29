package com.shopwell.api.model.VOs.response;

import com.shopwell.api.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.type.descriptor.DateTimeUtils;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiResponseVO<T> {

    private String message;

    private String time;

    private T payload;

    public ApiResponseVO(String message, T payload) {
        this.message = message;
        this.time = DateUtils.saveDate(LocalDateTime.now());
        this.payload = payload;
    }

    public ApiResponseVO(T payload) {
        this(null, payload);
    }
}
