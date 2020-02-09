/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.vmvp.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timofeevan
 */
public class FormatUtils {

    //+скорее всего надо переделывать ибо DateFormat
    //не потокобезопасен см. ThreadSafe
    private static final DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN);
    private static final DateFormat dtf = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.GERMAN);
    private static final DecimalFormat dc = new DecimalFormat("#,##0.00");
    //-

    public static final String YES = "ДА";
    public static final String NO = "НЕТ";

    private static final String M_1 = "Январь";
    private static final String M_2 = "Февраль";
    private static final String M_3 = "Март";
    private static final String M_4 = "Апрель";
    private static final String M_5 = "Май";
    private static final String M_6 = "Июнь";
    private static final String M_7 = "Июль";
    private static final String M_8 = "Август";
    private static final String M_9 = "Сентябрь";
    private static final String M_10 = "Октябрь";
    private static final String M_11 = "Ноябрь";
    private static final String M_12 = "Декабрь";

    private static final String M_0 = "Месяц не определен";

    private static final String[] MONTHS = {M_1, M_2, M_3,
        M_4, M_5, M_6,
        M_7, M_8, M_9,
        M_10, M_11, M_12,};

    public static String formatAsDDMMYYY(Date date) {
        if (date == null) {
            return "";
        }
        return df.format(date);
    }

    public static String formatAsDDMMYYY_HHMMSS(Date date) {
        if (date == null) {
            return "";
        }
        return dtf.format(date);
    }

    public static String formatAsRKK(Double value) {
        if (value == null) {
            value = 0.0;
        }
        return dc.format(value);
    }

    public static String formatAsRKK(BigDecimal value) {
        return dc.format(noNull(value));
    }
    
    public static String formatAs_RkAsv_RKK(BigDecimal value) {
        BigDecimal nnval = noNull(value);
        String sval = nnval.toString().replaceAll("\\.", ",");
        if ("0".equals(sval)){ // 10.05.2016: formatAsRKK тоже работает кстати но непрерывные пробелы смущают
            sval = "0,00";
        }
        return sval;
    }

    public static String noFormat(Integer value) {
        return value == null ? "0" : value.toString();
    }

    public static String noNull(String s) {
        return s == null ? "" : s;
    }

    public static BigDecimal noNull(BigDecimal value) {
        if (value == null) {
            value = BigDecimal.ZERO;
        }
        return value;
    }

    public static String noNull(Object s) {
        return noNull(s, "");
    }

    public static String noNull(Object s, String defaultValue) {
        return s == null ? defaultValue : s.toString();
    }

    public static String noNull(String s, String defaultValue) {
        return s == null ? defaultValue : s;
    }

    public static String firstCharacter(String str) {
        String result = "";
        if (str != null) {
            if (str.length() > 0) {
                result = str.substring(0, 1);
            }
        }
        return result;
    }

    public static String formatAsYESNO(Integer value) {
        boolean asBoolean = ConversionUtils.toBoolean(value);
        return formatAsYESNO(asBoolean);
    }

    public static String formatAsYESNO(boolean value) {
        return value == true ? YES : NO;
    }

    public static String formatAsTwoValue(Integer value, String firstValue, String twoValue) {
        boolean asBoolean = ConversionUtils.toBoolean(value);
        return asBoolean == true ? firstValue : twoValue;
    }

    public static String formatAsTwoValue(Integer value, Integer firstIntValue, Integer twoIntValue, String firstValue, String twoValue) {
        if (Objects.equals(value, firstIntValue)) {
            return firstValue;
        }
        if (Objects.equals(value, twoIntValue)) {
            return twoValue;
        }
        return "---";
    }

    public static String formatAsMonth(int value) {
        return formatAsMonth(value, false);
    }

    public static String formatAsMonth(int value, boolean isStart0) {
        if (isStart0) {
            if (value >= 0 && value < MONTHS.length) {
                return MONTHS[value];
            }
        } else {
            if (value > 0 && value <= MONTHS.length) {
                return MONTHS[value - 1];
            }
        }
        return M_0;
    }

    //восстановление из строк
    public static Integer asInteger(String str) {
        Integer result = 0;
        if (str != null && !str.isEmpty()) {
            try{
                result = Integer.parseInt(str);
            }catch (NumberFormatException ex){
                Logger.getLogger(FormatUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static String deleteLeaderZero(String string){
        String noLeadZero = "";
        try{
            if (string != null){
                noLeadZero = string.replaceFirst("^0+(?!$)", "");
            }
        }catch (Exception ex){
            Logger.getLogger(FormatUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noLeadZero;
    }

    public static String formatAsFullFIO(String fam, String im, String otc) {
        return noNull(fam, "") + " " + noNull(im, "") + " " + noNull(otc, "");
    }

    public static String formatAsMMYYYY(Integer month, Integer year) {
        if (month != null && year != null) {
            String ms = month.toString();
            String formatMs = ms.length() == 1 ? ("0" + ms) : ms;
            return formatMs.concat(".").concat(year.toString());
        } else {
            return null;
        }
    }

    public static String str_x(String s, int maxLength) {
        if (s != null) {
            if (s.length() > maxLength) {
                return s.substring(0, maxLength);
            } else {
                return s;
            }
        } else {
            return "";
        }
    }

}
