package com.ontopchallenge.ontopdigitalwallet.Dto.DestinationAccount;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DestinationAccountRequestDto {
    @NotBlank(message = "the field name is mandatory")
    private String name;
    @NotBlank(message = "the field last name is mandatory")
    private String lastName;
    @NotBlank(message = "the field identification number is mandatory")
    private String identificationNumber;
    @NotBlank(message = "the field account number is mandatory")
    private String accountNumber;
    @NotBlank(message = "the field routing number is mandatory")
    private String routingNumber;
    @NotBlank(message = "the field currency is mandatory")
    private String currency;
    @NotNull(message = "the field user_id is mandatory")
    private Long user_id;
}