/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.base;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

/**
 *
 * @author timofeevan
 */
public class ConfigBuilder {
    private Config config = new Config();
    
    public ConfigBuilder(){
        
    }
    
    public ConfigBuilder(boolean isTest){
        isTest(isTest);
    }
    
    public ConfigBuilder addEntityManagerFactory(String puName, EntityManagerFactory emf){
        config.addEntityManagerFactory(puName, emf);
        return this;
    }
    
    public ConfigBuilder addPreConfigPersistenceUnitName(String puName){
        config.addEntityManagerFactory(puName, null);
        return this;
    }
    
    public ConfigBuilder addPreConfigPersistenceUnitName(String... puNames){
        for (String puName : puNames) {
            config.addEntityManagerFactory(puName, null);
        }        
        return this;
    }
    
    public ConfigBuilder isTest(boolean isTest){
        config.setIsTest(isTest);
        return this;
    }
    
    public Config build(){
        return config;
    }
}
