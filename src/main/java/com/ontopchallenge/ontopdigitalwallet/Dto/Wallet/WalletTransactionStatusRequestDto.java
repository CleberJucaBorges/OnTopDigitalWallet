package com.ontopchallenge.ontopdigitalwallet.Dto.Wallet;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WalletTransactionStatusRequestDto {
    @NotBlank(message = "the field transactionTypeId is mandatory")
    private String status;
}