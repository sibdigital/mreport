package ru.p03.makpsb.main.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.p03.common.model.DomainUser;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.FormatUtils;

/**
 *
 * @author 003-0818
 */
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        log(request, response, chain);
        chain.doFilter(request,response);
    }
    
    private void log(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            HttpSession session = ((HttpServletRequest)request).getSession();
            Enumeration<String> attributeNames = session.getAttributeNames();
            String remoteAddr = request.getRemoteAddr();
            String url = "не инициализирован";
            String queryString = "не инициализирован";
            if (request instanceof HttpServletRequest) {
                url = ((HttpServletRequest)request).getRequestURL().toString();
                queryString = ((HttpServletRequest)request).getQueryString();
            }
            Logger.getLogger(LogFilter.class.getName()).log(Level.SEVERE, "Обращение к      " + url + " " + queryString + " от " + remoteAddr + " с параметрами: ");
            int i = 1;
            while (attributeNames.hasMoreElements()){
                String elem = attributeNames.nextElement();
                Object attribute = session.getAttribute(elem);               
                if (attribute instanceof DomainUser){
                    attribute = ((DomainUser)attribute).getLogin();
                }
                Logger.getLogger(LogFilter.class.getName()).log(Level.SEVERE, "     " + i + " - " + elem + " = " + attribute);
                i++;
            }
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String elem = parameterNames.nextElement();
                Object p = request.getParameter(elem);               
                if (p instanceof DomainUser){
                    p = ((DomainUser)p).getLogin();
                }
                Logger.getLogger(LogFilter.class.getName()).log(Level.SEVERE, "     " + i + " - " + elem + " = " + p);
                i++;
            }
            Logger.getLogger(LogFilter.class.getName()).log(Level.SEVERE, "Добавлено в протокол в: " + FormatUtils.formatAsDDMMYYY_HHMMSS(CommonUtils.now()));
        }catch (Exception ex){
            Logger.getLogger(LogFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {

    }

}
