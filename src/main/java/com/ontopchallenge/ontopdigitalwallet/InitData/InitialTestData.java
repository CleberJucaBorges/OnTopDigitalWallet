package com.ontopchallenge.ontopdigitalwallet.InitData;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidAccountException;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import com.ontopchallenge.ontopdigitalwallet.Service.AccountService;
import com.ontopchallenge.ontopdigitalwallet.Service.DestinationAccountService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

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
        if (!accountService.existsById(1L))
        {
            AccountModel account = new AccountModel();
            account.setName("ONTOP");
            account.setSurName("INC");
            account.setIdentificationNumber(123456789L);
            account.setAccountNumber("0245253419");
            account.setRoutingNumber("028444018");
            account.setBankName("test Bank");
            account.setCurrency("USD");
            account.setCreatedBy("test_user");
            account.setAccountType(AccountType.COMPANY);
            accountService.save(account);
        }
    }

    private void loadDestinationAccount() throws InvalidAccountException {

        if (!destinationAccountService.existsById(1L))
        {
            DestinationAccountModel account = new DestinationAccountModel();
            account.setName("TONY STARK");
            account.setLastName("Osbourne");
            account.setAccountNumber("1885226711");
            account.setCurrency("USD");
            account.setRoutingNumber("211927207");
            account.setIdentificationNumber(987654321L);
            account.setAccount(accountService.findById(1).get());
            account.setCreatedBy("test_user");
            destinationAccountService.save(account);
        }

    }
}
