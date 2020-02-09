/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author timofeevan
 */
public class ResourceUtil {
    
    private static ResourceUtil INSTANCED = null;
    
    private ResourceUtil(){
        
    }
    
    public static ResourceUtil inctance(){
        if (INSTANCED == null){
            INSTANCED = new ResourceUtil();
        }
        return INSTANCED;
    }

    public String resource_asString(Class parentClass, String path, String encoding){
        String str = "";
        try (InputStream stream = parentClass.getResourceAsStream(path); 
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, encoding))){            
            String s = "";
            while((s = br.readLine()) != null){
                str += (s + "\n");
            }           
        } catch (UnsupportedEncodingException ex ) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex ) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    public static String resource_asString(URL url){
        String str = "";
        try (InputStream stream = url.openStream(); 
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF8"))){                  
            String s = "";
            while((s = br.readLine()) != null){
                str += (s + "\n");
            }           
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return str;
    }
    
    public String resource_asString(Class parentClass, String path){
        return resource_asString(parentClass, path, "UTF8");       
    }
    
    public String resourceFilePath(Class clazz, String path){
        URL url = clazz.getResource(path);
        String urlPath = url.getPath();
        return urlPath.substring(1);
    }
    
    public Object getEnvironment (String fullName){
        Object value = null;
        try {
            Context ctx = new InitialContext();
            value = ctx.lookup(fullName);
        } catch (NamingException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    public Object getGlobalNamingResource(String initialContext, String source){
        Object resource = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup(initialContext);
            resource = envContext.lookup(source);
        } catch (NamingException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resource;    
    }
}
