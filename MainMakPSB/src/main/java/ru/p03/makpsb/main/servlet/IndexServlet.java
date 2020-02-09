/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.servlet;

import ru.p03.common.model.DomainUser;
import ru.p03.makpsb.main.utils.ServletUtils;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 003-0818
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"/main/index.do"})
public class IndexServlet extends HttpServlet {

    final String PAGE = "/index.jsp";
    final String DYN = "/main/dynreport_list.do";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        if (request.getSession().getAttribute("user") == null) {
//            response.sendRedirect(request.getContextPath() + "/login.do");
//            return;
//        }
        if (ServletUtils.isNoAuth()) {
            DomainUser domainUser = new DomainUser();
            domainUser.setLogin("");
            domainUser.setPassword("");
            domainUser.setAuthenticateStatus(DomainUser.AUTH_OK);
            domainUser.setHash("");
            domainUser.setAttributes(new HashMap<>());
            request.getSession().setAttribute("user", domainUser);

        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE);
        dispatcher.forward(request, response);
    }

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
