/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service.controller;

import ru.p03.makpsb.core.entity.base.MPDAO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import ru.p03.makpsb.core.entity.service.ClsStandartFormingPeriod;
import ru.p03.makpsb.increm.model.ClsPeriodType;

/**
 *
 * @author timofeevan
 */
public class ClsssifierRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    public ClsssifierRepository() {
         this.emf = MPDAO.instance().getEntityManagerFactory();//this.emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }
    
    public ClsssifierRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    private static<T> List<T> getAll(Class clazz, EntityManager em){
        try {
            CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(clazz); 
            cq.select(cq.from(clazz));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<ClsPeriodType> getClsPeriodType() {
        EntityManager em = getEntityManager();
        return getAll(ClsPeriodType.class, em);
    }
    
    public ClsPeriodType getClsPeriodType(String code) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT p FROM ClsPeriodType p WHERE p.isDeleted = 0 AND p.code = :code", ClsPeriodType.class);
        query.setParameter("code", code);
        List<ClsPeriodType> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }  
    //
    
    public List<ClsStandartFormingPeriod> getStandartFormingPeriod() {
        EntityManager em = getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        return getAll(ClsStandartFormingPeriod.class, em);
    }
}
