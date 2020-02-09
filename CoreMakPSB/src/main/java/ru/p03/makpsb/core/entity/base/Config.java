/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.entity.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

/**
 *
 * @author timofeevan
 */
public class Config {

    private final Map<String, EntityManagerFactory> persistenceUnitInfos = new HashMap<>();

    private boolean isTest = false;

    public Config() {

    }

    public Config(String puName, EntityManagerFactory emf) {
        persistenceUnitInfos.put(puName, emf);
    }

    public void addEntityManagerFactory(String puName, EntityManagerFactory emf) {
        persistenceUnitInfos.put(puName, emf);
    }

    public EntityManagerFactory removeEntityManagerFactory(String puName) {
        return persistenceUnitInfos.remove(puName);
    }

    public EntityManagerFactory getEntityManagerFactory(String puName) {
        return persistenceUnitInfos.get(puName);
    }

    public boolean containsEntityManagerFactoryKey(String puName) {
        return persistenceUnitInfos.containsKey(puName);
    }
    
    public Set<String> getPersistenceUnitNames(){
        return persistenceUnitInfos.keySet();
    }

    public boolean isTest() {
        return isTest;
    }

    public void setIsTest(boolean isTest) {
        this.isTest = isTest;
    }

    public boolean connectionAlive(String puName) {
        boolean result = false;
        try {
            EntityManagerFactory emf = getEntityManagerFactory(puName);
            emf.createEntityManager();
            result = true;
        } catch (Exception ex) {

        }
        return result;
    }
    
    public boolean connectionsAlive() {
        return persistenceUnitInfos.keySet().stream()
                .map(pu -> connectionAlive(pu))
                .reduce((alive1, alive2) -> alive1 && alive2)
                .orElse(false);
    }
}
