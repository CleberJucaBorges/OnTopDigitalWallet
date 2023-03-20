package com.ontopchallenge.ontopdigitalwallet.Repository;

import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IAccountRepository extends JpaRepository<AccountModel, Long> {

}