package com.ontopchallenge.ontopdigitalwallet.Repository;
import com.ontopchallenge.ontopdigitalwallet.Model.WalletTransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Repository
public interface IWalletTransactionRepository extends JpaRepository<WalletTransactionModel, Long>
{
   Page<WalletTransactionModel> findByUser_IdAndCreatedAtBetweenAndAmountBetween(
               Long user_id
           ,   LocalDateTime  createdAtStart
           ,   LocalDateTime  createdAtEnd
           ,   Double amountStart
           ,   Double amountEnd
           ,   Pageable pageable) ;

}