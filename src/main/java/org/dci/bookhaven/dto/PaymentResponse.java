package org.dci.bookhaven.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private String clientSecret;
    public PaymentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
