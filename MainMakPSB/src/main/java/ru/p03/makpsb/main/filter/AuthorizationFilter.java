package ru.p03.makpsb.main.filter;

import java.util.Objects;
import java.io.IOException;
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
import ru.p03.makpsb.core.resource.Verificator;
import ru.p03.makpsb.main.utils.ServletUtils;

/**
 *
 * @author 003-0818
 */
public class AuthorizationFilter implements Filter {

    private String loginPage;
    private String notAllowedPage;
    private static final String OPERATION_EXECUTE = "E";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            loginPage = filterConfig.getInitParameter("login_page");
            notAllowedPage = filterConfig.getInitParameter("not_allowed_page");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!ServletUtils.isNoAuth()) {
            HttpServletRequest hsr = (HttpServletRequest) request;
            HttpSession session = hsr.getSession();
            if (session.getAttribute("user") == null) {
                ((HttpServletResponse) response).sendRedirect(hsr.getContextPath() + "/" + loginPage);
                return;
            } else {
                if (resourceAllowed(request, response) == false) {
                    ((HttpServletResponse) response).sendRedirect(hsr.getContextPath() + "/" + notAllowedPage);
                    return;
                }
            }
        }
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
    
    private boolean orEquals (String servletPath, String... paths){
        boolean result = false;
        for (String path : paths) {
            result |= Objects.equals(servletPath, path);
        }
        return result;
    }
    
    private boolean orContains (String servletPath, String... paths){
        boolean result = false;
        for (String path : paths) {
            result |= servletPath.contains(path);
        }
        return result;
    }
    
    private boolean resourceAllowed(ServletRequest request, ServletResponse response){
        boolean result = false;
        String servletPath = "";
        String queryString = "";
        String username = "";
        if (request instanceof HttpServletRequest) {
            HttpServletRequest hsr = (HttpServletRequest)request;
            servletPath =  hsr.getServletPath();//hsr.getRequestURL().toString(); //"/main/classifier.do"
            queryString = hsr.getQueryString();
            if (orEquals(servletPath, "/main/index.do", "/index.jsp", "/main/not_allowed.do", "/main/error.jsp")
                    || orContains(servletPath, "error.jsp", "/main/available_periods.do",
                            "/main/user_info.do", "/main/classifier.do")){
                result = true;
            }else{
                HttpSession session = hsr.getSession();
                Object attribute = session.getAttribute("user");
                if (attribute != null && attribute instanceof DomainUser){
                    username = ((DomainUser)attribute).getLogin();
                }
                result = Verificator.instance().resourceAllowed(username, servletPath, OPERATION_EXECUTE);
            }
        }
        return result;
    }


}
