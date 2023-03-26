package com.ontopchallenge.ontopdigitalwallet.Dto.DestinationAccount;
import com.ontopchallenge.ontopdigitalwallet.Dto.Account.AccountResponseDto;
import lombok.Data;
@Data
public class DestinationAccountResponseDto {
    private long id;
    private String name;
    private String lastName;
    private String accountNumber;
    private String currency;
    private String routingNumber;
    private Long identificationNumber;
    private AccountResponseDto account;
}