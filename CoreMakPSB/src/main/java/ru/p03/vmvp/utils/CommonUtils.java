/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.vmvp.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.Days;


/**
 *
 * @author timofeevan
 */
public class CommonUtils {
    
    public static BigDecimal DIV_100 = new BigDecimal(100);
    
    protected static final String K_1 = "03";
    protected static final String K_2 = "06";
    protected static final String K_3 = "09";
    protected static final String K_4 = "12";
    
    private static final String[] PERIODS = {K_1, K_2, K_3, K_4};
    private static final Integer[] NUM_PERIODS = {1, 2, 3, 4};
    
    private static final int[] MONTHS_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    
    private static final int[] MONTHS_0 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    
    public static int[] months(){
        return MONTHS_1;
    }
    
    public static int[] months_0(){
        return MONTHS_0;
    }
        
    public static Integer year (Date date){
         Integer year = 0;
         if (date != null){
            DateTime dateTime = new DateTime(date.getTime());
            year = dateTime.year().get();
         }
         return year;
    }
    
    public static boolean isToday(Date date){
        boolean result = false;
        if (date != null){
            Date today_endDay = CommonUtils.endDay(now());
            Date date_endDay = CommonUtils.endDay(date);
            result = today_endDay.compareTo(date_endDay) == 0;
        }
        return result;
    }
    
    public static Integer monthOfYear (Date date){
         Integer monthOfYear = 0;
         if (date != null){
            DateTime dateTime = new DateTime(date.getTime());
            monthOfYear = dateTime.monthOfYear().get();
         }
         return monthOfYear;
     }
    
    public static Integer dayOfMonth (Date date){
         Integer dayOfMonth = 0;
         if (date != null){
            DateTime dateTime = new DateTime(date.getTime());
            dayOfMonth = dateTime.dayOfMonth().get();
         }
         return dayOfMonth;
     }
    
    public static Object[] noNulls (Object ... objects){
        List<Object> list = new ArrayList<>();
        for (Object object : objects) {
            if (object != null){
                list.add(object);
            }
        }        
        return list.toArray();
    }

