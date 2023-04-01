package com.ontopchallenge.ontopdigitalwallet.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ontopchallenge.ontopdigitalwallet.Model.Base.BaseEntityUUID;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "jwtUser")
@Data
public class JwtUserModel extends BaseEntityUUID {
    @Column(unique = true)
    private String username;
    @Column
    @JsonIgnore
    private String password;
}