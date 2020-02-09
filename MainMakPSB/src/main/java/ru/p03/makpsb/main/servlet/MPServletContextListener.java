/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;
/**
 *
 * @author timofeevan
 */
public class MPServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger.getLogger(MPServletContextListener.class.getName()).log(Level.SEVERE, null, "Старт приложения");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger(MPServletContextListener.class.getName()).log(Level.SEVERE, null, "Завершение приложения");
    }

}
