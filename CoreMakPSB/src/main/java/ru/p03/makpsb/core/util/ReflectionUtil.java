/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timofeevan
 */
public class ReflectionUtil {
    
    private static String GET = "get";
    
    private ReflectionUtil(){
        
    }
    
    public static ReflectionUtil inctance(){
        return new ReflectionUtil();
    }
    
    public<T> T invoke (Class clazz, Object object, String methodName, Object ... arguments){
        Object invoke = null;
        try {
            Method method = clazz.getMethod(methodName);
            invoke = method.invoke(object, arguments);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException 
                | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (T)invoke;
    }
    
    public<T> T invoke_getter(Class clazz, Object object, String methodNameWithout_get){
        return invoke(clazz, object, GET + methodNameWithout_get);
    }
    
    public Object[] invoke_gettersObj(Class clazz, Object object, String ... methodNameWithout_get){
        Object[] retarr = new Object[methodNameWithout_get.length];
        for (int i = 0; i < methodNameWithout_get.length; i++) {
            retarr[i] = invoke(clazz, object, GET + methodNameWithout_get[i]);            
        }
        return retarr; 
    }
    
    public Object[][] invoke_gettersObjArr(Class clazz, Object[] objects, String ... methodNameWithout_get){
        return invoke_getters(clazz, Arrays.asList(objects), methodNameWithout_get);        
    }
    
    public<T> Object[][] invoke_getters(Class clazz, List<T> objects, String ... methodNameWithout_get){
        Object[][] retarr = new Object[objects.size()][];
        for (int i = 0; i < objects.size(); i++) {
            retarr[i] = invoke_gettersObj(clazz, objects.get(i), methodNameWithout_get);            
        }
        return retarr;        
    }
}
