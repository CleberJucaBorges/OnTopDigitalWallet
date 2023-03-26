package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String accountNumber;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false)
    private String routingNumber;
    @Column(nullable = false)
    private Long identificationNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("destinationAccounts")
    private UserModel user;
    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("destinationAccount")
    private List<WalletTransactionModel> walletTransactions;
}
