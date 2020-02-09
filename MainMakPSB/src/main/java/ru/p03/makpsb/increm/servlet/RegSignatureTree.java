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
import ru.p03.makpsb.increm.model.ClsSignature;
import ru.p03.makpsb.increm.model.ClsSignatureType;
import ru.p03.makpsb.increm.model.SignatureTiming;
import ru.p03.makpsb.increm.repository.ClsssifierRepository;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "RegSignatureTree", urlPatterns = {"/main/reg_signature_tree.do"})
public class RegSignatureTree extends MPServlet {

    private static final long serialVersionUID = 1L;

    final String PAGE = "/jsp/increm/reg_signature_tree.jsp";
    final String ELEM_PAGE = "/jsp/increm/reg_signature.jsp";
    final String LIST = "signList";
    final String ELEMENT = "signatureElement";

    private List<ClsSignatureType> listSignatureType;
    private List<ClsSignature> listSignature;

    protected String page() {
        return PAGE;
    }

//    @Override
//    protected String successMessage(){
//        return "Выборка сформирована";
//    }
    @Override
    public void init() throws ServletException {
        //setDefaultExecution(true);
        //setReportClass(UhodSverReport.class);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ClsssifierRepository clsassifierRepo = new ClsssifierRepository();
        listSignatureType = clsassifierRepo.getClsSignatureType();
        listSignature = clsassifierRepo.getClsSignature();
        request.setAttribute("listSignatureType", listSignatureType);
        request.setAttribute("listSignature", listSignature);
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

//    @Override
//    protected AbstractReport prepareReport(HttpServletRequest request, HttpServletResponse response) {
//        return null;
//    }
}
