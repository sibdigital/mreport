/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.utils;

import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
public class MPConversion {

    private static MPConversion INSTANCE = null;

    private static final String ON = "on";
    private static final String OFF = "off";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private MPConversion() {

    }

    public static MPConversion instance() {
        if (INSTANCE == null) {
            INSTANCE = new MPConversion();
        }
        return INSTANCE;
    }

    public boolean toBoolean(String value) {
        boolean result = false;
        int ival = -1;
        if (ON.equalsIgnoreCase(value) == true || TRUE.equalsIgnoreCase(value) == true) {
            result = true;
        } else if (OFF.equalsIgnoreCase(value) == true || FALSE.equalsIgnoreCase(value) == true) {
            result = false;
        } else {
            ival = ConversionUtils.toInteger(value);
            result = ConversionUtils.toBoolean(ival);
        }
        return result;
    }
}
