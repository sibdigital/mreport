/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.dynreport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author timofeevan
 */
public class ActualDynReportJpaController implements Serializable {
    
    protected final String persistenceUnit = "CoreMakPSBPU";

    public ActualDynReportJpaController() {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }
    
    public ActualDynReportJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<ActualDynReport> findActualDynReportEntities() {
        return findActualDynReportEntities(true, -1, -1);
    }
    
    public List<ActualDynReport> findActualDynReportEntities(String username, String operation) {
        List<ActualDynReport> list = findActualDynReportEntities();
        return list;
//        List<ActualDynReport> result =  new ArrayList<>();
//        for (ActualDynReport adr : list) {
//            if (Verificator.instance().dynreportAllowed(username, adr, operation) == true){
//                result.add(adr);
//            }
//        }
//        return result;
    }

    public List<ActualDynReport> findActualDynReportEntities(int maxResults, int firstResult) {
        return findActualDynReportEntities(false, maxResults, firstResult);
    }

    private List<ActualDynReport> findActualDynReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActualDynReport.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ActualDynReport findActualDynReport(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActualDynReport.class, id);
        } finally {
            em.close();
        }
    }
    
    public ActualDynReport findActualDynReport(String code) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT adr FROM ActualDynReport adr WHERE adr.code = :code");//"EdvManResults"
        query.setParameter("code", code);
        List<ActualDynReport> resultList = query.getResultList();
        if (resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }

    public int getActualDynReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActualDynReport> rt = cq.from(ActualDynReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
