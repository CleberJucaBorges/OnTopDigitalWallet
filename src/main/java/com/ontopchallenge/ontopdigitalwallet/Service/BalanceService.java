package com.ontopchallenge.ontopdigitalwallet.Service;

import com.ontopchallenge.ontopdigitalwallet.Model.BalanceModel;
import com.ontopchallenge.ontopdigitalwallet.Model.WalletTransactionModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IAccountRepository;
import com.ontopchallenge.ontopdigitalwallet.Repository.IBalanceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BalanceService {

    private final IBalanceRepository  balanceRepository;
    public BalanceService(IBalanceRepository  balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public Optional<BalanceModel> findById(long id) {
        return balanceRepository.findById(id);
    }

    public BalanceModel findByAccountId(long accountId) {
       return balanceRepository.findByAccount_Id(accountId);
    }
    @Transactional
    public BalanceModel save(BalanceModel balanceModel) {
        if (balanceModel.getId() == 0){
            balanceModel.setCreatedAt(LocalDateTime.now());
        }
        else{
            balanceModel.setUpdatedAt(LocalDateTime.now());
        }
        return balanceRepository.save(balanceModel);
    }


    @Transactional
    public void recomposeBalance(WalletTransactionModel walletTransactionModel )
    {
        BalanceModel balanceModel = balanceRepository.findByAccount_Id(walletTransactionModel.getAccount().getId());
        balanceModel.setAmount(balanceModel.getAmount() + walletTransactionModel.getAmount() );
        balanceRepository.save(balanceModel);
    }
}

