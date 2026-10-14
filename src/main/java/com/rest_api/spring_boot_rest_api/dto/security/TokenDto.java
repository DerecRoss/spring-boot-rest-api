package com.rest_api.spring_boot_rest_api.dto.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenDto implements Serializable {

    private static final long SerialVersionUID = 1L;

    private String userName;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;

    public TokenDto() {
    }

    public TokenDto(String userName, Boolean authenticated, Date created, Date expiration, String accessToken, String refreshToken) {
        this.userName = userName;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenDto tokenDto = (TokenDto) o;
        return Objects.equals(userName, tokenDto.userName) && Objects.equals(authenticated, tokenDto.authenticated) && Objects.equals(created, tokenDto.created) && Objects.equals(expiration, tokenDto.expiration) && Objects.equals(accessToken, tokenDto.accessToken) && Objects.equals(refreshToken, tokenDto.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, authenticated, created, expiration, accessToken, refreshToken);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
