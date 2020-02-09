/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.vmvp.utils;

import java.util.Objects;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author timofeevan
 */
public class ConversionUtils {

    private static final NumberFormat GERMAN_NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMAN);
    //private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN); // исправить непотокобезопасно!

    public static boolean toBoolean(Integer value) {
        return value == null ? false : value > 0;
    }
    
    public static boolean toBoolean(String value) {
        return "true".equalsIgnoreCase(value);
    }

    public static int toInteger(boolean value) {
        return value ? 1 : 0;
    }

    public static int toInteger(Object value) {
        int parsed = -1;
        if (value != null) {
            String s = value.toString();
            try {
                parsed = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                Logger.getLogger(ConversionUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parsed;
    }
    
    public static int toInteger(Boolean value) {
        int parsed = value == true ? 1 : 0;
        return parsed;
    }
    
    public static int toInteger(BigDecimal value) {
        if (value != null){
            return value.intValue();
        }
        return 0;
    }

    public static boolean toBoolean(Object value) {
        if (value instanceof Integer) {
            Integer iv = (Integer)value;
            return toBoolean(iv);
        }
        if (value instanceof String) {
            String sv = (String)value;
            return toBoolean(sv);
        }
        return value == null ? false : (boolean) value;
    }

    public static String getAsFamiliaIO(String fam, String im, String otc) {
        String result = FormatUtils.noNull(fam) + " " + FormatUtils.firstCharacter(im) + "."
                + FormatUtils.firstCharacter(otc);
        return result;
    }

    public static String getAsFamiliaIO(Object fam, Object im, Object otc) {
        return getAsFamiliaIO(FormatUtils.noNull(fam), FormatUtils.noNull(im), FormatUtils.noNull(otc));
    }

    public static BigDecimal convertToBigDecimal(String string) throws ParseException {
        BigDecimal result = BigDecimal.ZERO;
        if (string != null) {
            if (!string.isEmpty()) {
                //Скорее всего парсер GERMAN_NUMBER_FORMAT не работал из-за пробелов между разрядами
                //пока используется устаревший toBigDecimal из ПК ВП ВМ
                Number parsedNumber = GERMAN_NUMBER_FORMAT.parse(string.replaceAll(" ", ""));
                if (parsedNumber != null) {
                    result = toBigDecimal(string);
                }
            }
        }
        return result;
    }

    public static BigDecimal convertToBigDecimal(Object value) {
        BigDecimal result = BigDecimal.ZERO;
        if (value != null) {
            String s = value.toString();
            try {
                result = convertToBigDecimal(s);
            } catch (ParseException ex) {
                Logger.getLogger(ParseException.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    protected static BigDecimal toBigDecimal(Object value) {
        BigDecimal b = BigDecimal.ZERO;
        if (value != null) {
            if (value instanceof String) {
                String str = ((String) value).replaceAll(",", ".").replaceAll(" ", "");
                if (!str.isEmpty()) {
                    b = new BigDecimal(str);
                }
            } else if (value instanceof Object) {
                String str = value.toString().replaceAll(",", ".");
                if (!str.isEmpty()) {
                    b = new BigDecimal(str);
                }
            } else if (value instanceof Double) {
                b = BigDecimal.valueOf((Double) value);
            } else if (value instanceof BigDecimal) {
                b = (BigDecimal) value;
            }
        }
        return b;
    }

    public static Object get_fromArray(Object[] array, int index) {
        return tget_fromArray(array, index);
    }

    public static <T> T tget_fromArray(Object[] array, int index) {
        if (index > -1) {
            if (array != null && array.length > index) {
                return (T) array[index];
            }
        }
        return null;
    }

    public static Date toDate(String dateAsDDMMYYYY) {
        Date parsed = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        try {
            parsed = formatter.parse(dateAsDDMMYYYY);
        } catch (ParseException ex) {
            Logger.getLogger(ConversionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parsed;
    }

    public static Timestamp toTimestamp(Date date) {
        Timestamp t = null;
        if (date != null) {
            long time = date.getTime();
            t = new Timestamp(time);
        }
        return t;
    }

    public static Timestamp toDate(Timestamp t) {
        Date d = null;
        if (t != null) {
            long time = t.getTime();
            d = new Date(time);
        }
        return t;
    }
    
    public static Long toLong (String str){
        Long result = 0L;
        try{
            result = Long.parseLong(str);
        }catch (NumberFormatException ex){
            Logger.getLogger(ConversionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xml) {
        Date result = CommonUtils.beginDay(CommonUtils.now());
        if (xml != null) {
            result = xml.toGregorianCalendar().getTime();
        }
        return result;
    }
    
    public static Date xmlGregorianCalendarToDate(String xml) {
        Date result = CommonUtils.beginDay(CommonUtils.now());
        if (xml != null) {
            result = toDate(xml);
        }
        return result;
    }
    
    public static Date xmlGregorianCalendarToDate_nullable(XMLGregorianCalendar xml) {
        Date result = null;
        if (xml != null) {
            result = xml.toGregorianCalendar().getTime();
        }
        return result;
    }
    
    public static Date xmlGregorianCalendarToDate_nullable(String xml) {
        Date result = null;
        if (xml != null) {
            result = toDate(xml);
        }
        return result;
    }

    public static XMLGregorianCalendar dateToXmlGregorianCalendar(Date date) {
        try {
            XMLGregorianCalendar xml = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            if (date != null) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(date);
                xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            }
            return xml;
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(ConversionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String formatRegNum(String regnum){
        String result = regnum;
        if (regnum != null){
            if (regnum.length() == 12 && regnum.contains("-") == false){
                result = regnum.substring(0, 3) + "-" + regnum.substring(3, 6) + "-" + regnum.substring(6);
            }
        }
        return result;
    }
    
    public static String formatPeriod(Integer period){
        String result = period != null ? period.toString() : "";
        if (Objects.equals(period, 3) == true){
            result = "03";
        } else if (Objects.equals(period, 6) == true){
            result = "06";
        } else if (Objects.equals(period, 9) == true){
            result = "09";
        } else if (Objects.equals(period, 12) == true){
            result = "12";
        }
        return result;
    }
}
