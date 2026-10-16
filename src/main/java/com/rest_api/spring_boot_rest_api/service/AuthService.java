package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.dto.security.AccountCredentialsSignupDto;
import com.rest_api.spring_boot_rest_api.dto.security.TokenDto;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.exception.RequiredObjectIsNonNullException;
import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.model.auth.User;
import com.rest_api.spring_boot_rest_api.repository.UserRepository;
import com.rest_api.spring_boot_rest_api.security.jwt.JwtTokenProvider;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

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

    public ResponseEntity<TokenDto> refreshToken(String username, String refreshToken){
        var user = repository.findByUsername(username);
        TokenDto token;
        if (user != null){
            token = jwtTokenProvider.refreshToken(refreshToken);
        }else {
            throw new UsernameNotFoundException("Username: " + username + "not found");
        }
        return ResponseEntity.ok(token);
    }

    private String generateHashedPassword(String password) {
        PasswordEncoder pbkdf2Encoder= new Pbkdf2PasswordEncoder( // create encoder
                "",
                8, // key length
                185000, // encode this times
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256); // encoder algorithm

        Map<String, PasswordEncoder> encoders = new HashMap<>(); // put encoder in hashmap for encoder
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders); // instance this encoder

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

        return passwordEncoder.encode(password);
    }

    public AccountCredentialsSignupDto save(AccountCredentialsSignupDto user){

        if (user == null) throw new RequiredObjectIsNonNullException();


        logger.info("Saving user in database.");
        var entity = new User();
        entity.setFullName(user.getFullName());
        entity.setUserName(user.getUserName());
        entity.setPassword(generateHashedPassword(user.getPassword()));

        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        var dto = repository.save(entity);
        return new AccountCredentialsSignupDto(dto.getUsername(), dto.getPassword(), dto.getFullName());
    }

}
