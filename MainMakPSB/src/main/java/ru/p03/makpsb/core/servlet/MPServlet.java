/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.servlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.p03.common.model.DomainUser;
/**
 *
 * @author timofeevan
 */
public abstract class MPServlet extends HttpServlet{
    
    protected abstract String page();
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
    
    /**
     * Перенаправление страницы на саму себя
     * @param   request       HTTP-запрос 
     * @param   response  HTTP-ответ
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected final void selfForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher(page());
        dispatcher.forward(request, response);
    }
    
    protected final DomainUser getUser(HttpServletRequest request, HttpServletResponse response){
        DomainUser domainUser = new DomainUser();
        domainUser.setLogin("");
        domainUser.setPassword("");
        domainUser.setAuthenticateStatus(DomainUser.AUTH_OK);
        domainUser.setHash("");
        domainUser.setAttributes(new HashMap<>());
        request.getSession().setAttribute("user", domainUser);

        DomainUser au = domainUser;
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("user");
        if (attribute != null && attribute instanceof DomainUser){
            au = ((DomainUser)attribute);
        }
        return au;
    }
}
