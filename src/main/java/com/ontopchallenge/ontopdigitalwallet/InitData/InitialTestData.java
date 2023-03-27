package com.ontopchallenge.ontopdigitalwallet.InitData;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidAccountException;
import com.ontopchallenge.ontopdigitalwallet.Model.UserModel;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import com.ontopchallenge.ontopdigitalwallet.Service.UserService;
import com.ontopchallenge.ontopdigitalwallet.Service.DestinationAccountService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class InitialTestData {
    private final UserService userService;
    private final DestinationAccountService destinationAccountService;
    public InitialTestData(UserService userService, DestinationAccountService destinationAccountService) {
        this.userService = userService;
        this.destinationAccountService = destinationAccountService;
    }
    @SneakyThrows
    @PostConstruct
    public void load() {
        loadAccount();
        loadDestinationAccount();
    }
    private void loadAccount(){
        if (!userService.existsById(1L))
        {
            UserModel account = new UserModel();
            account.setName("ONTOP");
            account.setSurName("INC");
            account.setIdentificationNumber("123456789");
            account.setAccountNumber("0245253419");
            account.setRoutingNumber("028444018");
            account.setBankName("test Bank");
            account.setCurrency("USD");
            account.setCreatedBy("test_user");
            account.setAccountType(AccountType.COMPANY);
            userService.save(account);
        }
    }

    private void loadDestinationAccount() throws InvalidAccountException {

        if (!destinationAccountService.existsById(1L))
        {
            DestinationAccountModel account = new DestinationAccountModel();
            account.setName("TONY");
            account.setLastName("STARK");
            account.setAccountNumber("1885226711");
            account.setCurrency("USD");
            account.setRoutingNumber("211927207");
            account.setIdentificationNumber("987654321");
            account.setUser(userService.findById(1).get());
            account.setCreatedBy("test_user");
            destinationAccountService.save(account);
        }

    }
}
