/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import ru.p03.makpsb.core.entity.service.UserInfo;
import ru.p03.makpsb.core.util.ResourceUtil;

/**
 *
 * @author timofeevan
 */
public class UserInfoJpaController implements Serializable {
    public UserInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }  
    
    public List<UserInfo> list(String username){
        List<UserInfo> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(ResourceUtil.inctance().resource_asString(UserInfoJpaController.class, "/ru/p03/makpsb/core/entity/sql/user_info.sql"), 
                UserInfo.class);
        nquery.setParameter(1, username);
        list = nquery.getResultList();
        return list;
    }        
}
