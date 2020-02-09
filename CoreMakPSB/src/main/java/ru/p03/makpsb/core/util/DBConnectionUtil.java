/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timofeevan
 */
public class DBConnectionUtil {

    private static final String INITIAL = "java:/comp/env";
    
    private DBConnectionUtil(){
        
    }
    
    public static DBConnectionUtil instance(){
        return new DBConnectionUtil();
    }
    
    public Connection createConnection(String initialContext, String source){
        Connection conn = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup(initialContext);
            DataSource ds = (DataSource)envContext.lookup(source);
            conn = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public Connection createConnection(String source){
        return createConnection(INITIAL, source);
    }

}