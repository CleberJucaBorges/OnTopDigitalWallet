package com.ontopchallenge.ontopdigitalwallet.Repository;

import com.ontopchallenge.ontopdigitalwallet.Model.BalanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBalanceRepository extends JpaRepository<BalanceModel, Long> {
    BalanceModel findByUser_Id(Long accountId);
}