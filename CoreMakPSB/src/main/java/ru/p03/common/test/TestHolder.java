/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.common.test;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author timofeevan
 */
public abstract class TestHolder<T> {
    private final Map<String, T> factories = new HashMap<>();
    
    public T get(String key){
        return factories.get(key);
    }
    
    protected void put(String key, T factory){
        factories.put(key, factory);
    }
    
    public boolean isFill(){
        return !factories.isEmpty();
    }
    
    public abstract void fill();
}
