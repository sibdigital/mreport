/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.model;

import java.util.Map;

/**
 *
 * @author timofeevan
 */
public interface IDomainUser {

    Map<String, String> getAttributes();

    String getAuthenticateStatus();

    String getHash();

    String getLogin();

    String getPassword();

    void setAttributes(Map<String, String> attributes);

    void setAuthenticateStatus(String authenticateStatus);

    void setHash(String hash);

    void setLogin(String login);

    void setPassword(String password);
    
}
