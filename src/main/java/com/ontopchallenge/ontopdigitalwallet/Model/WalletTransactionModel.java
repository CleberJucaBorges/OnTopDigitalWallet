package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Enum.TransactionType;
import com.ontopchallenge.ontopdigitalwallet.Enum.WalletTransactionStatus;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "wallet_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionModel extends BaseEntityIdentity  {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("transactions")
    private UserModel user;
    @Column(nullable = false)
    private Double amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletTransactionStatus walletTransactionStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;
    @Column
    @Type(type="uuid-char")
    private UUID external_id;
    @Column
    private Double feeAmount;
    @Column(nullable = false)
    private String currency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id", nullable = true)
    @JsonIgnoreProperties("walletTransaction")
    private DestinationAccountModel destinationAccount;
}
