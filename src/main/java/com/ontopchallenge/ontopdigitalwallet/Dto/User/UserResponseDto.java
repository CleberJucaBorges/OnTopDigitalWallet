package com.ontopchallenge.ontopdigitalwallet.Dto.User;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import lombok.Data;
@Data
public class UserResponseDto {
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