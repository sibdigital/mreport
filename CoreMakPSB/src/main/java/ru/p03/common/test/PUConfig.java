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
public class PUConfig {
    private final TestConfig testConfig;
    private final String puName;
    
    public PUConfig(TestConfig testConfig, String puName){
        this.testConfig = testConfig;
        this.puName = puName;
    }

    public TestConfig getTestConfig() {
        return testConfig;
    }

    public String getPuName() {
        return puName;
    }
}
