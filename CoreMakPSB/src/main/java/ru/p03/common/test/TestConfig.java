/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author timofeevan
 */
public class TestConfig {

    private String dbUrl;
    private String user;
    private String password;
    private String driver;

    /**
     * @return the dbUrl
     */
    protected String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    protected void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * @return the user
     */
    protected String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    protected void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    protected String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    protected void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the driver
     */
    protected String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    protected void setDriver(String driver) {
        this.driver = driver;
    }

    public Map asMap() {
        Map hm = new HashMap();
        hm.put("javax.persistence.jdbc.url", dbUrl);
        hm.put("javax.persistence.jdbc.user", user);
        hm.put("javax.persistence.jdbc.driver", driver);
        hm.put("javax.persistence.jdbc.password", password);
        return hm;
    }
}
