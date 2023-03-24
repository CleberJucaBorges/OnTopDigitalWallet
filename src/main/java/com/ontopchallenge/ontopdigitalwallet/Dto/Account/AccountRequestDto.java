package com.ontopchallenge.ontopdigitalwallet.Dto.Account;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountRequestDto {


    @NotBlank(message = "the field name is mandatory")
    private String name;
    @NotBlank(message = "the field surname is mandatory")
    private String surName;
    @NotNull(message = "the field identification number is mandatory")
    private Long identificationNumber;
    @NotNull(message = "the field account number is mandatory")
    private int accountNumber;
    @NotBlank(message = "the field bank name is mandatory")
    private String bankName;
    @NotBlank(message = "the field currency is mandatory")
    private String currency;
}