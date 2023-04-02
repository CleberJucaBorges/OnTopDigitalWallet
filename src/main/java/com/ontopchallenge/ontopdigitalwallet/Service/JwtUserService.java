package com.ontopchallenge.ontopdigitalwallet.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.ontopchallenge.ontopdigitalwallet.Config.Jwt.PasswordPolicy;
import com.ontopchallenge.ontopdigitalwallet.Dto.Jwt.JwtRequest;
import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidPasswordPolicyException;
import com.ontopchallenge.ontopdigitalwallet.Model.JwtUserModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IJwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class JwtUserService implements UserDetailsService {
    @Autowired
    private IJwtUserRepository jwtUserRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserModel user = jwtUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public JwtUserModel save(JwtRequest user) throws InvalidPasswordPolicyException {
        JwtUserModel newUser = new JwtUserModel();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        return this.save(newUser);
    }

    public JwtUserModel save(JwtUserModel newUser) throws InvalidPasswordPolicyException {
        if(!PasswordPolicy.validate(newUser.getPassword())){
            throw new InvalidPasswordPolicyException("invalid password format");
        }
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        newUser.setCreatedBy("api_user");
        newUser.setCreatedAt(LocalDateTime.now());
        return jwtUserRepository.save(newUser);
    }

    public boolean existsById(Long id) {
        return jwtUserRepository.existsById(id);
    }

}