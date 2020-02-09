/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.makpsb.main.utils.ServletUtils;
import ru.p03.common.client.DomainAuthetificator;
import ru.p03.common.model.DomainUser;

/**
 *
 * @author 003-0807
 */
//@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {

    String LOGIN_PAGE = "jsp/loginform.jsp";
    String ERROR_PAGE = "jsp/error.jsp";
    final String DYN = "/main/dynreport_list.do";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
//        if (ServletUtils.isNoAuth()) {
//            DomainUser domainUser = new DomainUser();
//            domainUser.setLogin("");
//            domainUser.setPassword("");
//            domainUser.setAuthenticateStatus(DomainUser.AUTH_OK);
//            domainUser.setHash("");
//            domainUser.setAttributes(new HashMap<>());
//            request.getSession().setAttribute("user", domainUser);
//            response.sendRedirect(DYN);
//        }
        //processRequest(request, response);
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
        dispatcher.forward(request, response);
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
        if (ServletUtils.isNoAuth()){
//            DomainUser domainUser = new DomainUser();
//            domainUser.setLogin("");
//            domainUser.setPassword("");
//            domainUser.setAuthenticateStatus(DomainUser.AUTH_OK);
//            domainUser.setHash("");
//            domainUser.setAttributes(new HashMap<>());
//            request.getSession().setAttribute("user", domainUser);
//            response.sendRedirect(DYN);
        }else {
            //processRequest(request, response);
            if (request.getParameter("login") != null && request.getParameter("password") != null) {

                String login = request.getParameter("login");
                String password = request.getParameter("password");

                try {
                    //ServletUtils.configureProperties();
                    URI commonServiceURL = ServletUtils.getCommonServiceURL();
                    DomainAuthetificator domainAuthetificator = new DomainAuthetificator(commonServiceURL);
                    DomainUser domainUser = domainAuthetificator.authenticate(login, password);

                    //попробуем еще раз авторизоваться в случаях когда первый раз домен почему-то не отдает авторизацию
                    if (DomainUser.AUTH_FAILED.equals(domainUser.getAuthenticateStatus())) {
                        domainUser = domainAuthetificator.authenticate(login, password);
                    }

                    if (DomainUser.AUTH_FAILED.equals(domainUser.getAuthenticateStatus())) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
                        request.setAttribute("msg_error", "Неправильное имя пользователя или пароль.");
                        dispatcher.forward(request, response);
                    } else {
                        //AuthenticationUtil.log("Пользователь вошел в МАК ПСБ, login:", login);
                        request.getSession().setAttribute("user", domainUser);

//                    String ra_num = ServletUtils.getRaNumFromLoginUser(login);
//                    request.getSession().setAttribute("ra_num", ra_num);
                        response.sendRedirect(request.getContextPath());
                    }
                } catch (URISyntaxException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("msg_error", "Ошибка авторизации. Нерпавильные параметры службы авторизации");
                } catch (Exception ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("msg_error", "Неизвестная ошибка авторизации.");
                }
            }
        }
//            ServletUtils.configureConnections();
//
//            AuthUser authenticate = AuthenticationUtil.authenticate(login, password);
//            if (authenticate.getAuthenticateStatus() == AuthKey.AUTH_FAILED) {
//                RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
//                request.setAttribute("msg_error", "Неправильное имя пользователя или пароль.");
//                dispatcher.forward(request, response);
//            } else {
//                AuthenticationUtil.log("Пользователь вошел в МАК ПСБ, login:", login);
//                request.getSession().setAttribute("user", authenticate);
//
//                String ra_num = ServletUtils.getRaNumFromLoginUser(login);
//                request.getSession().setAttribute("ra_num", ra_num);
//                response.sendRedirect(request.getContextPath());
//            }
        }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
