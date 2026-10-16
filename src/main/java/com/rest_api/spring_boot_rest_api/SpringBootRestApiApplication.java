package com.rest_api.spring_boot_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApplication.class, args);

//		generateHashedPassword();
	}

    private static void generateHashedPassword() {
		PasswordEncoder pbkdf2Encoder= new Pbkdf2PasswordEncoder( // create encoder
				"",
				8, // key length
				185000, // encode this times
				Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256); // encoder algorithm

		Map<String, PasswordEncoder> encoders = new HashMap<>(); // put encoder in hashmap for encoder
		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders); // instance this encoder

		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

		String passwordOne = passwordEncoder.encode("admin123");
		String passwordTwo = passwordEncoder.encode("admin321");

		System.out.println(passwordOne);
		System.out.println(passwordTwo);
    }
}
