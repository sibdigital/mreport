/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.p03.common.model.DomainUser;
import ru.p03.makpsb.core.util.AvailabelPeriodUtil;
import ru.p03.vmvp.utils.ConversionUtils;
import ru.p03.makpsb.core.resource.Verificator;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "AvailablePeriodsServlet", urlPatterns = {"/main/available_periods.do"})
public class AvailablePeriodsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean requested = false;
        Map map = new HashMap();
        List<Integer> availableHours = new ArrayList<>();//.availableHours();
        List<Integer> availableMinutes = new ArrayList<>();//AvailabelPeriodUtil.instance().availableMinutes();
        availableHours.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19,20,21,22,23,0));
        availableMinutes.addAll(Arrays.asList(1,4,7,10,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57,59));

//        if (request.getParameter("scheduled_date") != null){
//            Date shedDate = ConversionUtils.toDate(request.getParameter("scheduled_date"));
//
//            List<Integer> availableHours = AvailabelPeriodUtil.instance().availableHours();
//            List<Integer> availableMinutes = AvailabelPeriodUtil.instance().availableMinutes();
//            availableHours.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19,20,21,22,23,0));
//            availableMinutes.addAll(Arrays.asList(1,4,7,10,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57,59));
//
////            List<Integer> availableHours = AvailabelPeriodUtil.instance().availableHours(shedDate);
////            List<Integer> availableMinutes = AvailabelPeriodUtil.instance().availableMinutes(shedDate);
////
////            DomainUser au = getUser(request, response);
////            if (au != null){
////                if (Verificator.instance().isAdmin(au.getLogin())){
////                    availableHours.clear();
////                    availableHours.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19,20,21,22,23,0));
////                }
////            }
//
//            requested = true;
//            map.put("availableHours", availableHours);
//            map.put("availableMinutes", availableMinutes);
//
//        }else if (request.getParameter("dayOfWeek") != null && request.getParameter("dayOfMonth") != null){
//            int empty = -1;
//            int dayOfWeek = ConversionUtils.toInteger(request.getParameter("dayOfWeek"));
//            int dayOfMonth = ConversionUtils.toInteger(request.getParameter("dayOfMonth"));
//
//            List<Integer> availableHours = new ArrayList<>();//.availableHours();
//            List<Integer> availableMinutes = new ArrayList<>();//AvailabelPeriodUtil.instance().availableMinutes();
//            availableHours.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19,20,21,22,23,0));
//            availableMinutes.addAll(Arrays.asList(1,4,7,10,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57,59));
//
//
////            List<Integer> availableHours = AvailabelPeriodUtil.instance().availableHours();
////            List<Integer> availableMinutes = AvailabelPeriodUtil.instance().availableMinutes();
////
////            if (dayOfWeek != empty && dayOfMonth == empty){
////                availableHours = AvailabelPeriodUtil.instance().availableHours(dayOfWeek);
////                availableMinutes = AvailabelPeriodUtil.instance().availableMinutes(dayOfWeek);
////
////            }else if (dayOfWeek == empty && dayOfMonth != empty){
////
////            }else if (dayOfWeek == empty && dayOfMonth == empty){
////
////            }
////
////            DomainUser au = getUser(request, response);
////            if (au != null){
////                if (Verificator.instance().isAdmin(au.getLogin())){
////                    availableHours.clear();
////                    availableHours.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19,20,21,22,23,0));
////                }
// //           }
            
//            requested = true;
//            map.put("availableHours", availableHours);
//            map.put("availableMinutes", availableMinutes);
//        }
        requested = true;
        map.put("availableHours", availableHours);
        map.put("availableMinutes", availableMinutes);
        
        if (requested){
            ObjectMapper mapper = new ObjectMapper();
          
            response.setContentType("application/json; charset=UTF-8");//
            response.setCharacterEncoding("UTF-8");

            String json = mapper.writeValueAsString(map);

            BufferedOutputStream output = null;
            output = new BufferedOutputStream(response.getOutputStream());
            output.write(json.getBytes(Charset.forName("UTF-8")));
            output.close();
        }
    }
    
     protected final DomainUser getUser(HttpServletRequest request, HttpServletResponse response){
        DomainUser au = new DomainUser();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("user");
        if (attribute != null && attribute instanceof DomainUser){
            au = ((DomainUser)attribute);
        }
        return au;
    }

}
