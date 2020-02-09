/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.makpsb.core.servlet.MPServlet;
import ru.p03.makpsb.increm.model.SignatureTiming;
import ru.p03.makpsb.increm.repository.ClsssifierRepository;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "SignatureListServlet", urlPatterns = {"/main/signature_list.do"})
public class SignatureListServlet extends MPServlet {

    private static final long serialVersionUID = 1L;
    
    final String PAGE = "/jsp/increm/signature_list.jsp";
    final String ELEM_PAGE = "/jsp/increm/signature.jsp";
    final String LIST = "signList";
    final String ELEMENT = "signatureElement";
    
    protected String page(){
        return PAGE;
    }

    @Override
    public void init() throws ServletException {
        //setDefaultExecution(true);
        //setReportClass(UhodSverReport.class);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       ClsssifierRepository clsassifierRepo = new ClsssifierRepository();
       List<SignatureTiming> signList = clsassifierRepo.getSignatureTiming();
       request.setAttribute(LIST, signList);
       selfForward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
