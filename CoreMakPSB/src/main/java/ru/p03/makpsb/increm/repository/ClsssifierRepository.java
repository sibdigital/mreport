/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import ru.p03.makpsb.increm.model.ClsPeriodType;
import ru.p03.makpsb.increm.model.ClsSignature;
import ru.p03.makpsb.increm.model.ClsSignatureType;
import ru.p03.makpsb.increm.model.SignatureTiming;

/**
 *
 * @author timofeevan
 */
public class ClsssifierRepository implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected final String persistenceUnit = "MPIncrem";

    public ClsssifierRepository() {
         this.emf = MPIncremDAO.instance().getEntityManagerFactory();//this.emf = Persistence.createEntityManagerFactory(persistenceUnit);
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

    public List<SignatureTiming> getSignatureTiming() {
        EntityManager em = getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        return getAll(SignatureTiming.class, em);
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
    
    public List<ClsSignatureType> getClsSignatureType() {
        EntityManager em = getEntityManager();
        return getAll(ClsSignatureType.class, em);
    }
    
    public List<ClsSignature> getClsSignature() {
        EntityManager em = getEntityManager();
        return getAll(ClsSignature.class, em);
    }
    
    public List<ClsSignature> getClsSignature(Long signatureTypeId) {
        EntityManager em = getEntityManager();
        Query query = em.<ClsSignature>createQuery("SELECT p FROM ClsSignature p WHERE p.isDeleted = 0 AND p.idSignatureType.id = :signatureTypeId", ClsSignature.class);
        query.setParameter("signatureTypeId", signatureTypeId);
        List<ClsSignature> resultList = query.getResultList();
        return resultList;
    }  
    
    public List<ClsSignature> getClsSignature(ClsSignatureType signatureType) { 
        return getClsSignature(signatureType.getId());
    }
    
    public SignatureTiming getSignatureTiming(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getEntityManagerFactory().getCache().evict(SignatureTiming.class);
            return em.find(SignatureTiming.class, id);
        } finally {
            em.close();
        }
    }
    
    public ClsSignature findClsSignature(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClsSignature.class, id);
        } finally {
            em.close();
        }
    }
    


//    public ActualDynReport findActualDynReport(long id) {
//        EntityManager em = getEntityManager();
//        try {
//            return em.find(ActualDynReport.class, id);
//        } finally {
//            em.close();
//        }
//    }
//
//    public int getActualDynReportCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<ActualDynReport> rt = cq.from(ActualDynReport.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }

}
