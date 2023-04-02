package com.ontopchallenge.ontopdigitalwallet.Config.Jwt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPolicy {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}$";

    public static boolean validate(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

