package com.ontopchallenge.ontopdigitalwallet.Repository;

import com.ontopchallenge.ontopdigitalwallet.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {

}