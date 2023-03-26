package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceModel extends BaseEntityIdentity {
    private Double amount;
    @JsonIgnoreProperties("balance")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;
}
