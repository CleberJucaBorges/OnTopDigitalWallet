package com.ontopchallenge.ontopdigitalwallet.Dto.BankProvider;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BankProviderResponseDTO {
    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;
}