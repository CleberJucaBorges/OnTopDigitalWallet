package com.ontopchallenge.ontopdigitalwallet.Dto.Wallet;
import com.ontopchallenge.ontopdigitalwallet.Enum.TransactionType;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class WalletTransactionRequestDto {
    @NotNull(message = "the field accountId number is mandatory")
    @Positive(message = "the field amount number is mandatory")
    private long account_id;

    @Nullable
    private Long destination_account_id;

    @NotNull(message = "the field amount number is mandatory")
    @Positive(message = "the field amount number is mandatory")
    private Double amount;
    @NotNull(message = "the field transaction type is mandatory")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;



}