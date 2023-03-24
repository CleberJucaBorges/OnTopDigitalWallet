package com.ontopchallenge.ontopdigitalwallet.InitData;
import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidAccountException;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import com.ontopchallenge.ontopdigitalwallet.Service.AccountService;
import com.ontopchallenge.ontopdigitalwallet.Service.DestinationAccountService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class InitialTestData {
    private final AccountService accountService;
    private final DestinationAccountService destinationAccountService;
    public InitialTestData(AccountService accountService, DestinationAccountService destinationAccountService) {
        this.accountService = accountService;
        this.destinationAccountService = destinationAccountService;
    }
    @SneakyThrows
    @PostConstruct
    public void load() {
        loadAccount();
        loadDestinationAccount();
    }
    private void loadAccount(){
        AccountModel account = new AccountModel();
        account.setName("Cleber");
        account.setSurName("Juca");
        account.setIdentificationNumber(123456789L);
        account.setAccountNumber(123);
        account.setBankName("test Bank");
        account.setCurrency("USD");
        account.setCreatedBy("test_user");
        accountService.save(account);
    }

    private void loadDestinationAccount() throws InvalidAccountException {
        DestinationAccountModel account = new DestinationAccountModel();
        account.setName("Ozzy");
        account.setLastName("Osbourne");
        account.setAccountNumber(321);
        account.setCurrency("USD");
        account.setRoutingNumber(123456L);
        account.setIdentificationNumber(987654321L);
        account.setAccount(accountService.findById(1).get());
        account.setCreatedBy("test_user");
        destinationAccountService.save(account);

        DestinationAccountModel account2 = new DestinationAccountModel();
        account2.setName("Bruce");
        account2.setLastName("Dickinson");
        account2.setAccountNumber(312);
        account2.setCurrency("USD");
        account2.setRoutingNumber(153321L);
        account2.setIdentificationNumber(987456321L);
        account2.setAccount(accountService.findById(1).get());
        account2.setCreatedBy("test_user");
        destinationAccountService.save(account2);
    }
}
