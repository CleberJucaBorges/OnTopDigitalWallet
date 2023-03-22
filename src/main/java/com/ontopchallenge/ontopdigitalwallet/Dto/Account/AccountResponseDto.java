package com.ontopchallenge.ontopdigitalwallet.Dto.Account;
import lombok.Data;
@Data
public class AccountResponseDto {
    private long id;
    private String name;
    private String surName;
    private Long identificationNumber;
    private int accountNumber;
    private String bankName;
}