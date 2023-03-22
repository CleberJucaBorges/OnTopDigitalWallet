package com.ontopchallenge.ontopdigitalwallet.Dto.Wallet;
import com.ontopchallenge.ontopdigitalwallet.Enum.TransactionType;
import com.ontopchallenge.ontopdigitalwallet.Enum.WalletTransactionStatus;
import lombok.Data;

@Data
public class WalletTransactionResponseDto {
    private long id;
    private long account_id;
    private Double amount;
    private WalletTransactionStatus walletTransactionStatus;
    private TransactionType transactionType;
}