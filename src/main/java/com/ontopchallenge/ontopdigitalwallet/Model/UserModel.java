package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseEntityIdentity  {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surName;
    @Column(nullable = false)
    private Long identificationNumber;
    @Column(nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String routingNumber;
    @Column(nullable = false)
    private String bankName;
    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WalletTransactionModel> transactions;
    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DestinationAccountModel> destinationAccounts;
    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonIgnoreProperties("user")
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private BalanceModel balance;
}
