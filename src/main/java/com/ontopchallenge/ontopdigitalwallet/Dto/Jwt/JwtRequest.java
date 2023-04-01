package com.ontopchallenge.ontopdigitalwallet.Dto.Jwt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank(message = "the field password is mandatory")
    private String password;
    @Email(message = "a valid email is mandatory")
    @NotBlank(message = "a valid email is mandatory")
    private String username;
}
