package com.ontopchallenge.ontopdigitalwallet.Dto.DestinationAccount;
import com.ontopchallenge.ontopdigitalwallet.Dto.Account.AccountResponseDto;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import lombok.Data;
@Data
public class DestinationAccountResponseDto {
    private long id;
    private String name;
    private String lastName;
    private int accountNumber;
    private String currency;
    private Long routingNumber;
    private Long identificationNumber;
    private AccountResponseDto account;
}