/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import ru.p03.makpsb.core.entity.dict.LeaderInfo;
import ru.p03.makpsb.core.util.ResourceUtil;

/**
 *
 * @author timofeevan
 */
public class LeaderInfoJpaController implements Serializable {
    public LeaderInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }  
    
    public List<LeaderInfo> list(){
        List<LeaderInfo> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(ResourceUtil.inctance().resource_asString(LeaderInfoJpaController.class, "/ru/p03/makpsb/core/entity/sql/leader_info.sql"), 
                LeaderInfo.class);
        list = nquery.getResultList();
        return list;
    }        
}
