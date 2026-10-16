package com.rest_api.spring_boot_rest_api.dto.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsSignupDto implements Serializable {

    private static final long SerialVersionUID = 1L;

    private String userName;
    private String password;
    private String fullName;

    public AccountCredentialsSignupDto() {}

    public AccountCredentialsSignupDto(String userName, String password, String fullName) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialsSignupDto that = (AccountCredentialsSignupDto) o;
        return Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