    public static int compareToZERO (BigDecimal value){
        return compareTo (value, BigDecimal.ZERO);
    }
    public static int compareTo (BigDecimal value1, BigDecimal value2){
        int result = 0;
        if (value1 == null && value2 != null){
            result = -1;
        }else if (value1 != null && value2 == null){
            result = 1;
        }else if (value1 != null && value2 != null){
            result = value1.compareTo(value2);
        }
        return result;
    }
    /**
     *
     * @param value1 the value of value1
     * @param value2 the value of value2
     */
    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        BigDecimal result = BigDecimal.ZERO;
        value1 = noNull_BigDecimal(value1);
        value2 = noNull_BigDecimal(value2);
        //if (value1 != null && value2 != null) {
            result = value1.subtract(value2);
        //}
        return result;
    }
    
    public static BigDecimal subtract(String value1, String value2) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            BigDecimal bvalue1 = noNull_BigDecimal(ConversionUtils.convertToBigDecimal(value1));
            BigDecimal bvalue2 = noNull_BigDecimal(ConversionUtils.convertToBigDecimal(value2));
            result = bvalue1.subtract(bvalue2);
        } catch (ParseException ex) {
            Logger.getLogger(CommonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     *
     * @param value the value of value
     * @param percent the value of percent
     */
    public static BigDecimal multiply(BigDecimal value, BigDecimal percent) {
        BigDecimal result = BigDecimal.ZERO;
        value = noNull_BigDecimal(value);
        percent = noNull_BigDecimal(percent);
        //if (value != null && percent != null) {
            result = value.multiply(percent);
        //}
        return result;
    }
    
    /**
     *
     * @param value the value of value
     * @param percent the value of percent
     * @see Не учитывается масштаб scale. Необходимы изменения
     */
    public static BigDecimal divide(BigDecimal value, BigDecimal percent) {
        //Не учитывается масштаб scale. Необходимы изменения
        BigDecimal result = BigDecimal.ZERO;
        value = noNull_BigDecimal(value);
        percent = noNull_BigDecimal(percent);
        //if (value != null && percent != null) {
            result = value.divide(percent);
        //}
        return result;
    }
    
    public static BigDecimal divide(BigDecimal value, BigDecimal percent, int scale, int roundingMode) {
        //Не учитывается масштаб scale. Необходимы изменения
        BigDecimal result = BigDecimal.ZERO;
        value = noNull_BigDecimal(value);
        percent = noNull_BigDecimal(percent);
        //if (value != null && percent != null) {
            result = value.divide(percent,scale, roundingMode);
        //}
        return result;
    }
    
    public static BigDecimal divide(BigDecimal value, BigDecimal percent, MathContext mc) {
        //Не учитывается масштаб scale. Необходимы изменения
        BigDecimal result = BigDecimal.ZERO;
        value = noNull_BigDecimal(value);
        percent = noNull_BigDecimal(percent);
        //if (value != null && percent != null) {
            result = value.divide(percent, mc);
        //}
        return result;
    }

    /**
     *
     * @param value1 the value of value1
     * @param value2 the value of value2
     */
    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        BigDecimal result = BigDecimal.ZERO;
        value1 = noNull_BigDecimal(value1);
        value2 = noNull_BigDecimal(value2);
        //if (value1 != null && value2 != null) {
            result = value1.add(value2);
        //}
        return result;
    }
    
    private static BigDecimal noNull_BigDecimal(BigDecimal value){
        return (value != null ? value : BigDecimal.ZERO);
    }
    
    public static BigDecimal add(BigDecimal ... values) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal v : values) {
            result = add(result, v);
        }
        return result;
    }
    
    public static BigDecimal add(List<BigDecimal> values) {
        if (values.size() > 0){
            BigDecimal[] toArray = new BigDecimal[values.size()];
            toArray = values.toArray(toArray);
            return add(toArray);
        }
        return  BigDecimal.ZERO;
    }   
    
    public static BigDecimal addStrings(List<String> values) {
        if (values.size() > 0){
            BigDecimal[] toArray = new BigDecimal[values.size()];
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) != null){
                    toArray[i] = new BigDecimal(values.get(i));
                }
            }
            return add(toArray);
        }
        return  BigDecimal.ZERO;
    }   
    
    public static<T> boolean contains (T value, T ... values){
        List<T> list = Arrays.asList(values);
        return list.contains(value);
    }
    
    public static Date now(){
        return new Date(System.currentTimeMillis());
    }
    
    public static Date now(long millis){
        return new Date(millis);
    }
    
    public static boolean is_1_monthKvartal(int month){
        return contains(month, 1, 4, 7, 10);
    }
    
    public static boolean is_2_monthKvartal(int month){
        return contains(month, 2, 5, 8, 11);
    }
    
    public static boolean is_3_monthKvartal(int month){
        return contains(month, 3, 6, 9, 12);
    }
    
    public static int period_of_month(int month){
        int period = -1;      
        if (months_1_kvartal().contains(month) == true){
            period = NUM_PERIODS[0];
        } else if (months_2_kvartal().contains(month) == true){
            period = NUM_PERIODS[1];
        }else if (months_3_kvartal().contains(month) == true ){
            period = NUM_PERIODS[2];
        }else if (months_4_kvartal().contains(month) == true){
            period = NUM_PERIODS[3];
        }
        return period;
    }
    
    public static String period_of_monthAsRSV(int month){
        int period = period_of_month(month) - 1;
        if (period >= 0){
            return PERIODS[period];
        }
        return "-";
    }
    
    public static List<Integer> months_1_kvartal(){
        Integer[] m = {1,2,3};
        List<Integer> list = Arrays.asList(m);
        return list;
    }
    
    public static List<Integer> months_2_kvartal(){
        Integer[] m = {4,5,6};
        List<Integer> list = Arrays.asList(m);
        return list;
    }
    
    public static List<Integer> months_3_kvartal(){
        Integer[] m = {7,8,9};
        List<Integer> list = Arrays.asList(m);
        return list;
    }
    
    public static List<Integer> months_4_kvartal(){
        Integer[] m = {10,11,12};
        List<Integer> list = Arrays.asList(m);
        return list;
    }

    public static boolean isDigit(String str){
        boolean result = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            result &= Character.isDigit(ch);
            if (result == false){
                break;
            }
        }
        return result;       
    }
    
    public static String from_number(int number, String ... strings){
        if (number < strings.length && number >= 0){
            return strings[number];
        }
        return "---";
    }
    
    private static boolean object_emptyOrNull(Object value){
        boolean result = false;
        if (value == null){
            result = true;
        }
        return result;
    }
    
    public static boolean emptyOrNull(String value){
        boolean result = object_emptyOrNull(value);
        if (result == false){
            result = value.isEmpty();
        }
        return result;
    }
    
    public static String upper(String string){
        String result = "";
        if (string != null){
            result = string.toUpperCase();
        }
        return result;
    }
    
    public static int getKvartal(Date date){
        Integer monthOfYear = monthOfYear(date);
        return period_of_month(monthOfYear);
    }
    
    /**
     *
     * @param value
     * @see Не учитывается масштаб scale. Необходимы изменения
     * @return
     */
    public static BigDecimal divide100(BigDecimal value){
        //Не учитывается масштаб scale. Необходимы изменения
        return divide(value, DIV_100);
    }
    
    public static int days (Date dateBegin, Date dateEnd){
        if (dateBegin == null || dateEnd == null){
            return 0;
        }
        DateTime dtb = new DateTime(dateBegin.getTime());
        DateTime dte = new DateTime(dateEnd.getTime());
        Days daysBetween = Days.daysBetween(dtb, dte);
        int days = Math.abs(daysBetween.getDays());
        return days;
    }
    
    public static Date endDay(Date date){
        DateTime dt = new DateTime(date.getTime());
        DateTime withTime = dt.withTime(23, 59, 59, 999);
        return withTime.toDate();
    }
    
    public static Date endDay(Calendar calendar){
        return endDay(calendar.getTime());
    }
    
    public static Calendar endDayCalendar(Calendar calendar){
        Calendar db = Calendar.getInstance();
        db.setTime(endDay(calendar.getTime()));
        return db;
    }

    public static Date addDay(Date date, int day){
        DateTime dt = new DateTime(date.getTime());
        DateTime withTime = dt.plusDays(day);
        return withTime.toDate();
    }
    
    public static Date minusDay(Date date, int day){
        DateTime dt = new DateTime(date.getTime());
        DateTime withTime = dt.minusDays(day);
        return withTime.toDate();
    }
    
    public static Date beginDay(Date date){
        DateTime dt = new DateTime(date.getTime());
        DateTime withTime = dt.withTime(0, 0, 0, 0);
        return withTime.toDate();
    }
    
    public static Date beginDay(Calendar calendar){
        return beginDay(calendar.getTime());
    }
    
    public static Calendar beginDayCalendar(Calendar calendar){
        Calendar db = Calendar.getInstance();
        db.setTime(beginDay(calendar.getTime()));
        return db;
    }
    
    public static Date endYear(int year){
        DateTime dt = new DateTime(year, 12, 31, 23, 59, 59, 999);
        return dt.toDate();
    }
    
    public static Date beginYear(int year){
        DateTime dt = new DateTime(year, 1, 1, 0, 0, 0, 0);
        return dt.toDate();
    }
    
    public static Date endYear(){
        return endYear(CommonUtils.year(CommonUtils.now()));
    }
    
    public static Date beginYear(){
        return beginYear(CommonUtils.year(CommonUtils.now()));
    }
    
    public static Date beginNextMonth(Date date) {
        DateTime dt = new DateTime(date.getTime());
        DateTime plusMonth = dt.plusMonths(1);
        plusMonth = plusMonth.withDate(plusMonth.getYear(), plusMonth.getMonthOfYear(), 1);
        return plusMonth.toDate();
    }
    
    public static Date endCurrentMonth(Date date) {
        DateTime dt = new DateTime(date.getTime());
        DateTime plusMonth = dt.plusMonths(1);
        plusMonth = plusMonth.withDate(plusMonth.getYear(), plusMonth.getMonthOfYear(), 1).minusDays(1);
        return plusMonth.toDate();
    }
    
    public static Map.Entry<String, Object> pair(String s, Object o){
        ImmutablePair<String, Object> ip = new ImmutablePair<>(s, o);
        return ip;
    }
    
    public static Map.Entry<Integer, Object> pair(Integer i, Object o){
        ImmutablePair<Integer, Object> ip = new ImmutablePair<>(i, o);
        return ip;
    }
    
    public static<T, K> Map.Entry<T, K> pair(T s, K o){
        ImmutablePair<T, K> ip = new ImmutablePair<>(s, o);
        return ip;
    }
    
    public static boolean betweenDates(Integer kvartal, Integer year, Date dateBegin, Date dateEnd){
        Integer yearBegin = year(dateBegin);
        Integer yearEnd = year(dateEnd);
        Integer kvBegin = getKvartal(dateBegin);
        Integer kvEnd = getKvartal(dateEnd);
        boolean result = ((year >= yearBegin && year <= yearEnd) && (kvartal >= kvBegin && kvartal <= kvEnd))
                || ((year < yearEnd && year >= yearBegin) && (kvartal >= kvBegin));
        return result;
    }
}
