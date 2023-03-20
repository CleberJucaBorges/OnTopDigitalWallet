package com.ontopchallenge.ontopdigitalwallet.Model;

import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityLong;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_wallet_transaction_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionTypeModel extends BaseEntityLong implements Serializable {
    @Column(nullable = false)
    private String transactionTypeName;
}
