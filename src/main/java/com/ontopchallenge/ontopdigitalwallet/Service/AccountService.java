package com.ontopchallenge.ontopdigitalwallet.Service;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IAccountRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountModel save(AccountModel accountModel) {
        if (accountModel.getId() == 0){
            accountModel.setCreatedAt(LocalDateTime.now());
        }
        else{
            accountModel.setUpdatedAt(LocalDateTime.now());
        }
        return  accountRepository.save(accountModel);
    }

    public List<AccountModel> findAll() {
        return accountRepository.findAll();
    }

    public Optional<AccountModel> findById(long id) {
        return accountRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    @Transactional
    public void delete(AccountModel accountModel) {
        accountRepository.delete(accountModel);
    }


}

