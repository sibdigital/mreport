/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author timofeevan
 */
public class DomainUser implements IDomainUser {

    public static final String AUTH_OK = "AUTH_OK";
    public static final String AUTH_FAILED = "AUTH_FAILED";

    private String authenticateStatus;

    private String login;

    private String hash;
    
    private String password;

    private Map<String, String> attributes = new HashMap<>();

    @Override
    public String getAuthenticateStatus() {
        return authenticateStatus;
    }

    @Override
    public void setAuthenticateStatus(String authenticateStatus) {
        this.authenticateStatus = authenticateStatus;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    @Override
     public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
