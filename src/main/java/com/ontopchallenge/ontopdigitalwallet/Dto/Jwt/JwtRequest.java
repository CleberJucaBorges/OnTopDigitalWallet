package com.ontopchallenge.ontopdigitalwallet.Dto.Jwt;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
