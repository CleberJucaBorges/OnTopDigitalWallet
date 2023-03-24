package com.ontopchallenge.ontopdigitalwallet.Repository;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDestinationAccountRepository extends JpaRepository<DestinationAccountModel, Long> {

}