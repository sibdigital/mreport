/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
public class WebConversionUtil {
    
    private static final String ON = "on";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    
    private static WebConversionUtil INSTANCE = null;
    
    private WebConversionUtil(){
        
    }
    
    public static WebConversionUtil instance(){
        if (INSTANCE == null){
            INSTANCE = new WebConversionUtil();
        }
        return INSTANCE;
    }
    
    public boolean toBoolean (String value){
        return MPConversion.instance().toBoolean(value);
//        boolean result = false;
//        int ival = -1;
//        if (ON.equalsIgnoreCase(value) == false && TRUE.equalsIgnoreCase(value) == false && FALSE.equalsIgnoreCase(value) == false){
//            ival = ConversionUtils.toInteger(value);
//        }        
//        if (ival == -1){
//            result = ON.equalsIgnoreCase(value) || TRUE.equalsIgnoreCase(value);
//        }else{
//            result = ConversionUtils.toBoolean(ival);
//        }
//        return result;
    }
    
    public Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    public Map<String, Object> getShedulerParams (HttpServletRequest request){
        Date reqScheduledDate = ConversionUtils.toDate(request.getParameter("scheduled_date"));
        String reqHour = request.getParameter("hour");
        String reqMin = request.getParameter("min"); 
        boolean reqAllRaion = toBoolean(request.getParameter("all_raion"));
        Map<String, Object> map = new HashMap<>();
        map.put("scheduled_date", reqScheduledDate);
        map.put("hour", reqHour);
        map.put("min", reqMin);
        map.put("all_raion", reqAllRaion);
        return map;
    }
}
