package com.ontopchallenge.ontopdigitalwallet.Repository;

import com.ontopchallenge.ontopdigitalwallet.Model.JwtUserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IJwtUserRepository extends CrudRepository<JwtUserModel, Long> {
    JwtUserModel findByUsername(String username);
}