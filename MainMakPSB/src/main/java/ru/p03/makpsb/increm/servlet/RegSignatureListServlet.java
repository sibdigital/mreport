/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.servlet.MPServlet;
import ru.p03.makpsb.core.util.RaionUtil;
import ru.p03.makpsb.increm.model.ClsSignature;
import ru.p03.makpsb.increm.model.ClsSignatureType;
import ru.p03.makpsb.increm.model.RegDetectedSignature;
import ru.p03.makpsb.increm.model.RegDetectedSignatureAdapter;
import ru.p03.makpsb.increm.model.SignatureTiming;
import ru.p03.makpsb.increm.repository.ClsssifierRepository;
import ru.p03.makpsb.increm.repository.RegDetectedSignatureRepository;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "RegSignatureListServlet1", urlPatterns = {"/main/reg_signature_list.do"})
public class RegSignatureListServlet extends MPServlet {

    private static final long serialVersionUID = 1L;

    private List<ClsSignatureType> listSignatureType;
    private List<ClsSignature> listSignature;

    final String PAGE = "/jsp/increm/reg_signature_list.jsp";
    final String ELEM_PAGE = "/jsp/increm/signature.jsp";
    final String LIST = "regSignList";
    final String ELEMENT = "signatureElement";

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
        if ("show".equals(request.getParameter("action")) == true) {

            Long signatureId = 0L;

            if (request.getParameter("sgn_id") != null) {
                if (!request.getParameter("sgn_id").isEmpty()) {
                    signatureId = Long.parseLong(request.getParameter("sgn_id"));
                }
            }

            Long signatureTypeId = null;
            if (request.getParameter("type_id") != null) {
                if (!request.getParameter("type_id").isEmpty()) {
                    signatureTypeId = Long.parseLong(request.getParameter("type_id"));
                }
            }

            // Если уже провалились и выбрали какой-либо фильтр  
            String snils = request.getParameter("snils") != null ? request.getParameter("snils") : "";
            Long raion = request.getParameter("raion") != null ? Integer.parseInt(request.getParameter("raion")) : 0L;   // при первой загрузке raion присваиваем null, при выборе пустого района на форме raion = 0          
            Long r = RaionUtil.instance().byNomer(raion.intValue()) == null ? 0 : RaionUtil.instance().byNomer(raion.intValue()).getId();

            if (request.getParameter("sel_sng_id") != null) {
                if (!request.getParameter("sel_sng_id").isEmpty()) {
                    signatureId = Long.parseLong(request.getParameter("sel_sng_id"));
                }
            }

            // Если выбрали sgn_id (район и снилс не важен), то используем SLICE_DETECTED_SIGNATURE_ON_SIGNATURE. (формат СНИЛС проверили на форме).
            RegDetectedSignatureRepository regRepo = new RegDetectedSignatureRepository();
            List<RegDetectedSignature> list = new ArrayList<>();
            if (signatureId != 0) {    // не при первой загрузке                 
                list = regRepo.findRegDetectedSignatureEntities(signatureId, r, snils, new Date(), true);
            }else{
                // Если не выбрали sgn_id, то используем SLICE_DETECTED_SIGNATURE_ON_TYPE.
                list = regRepo.findRegDetectedSignatureEntitiesOnType(signatureTypeId, r, snils, new Date(), true);
            }
            
            ClsssifierRepository clsassifierRepo = new ClsssifierRepository();
            listSignature = clsassifierRepo.getClsSignature(signatureTypeId);

            request.setAttribute("listSignature", listSignature);

            request.setAttribute("raions", RaionUtil.instance().raions());
            request.setAttribute(LIST, RegDetectedSignatureAdapter.build(list));
            request.setAttribute("sgn_id", signatureId);
            request.setAttribute("type_id", signatureTypeId);
            request.setAttribute("raion", raion);
            request.setAttribute("sel_sng_id", signatureId);  // чтобы выбор остался а не обнулялся при применении фильтра
            request.setAttribute("snils", snils);  // чтото с input не получилось
        }

        //request.setAttribute(LIST, signList);
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

}
