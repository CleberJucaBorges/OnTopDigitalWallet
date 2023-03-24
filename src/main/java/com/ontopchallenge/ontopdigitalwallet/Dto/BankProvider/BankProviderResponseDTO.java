package com.ontopchallenge.ontopdigitalwallet.Dto.BankProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankProviderResponseDTO {
    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class RequestInfo {
    private String status;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class PaymentInfo {
    private double amount;
    private String id;
}
