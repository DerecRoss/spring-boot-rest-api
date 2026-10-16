package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.dto.security.AccountCredentialsSignupDto;
import com.rest_api.spring_boot_rest_api.dto.security.TokenDto;
import com.rest_api.spring_boot_rest_api.repository.UserRepository;
import com.rest_api.spring_boot_rest_api.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity<TokenDto> signIn(AccountCredentialsSignupDto credentials){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUserName(),
                credentials.getPassword())
        );

        var user = repository.findByUsername(credentials.getUserName());
        if (user == null) throw new UsernameNotFoundException("Username: " + credentials.getUserName() + "not found");

        var token = jwtTokenProvider.createAccessToken(credentials.getUserName(), user.getRoles());

        return ResponseEntity.ok(token);
    }
}
