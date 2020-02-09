/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import ru.p03.makpsb.core.resource.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ru.p03.makpsb.core.entity.service.Role;
import ru.p03.makpsb.core.entity.service.UserInfo;
import ru.p03.makpsb.core.entity.service.controller.RoleJpaController;
import ru.p03.makpsb.core.entity.service.controller.UserInfoJpaController;
import ru.p03.makpsb.dynreport.ActualDynReport;
import ru.p03.makpsb.increm.model.SignatureTiming;

/**
 *
 * @author timofeevan
 */
public class UserUtil {

    protected static final String persistenceUnit = "CoreMakPSBPU";

    private static EntityManagerFactory emf;
    private static UserInfoJpaController uijp;
    private static RoleJpaController rjc;

    private UserUtil() {

    }

    public static UserUtil instance() {
        return new UserUtil();
    }
    
    public void evictAllUsers(){       
        getEmf().getCache().evict(UserInfo.class);
    }
    
    private static EntityManagerFactory getEmf(){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return emf;
    }

    public List<UserInfo> usersInfo(String username) {
        if (uijp == null){
            uijp = new UserInfoJpaController(getEmf());
        }
        List<UserInfo> list = uijp.list(username);
        return list;
    }
    
    public List<UserInfo> roles(String username) {
        List<UserInfo> list = usersInfo(username);
        Map<Long, UserInfo> map = new HashMap<>();
        for (UserInfo userInfo : list) {
            map.put(userInfo.getUserInfoId().getRoleId(), userInfo);
        }
        List<UserInfo> result = new ArrayList<>(map.values());
        return result;
    }
    
    public List<Role> roles() {
        if (rjc == null){
            rjc = new RoleJpaController(getEmf());
        }
        List<Role> list = rjc.findRoleEntities();
        return list;
    }
    
    public Role role(Long idRole) {
        if (rjc == null){
            rjc = new RoleJpaController(getEmf());
        }
        Role r = rjc.findRole(idRole);
        return r;
    }

}
