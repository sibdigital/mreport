/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.servlet;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.util.RaionUtil;
import ru.p03.makpsb.dynreport.ActualDynReport;
import ru.p03.makpsb.dynreport.ActualDynReportJpaController;
import ru.p03.makpsb.dynreport.RaionPeriodDynReport;
import ru.p03.makpsb.main.servlet.MPReportServlet;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "DynReporElementServlet", urlPatterns = {"/main/dynreport_element.do"})
public class DynReporElementServlet extends MPReportServlet {
    
    final String PAGE = "/jsp/element/dynreport.jsp";
    final String ELEMENT = "actualDynReport";
    
    private ActualDynReport actualDynReport = new ActualDynReport();
    
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
        setReportClass(RaionPeriodDynReport.class);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if ("open".equals(request.getParameter("action")) == true){
            String sid = request.getParameter("id");
            ActualDynReportJpaController adrjc = new ActualDynReportJpaController();           
            long id = ConversionUtils.toInteger(sid);
            
            actualDynReport = adrjc.findActualDynReport(id);
//            if (actualDynReport != null){
//                request.setAttribute(ELEMENT, actualDynReport);
//                RequestDispatcher dispatcher = request.getRequestDispatcher(ELEM_PAGE);
//                dispatcher.forward(request, response);
//            }
        }
        
        if (actualDynReport != null){
            //request.setAttribute("raions", RaionUtil.instance().raions());
            request.setAttribute("adn_name", actualDynReport.getViewName());
        }
        
        processCreate(request, response);      
        request.setAttribute("raions", RaionUtil.instance().raions());
        selfForward(request, response);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    protected AbstractReport prepareReport(HttpServletRequest request, HttpServletResponse response) {
        Date dateBegin = ConversionUtils.toDate(request.getParameter("begin_date"));
        Date dateEnd = ConversionUtils.toDate(request.getParameter("end_date"));
        Raion raion = RaionUtil.instance().byNomer(request.getParameter("raion"));
        
        RaionPeriodDynReport report = new RaionPeriodDynReport(actualDynReport);
        report.setDateBegin(dateBegin);
        report.setDateEnd(dateEnd);
        report.setRaion(raion);
        
        setReportFilePath(report, actualDynReport.getViewName(), ".xls");
        return report;
    }
}
