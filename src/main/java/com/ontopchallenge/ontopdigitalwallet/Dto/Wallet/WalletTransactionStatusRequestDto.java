package com.ontopchallenge.ontopdigitalwallet.Dto.Wallet;
import com.ontopchallenge.ontopdigitalwallet.Enum.WalletTransactionStatus;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class WalletTransactionStatusRequestDto {
    @NotNull(message = "the field transactionTypeId is mandatory")
    private WalletTransactionStatus  walletTransactionStatus;
}