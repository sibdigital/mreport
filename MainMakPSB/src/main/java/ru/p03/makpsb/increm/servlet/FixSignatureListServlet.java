/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.p03.makpsb.increm.model.RegDetectedSignature;
import ru.p03.makpsb.increm.model.RegDetectedSignatureAdapter;
import ru.p03.makpsb.increm.repository.ClsssifierRepository;
import ru.p03.makpsb.increm.repository.RegDetectedSignatureRepository;
import ru.p03.vmvp.utils.FormatUtils;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "FixSignatureListServlet", urlPatterns = {"/main/fix_signature.do"})
public class FixSignatureListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RegDetectedSignatureRepository repo;
    private ClsssifierRepository classifierRepo;

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
        repo = new RegDetectedSignatureRepository();
        classifierRepo = new ClsssifierRepository();
        //setDefaultExecution(true);
        //setReportClass(UhodSverReport.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("fix".equals(request.getParameter("action")) == true) {
            Long id = request.getParameter("id") != null ? Long.parseLong(request.getParameter("id")) : null;
            if (id != null) {
                try {
                    RegDetectedSignature rds = repo.findRegDetectedSignature(id);
                    rds.setTimeFix(new Date());
                    repo.edit(rds, true);
                    request.setAttribute("status", "ok");
                } catch (Exception ex) {
                    request.setAttribute("status", "fail");
                    Logger.getLogger(FixSignatureListServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if ("info".equals(request.getParameter("action")) == true) {
            Long id = request.getParameter("id") != null ? Long.parseLong(request.getParameter("id")) : null;
            if (id != null) {
                try {
                    RegDetectedSignature rds = repo.findRegDetectedSignature(id);
                    RegDetectedSignatureAdapter ards = new RegDetectedSignatureAdapter(rds);

                    List<RegDetectedSignature> fnd = repo.findRegDetectedSignatureSnils(ards.getSnils(), new Date(), true);
                    //response.reset();
                    response.setContentType("application/json; charset=UTF-8");//
                    response.setCharacterEncoding("UTF-8");

                    ObjectMapper mapper = new ObjectMapper();

                    Map map = new HashMap();
                    List addMap = new ArrayList();
                    for (RegDetectedSignature irds : fnd) {

                        RegDetectedSignatureAdapter iards = new RegDetectedSignatureAdapter(irds);
                        Map m = new HashMap<>();
                        fillMap(m, iards);
                        addMap.add(m);
                    }

                    fillMap(map, ards);
                    map.put("description", ards.getIdSignature().getDescription());
                    map.put("errors", addMap);

                    String json = mapper.writeValueAsString(map);

                    BufferedOutputStream output = null;
                    output = new BufferedOutputStream(response.getOutputStream());
                    output.write(json.getBytes(Charset.forName("UTF-8")));
                    output.close();
                } catch (Exception ex) {
                    request.setAttribute("status", "fail");
                    Logger.getLogger(FixSignatureListServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void fillMap(Map map, RegDetectedSignatureAdapter ards) {
        map.put("id", ards.getId());
        map.put("timeDetect", FormatUtils.formatAsDDMMYYY_HHMMSS(ards.getTimeDetected()));
        map.put("signatureName", ards.getIdSignature().getName());
        map.put("signatyreTypeName", ards.getIdSignature().getIdSignatureType().getName());
        map.put("raionName", ards.getRaion().getName());
        map.put("snils", ards.getSnils());
        map.put("fio", ards.getFio());
        map.put("dateBorn", FormatUtils.formatAsDDMMYYY(ards.getDateBorn()));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
