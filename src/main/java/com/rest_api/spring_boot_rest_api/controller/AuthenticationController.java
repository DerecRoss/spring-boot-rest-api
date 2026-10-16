package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.dto.security.AccountCredentialsSignupDto;
import com.rest_api.spring_boot_rest_api.dto.security.TokenDto;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/signin")
@Tag(name = "Authentication Endpoint")
public class AuthenticationController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Authenticate user and return token.")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(AccountCredentialsSignupDto credentials){
        if (credentialsIsInvalid(credentials)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        }

        var token = authService.signIn(credentials);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        return ResponseEntity.ok().body(token);
    }

    private static boolean credentialsIsInvalid(@RequestBody AccountCredentialsSignupDto credentials) {
        return credentials == null || StringUtils.isBlank(credentials.getPassword()) || StringUtils.isBlank(credentials.getUserName());
    }

    @Operation(summary = "Refresh token for authenticated user and returns a token.")
    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refresh(
            @PathVariable("username") String username,
            @RequestHeader("Authorization") String refreshToken
    ){
        if (parameterAreInvalid(username, refreshToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request.");
        }

        var token = authService.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        return ResponseEntity.ok().body(token);
    }

    private boolean parameterAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    @PostMapping("/auth/createUser")
    public ResponseEntity<AccountCredentialsSignupDto> save(@RequestBody AccountCredentialsSignupDto user) {
        AccountCredentialsSignupDto entity = authService.save(user);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }
}
