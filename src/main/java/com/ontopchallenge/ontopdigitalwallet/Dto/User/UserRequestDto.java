package com.ontopchallenge.ontopdigitalwallet.Dto.User;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRequestDto {
    @NotBlank(message = "the field name is mandatory")
    private String name;
    @NotBlank(message = "the field surname is mandatory")
    private String surName;
    @NotNull(message = "the field identification number is mandatory")
    private Long identificationNumber;
    @NotBlank(message = "the field account number is mandatory")
    private String accountNumber;
    @NotBlank(message = "the field routing number is mandatory")
    private String routingNumber;
    @NotBlank(message = "the field bank name is mandatory")
    private String bankName;
    @NotBlank(message = "the field currency is mandatory")
    private String currency;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "the field account type is mandatory")
    private AccountType accountType;

}