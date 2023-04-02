package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityIdentity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "jwtUser")
@Data
public class JwtUserModel extends BaseEntityIdentity {
    @Column(unique = true)
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @JsonIgnoreProperties("jwtUserModel")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;
}