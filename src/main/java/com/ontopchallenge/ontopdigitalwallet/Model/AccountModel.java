package com.ontopchallenge.ontopdigitalwallet.Model;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityLongIdentity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "tb_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountModel extends BaseEntityLongIdentity implements Serializable {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surName;
    @Column(nullable = false)
    private Long identificationNumber;
    @Column(nullable = false)
    private int accountNumber;
    @Column(nullable = false)
    private String bankName;
}
