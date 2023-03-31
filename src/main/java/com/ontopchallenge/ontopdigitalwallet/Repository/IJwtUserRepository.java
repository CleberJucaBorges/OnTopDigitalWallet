package com.ontopchallenge.ontopdigitalwallet.Repository;

import com.ontopchallenge.ontopdigitalwallet.Model.JwtUserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IJwtUserRepository extends CrudRepository<JwtUserModel, UUID> {
    JwtUserModel findByUsername(String username);
}