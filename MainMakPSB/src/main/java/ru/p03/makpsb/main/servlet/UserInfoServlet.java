/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.p03.common.model.DomainUser;
import ru.p03.makpsb.core.entity.service.Role;
import ru.p03.makpsb.core.entity.service.UseRole;
import ru.p03.makpsb.core.entity.service.UserInfo;
import ru.p03.makpsb.core.entity.service.controller.UseRoleJpaController;
import ru.p03.makpsb.core.resource.Verificator;
import ru.p03.makpsb.core.servlet.MPServlet;
import ru.p03.makpsb.core.util.UserUtil;

/**
 *
 * @author timofeevan
 */
@WebServlet(name = "UserInfoServlet", urlPatterns = {"/main/user_info.do"})
public class UserInfoServlet extends MPServlet {

    private static final long serialVersionUID = 1L;

    final String PAGE = "/jsp/form/user_info.jsp";

    @Override
    protected String page() {
        return PAGE;
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DomainUser user = getUser(request, response);
        String submitAction = request.getParameter("submit_action");
        String action = request.getParameter("action");
        String currentLogin = request.getParameter("current_login");
        
        boolean isAdmin = Verificator.instance().isAdmin(user.getLogin());
        request.setAttribute("isAdmin", isAdmin);

        if (submitAction == null) {
            
            if ("delete_role".equals(action) && isAdmin) {
                try {
                    Long useRoleId = Long.decode(request.getParameter("use_role_id"));
                    UseRoleJpaController urjc = new UseRoleJpaController();
                    UseRole fur = urjc.findUseRole(useRoleId);
                    fur.setIsDeleted(1);
                    urjc.edit(fur);
                    UserUtil.instance().evictAllUsers();
                    
                    request.setAttribute("current_login", fur.getName());
                    request.setAttribute("user_login", fur.getName());
                    request.setAttribute("user_name", "");
                    
                    setAttributes(fur.getName(), request, response);
                } catch (Exception ex) {
                    Logger.getLogger(UserInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if (user != null) {
                request.setAttribute("user_name", user.getAttributes().get("displayname"));
                request.setAttribute("user_login", user.getLogin());
                setAttributes(user.getLogin(), request, response);
            }
        } else if (isAdmin){

            request.setAttribute("user_login", currentLogin);

            if ("filter_login".equals(submitAction)) {

            } else if ("add_role".equals(submitAction)) {
                if (currentLogin != null) {
                    Long roleId = Long.decode(request.getParameter("roleList"));
                    UseRole useRole = new UseRole();
                    useRole.setIsDeleted(0);
                    useRole.setIdDepart(0);
                    useRole.setName(currentLogin);
                    useRole.setIdRole(UserUtil.instance().role(roleId));

                    UseRoleJpaController urjc = new UseRoleJpaController();
                    urjc.create(useRole);
                    UserUtil.instance().evictAllUsers();
                }
            } 
            if (currentLogin != null && !currentLogin.isEmpty()) {
                request.setAttribute("current_login", currentLogin);
                request.setAttribute("user_name", "");

                setAttributes(currentLogin, request, response);

            }
        }
        selfForward(request, response);
    }

    private void setAttributes(String login, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfo> usersInfo = UserUtil.instance().usersInfo(login);
        request.setAttribute("user_info_list", usersInfo);

        List<UserInfo> roles = UserUtil.instance().roles(login);
        request.setAttribute("user_role_list", roles);

        List<Role> allRoles = UserUtil.instance().roles();
        request.setAttribute("roleList", allRoles);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
