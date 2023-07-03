package com.shopwell.api.model.VOs.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentSuccessResponse {

    private String reference;

    private String amount;
}
