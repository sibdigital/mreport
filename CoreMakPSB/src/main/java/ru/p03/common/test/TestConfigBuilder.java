/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.common.test;

/**
 *
 * @author timofeevan
 */
public class TestConfigBuilder {
    
    private static final String DB2Driver = "com.ibm.db2.jcc.DB2Driver";
    private static final String PostgresDriver = "org.postgresql.Driver";
    
    private TestConfig config =  new TestConfig();
    
    public TestConfigBuilder setDbUrl(String dbUrl) {
        config.setDbUrl(dbUrl);
        return this;
    }

    public TestConfigBuilder setUser(String user) {
        config.setUser(user);
        return this;
    }

    public TestConfigBuilder setPassword(String password) {
        config.setPassword(password);
        return this;
    }

    public TestConfigBuilder setDriver(String driver) {
        config.setDriver(driver);
        return this;
    }
    
    public TestConfigBuilder db2Driver(){
        config.setDriver(DB2Driver);
        return this;
    }

    public TestConfigBuilder postgresDriver(){
        config.setDriver(PostgresDriver);
        return this;
    }
    
    public TestConfig build(){
        return config;
    }
}
