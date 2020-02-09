/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.p03.common.model.DomainUser;
import ru.p03.makpsb.core.entity.service.ClsStandartFormingPeriod;
import ru.p03.makpsb.core.entity.service.controller.ClsssifierRepository;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "ClassifierServlet", urlPatterns = {"/main/classifier.do"})
public class ClassifierServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClsssifierRepository clsssifierRepository;

    //private ClsStandartFormingPeriod
    @Override
    public void init() throws ServletException {
        clsssifierRepository = new ClsssifierRepository();
    }

    private ClsssifierRepository getClsssifierRepository() {
        if (clsssifierRepository == null) {
            clsssifierRepository = new ClsssifierRepository();
        }
        return clsssifierRepository;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classifierName = request.getParameter("classifier");
        Map map = new HashMap();

        if ("ClsStandartFormingPeriod".equals(classifierName)) {
            List<ClsStandartFormingPeriod> list = getClsssifierRepository().getStandartFormingPeriod();
            map.put("classifier_list", list);
        }

        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");//
        response.setCharacterEncoding("UTF-8");

        String json = mapper.writeValueAsString(map);

        BufferedOutputStream output = null;
        output = new BufferedOutputStream(response.getOutputStream());
        output.write(json.getBytes(Charset.forName("UTF-8")));
        output.close();
    }

    protected final DomainUser getUser(HttpServletRequest request, HttpServletResponse response) {
        DomainUser au = new DomainUser();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("user");
        if (attribute != null && attribute instanceof DomainUser) {
            au = ((DomainUser) attribute);
        }
        return au;
    }

}
