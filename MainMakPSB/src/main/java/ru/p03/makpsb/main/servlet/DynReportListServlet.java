/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.p03.common.model.DomainUser;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.resource.Verificator;
import ru.p03.makpsb.dynreport.ActualDynReport;
import ru.p03.makpsb.dynreport.ActualDynReportJpaController;
import ru.p03.makpsb.main.servlet.MPReportServlet;
import ru.p03.makpsb.main.utils.ServletUtils;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "DynReportListServlet", urlPatterns = {"/main/dynreport_list.do"})
public class DynReportListServlet extends MPReportServlet {
    
    final String PAGE = "/jsp/list/dynreport_list.jsp";
    final String ELEM_PAGE = "/jsp/element/dynreport.jsp";
    final String LIST = "actualDynReportList";
    final String ELEMENT = "actualDynReport";
    
    @Override
    protected String page(){
        return PAGE;
    }
    
    @Override
    protected String successMessage(){
        return "Выборка сформирована";
    }
    
    @Override
    public void init() throws ServletException {
        //setDefaultExecution(true);
        //setReportClass(UhodSverReport.class);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (ServletUtils.isNoAuth()) {
            DomainUser domainUser = new DomainUser();
            domainUser.setLogin("");
            domainUser.setPassword("");
            domainUser.setAuthenticateStatus(DomainUser.AUTH_OK);
            domainUser.setHash("");
            domainUser.setAttributes(new HashMap<>());
            request.getSession().setAttribute("user", domainUser);
        }
       ActualDynReportJpaController adrjc = new ActualDynReportJpaController();
       List<ActualDynReport> actualDynReportList = adrjc.findActualDynReportEntities(getUser(request, response).getLogin(), Verificator.instance().operationExecuteCode());
       request.setAttribute(LIST, actualDynReportList);
       selfForward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    protected AbstractReport prepareReport(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
