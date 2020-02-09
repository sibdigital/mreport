/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timofeevan
 */
@Deprecated
public class Main {

    public static String MAIN = "";
    public static String TEMP_DIR = "";
    public static URL urlAuthenticateWsdl = null;
    private static final String UTF8 = "UTF-8";

    @Deprecated
    public static String getTempFileName(String tmpdir, String... arguments) {
        String result = "";
        String a = "";
        if (arguments.length == 0) {
            a = String.valueOf(System.currentTimeMillis());
        }
        for (String s : arguments) {
            a += (s.replace("\\", "_").replace(":", "_").replace("/", "_") + "_");
        }
        //Date d = new Date(System.currentTimeMillis());
        long l = System.currentTimeMillis();
        result = tmpdir + a + "(" + dateTimeToStr(l).replace("\\", "_").replace(":", "_").replace("/", "_") + ")";
        return result;
    }

    @Deprecated
    public static String dateToStr(Calendar c) {
        return dateToStr(c.getTime());
    }

    @Deprecated
    public static String dateToStr(java.util.Date d) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String format = simpleDateFormat.format(d);
        return format;
    }

    @Deprecated
    public static String dateTimeToStr(long l) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String format = simpleDateFormat.format(l);
        return format;
    }

    @Deprecated
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try {
            try (InputStream is = new FileInputStream(fileName)) {
                try (Reader reader = new InputStreamReader(is, UTF8)) {
                    properties.load(reader);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return properties;
    }

    @Deprecated
    public static Object setProperty(Map map, String name, Properties properties) {
        if (properties.getProperty(name) != null) {
            return map.put(name, properties.getProperty(name));
        }
        return null;
    }

    @Deprecated
    public static Object setProperty(Map map, String name, Object value, Properties properties) {
        if (properties.getProperty(name) != null) {
            return map.put(name, value);
        }
        return null;
    }

    @Deprecated
    public static String convertPath(String path) {
        return path.replace("\\", "/");
    }
}
