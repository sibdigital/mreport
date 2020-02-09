
package ru.p03.makpsb.main.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
//import ru.p03.makpsb.auth.AuthenticationUtil;
import ru.p03.makpsb.core.util.ResourceUtil;

/**
 *
 * @author 003-0807, altmf
 */
public class ServletUtils {

    private static URL urlAuthenticateWsdl = null;  
    public static String DEFAULT_HOST_PORT  = "localhost";

//    @Deprecated
//    public static void configureConnections() throws MalformedURLException {
//        if(urlAuthenticateWsdl == null){
//            String authurl = (String)ResourceUtil.inctance().getGlobalNamingResource("java:/comp/env", "mpcore/authurl");
//            System.out.println(authurl);
//            urlAuthenticateWsdl = new URL(authurl);
//            System.out.println("Got URL to MakPSBCore webService from config file: " + urlAuthenticateWsdl);
//            AuthenticationUtil.init(urlAuthenticateWsdl);
//            System.out.println("Connected to web service MakPSBCore successfully.");
//        }
//    }
    
    private static URI COMMON_SERVICE_URL = null;  
    
    public static URI getCommonServiceURL() throws URISyntaxException{
        if (COMMON_SERVICE_URL == null){
            String authurl = (String)ResourceUtil.inctance().getGlobalNamingResource("java:/comp/env", "mpcore/commonauthurl"); 
            COMMON_SERVICE_URL = new URI(authurl);
        }
        return COMMON_SERVICE_URL;
    }

    public static boolean isNoAuth(){
//        if (COMMON_SERVICE_URL == null){
//            String authurl = (String)ResourceUtil.inctance().getGlobalNamingResource("java:/comp/env", "mpcore/commonauthurl");
//            COMMON_SERVICE_URL = new URI(authurl);
//        }
        return true;//COMMON_SERVICE_URL;
    }
    
//    public static void getDataForPrinting(String sprav_filename, HttpServletResponse response) throws FileNotFoundException, IOException{
//
//        response.setContentType("APPLICATION/OCTET-STREAM");
//        response.setHeader("Content-Disposition", "attachment; filename=sprav.odt");
//
//        try (java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sprav_filename)) {
//            ServletOutputStream out = response.getOutputStream();
//            int i;
//            while ((i = fileInputStream.read()) != -1) {
//                out.write(i);
//            }
//        }
//        
//    }

//    @Deprecated
//    public static String getRaNumFromLoginUser(String login){
//        if ((login != null) && (login.length()>8)){  //считаем , что это районный логин
//            return login.substring(5, 7);
//        }
//        return "0";
//    }

}
