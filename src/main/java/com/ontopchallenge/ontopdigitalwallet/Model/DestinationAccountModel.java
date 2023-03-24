package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "destination_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationAccountModel extends BaseEntityIdentity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private int accountNumber;
    @Column(nullable = false)
    private String currency;
    private Long routingNumber;
    @Column(nullable = false)
    private Long identificationNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties("destinationAccounts")
    private AccountModel account;
    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("destinationAccount")
    private List<WalletTransactionModel> walletTransactions;
}
