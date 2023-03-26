package com.ontopchallenge.ontopdigitalwallet.Dto.Account;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import lombok.Data;
@Data
public class AccountResponseDto {
    private long id;
    private String name;
    private String surName;
    private String identificationNumber;
    private String accountNumber;
    private String routingNumber;
    private String bankName;
    private String currency;
    private AccountType accountType;
}