/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.dict.AvailablePeriod;
import ru.p03.makpsb.core.entity.dict.exceptions.NonexistentEntityException;
import ru.p03.vmvp.utils.CommonUtils;

/**
 *
 * @author timofeevan
 */
public class AvailablePeriodJpaController implements Serializable {

    public AvailablePeriodJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvailablePeriod availablePeriod) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(availablePeriod);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvailablePeriod availablePeriod) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            availablePeriod = em.merge(availablePeriod);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = availablePeriod.getId();
                if (findAvailablePeriod(id) == null) {
                    throw new NonexistentEntityException("The availablePeriod with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvailablePeriod availablePeriod;
            try {
                availablePeriod = em.getReference(AvailablePeriod.class, id);
                availablePeriod.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The availablePeriod with id " + id + " no longer exists.", enfe);
            }
            em.remove(availablePeriod);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<AvailablePeriod> getAvailablePeriod(Date date) {
        
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(date);
        
        EntityManager em = getEntityManager();
        Query query = em.<AvailablePeriod>createQuery("SELECT p FROM AvailablePeriod p WHERE p.isDeleted = 0 AND p.dayOfWeek = :dayOfWeek "
                //+ "AND p.hourBegin <= :hour AND p.hourEnd >= :hour AND p.minuteBegin <= :minute AND p.minuteEnd >= :minute"
                , AvailablePeriod.class);
        query.setParameter("dayOfWeek", c.get(Calendar.DAY_OF_WEEK));
        //query.setParameter("hour", c.get(Calendar.HOUR_OF_DAY));
        //query.setParameter("minute", c.get(Calendar.MINUTE));
        List<AvailablePeriod> resultList = query.getResultList();
        return resultList;
    }
    
    public List<AvailablePeriod> getAvailablePeriod(Integer dayOfWeek) {
 
        EntityManager em = getEntityManager();
        Query query = em.<AvailablePeriod>createQuery("SELECT p FROM AvailablePeriod p WHERE p.isDeleted = 0 AND p.dayOfWeek = :dayOfWeek "
                //+ "AND p.hourBegin <= :hour AND p.hourEnd >= :hour AND p.minuteBegin <= :minute AND p.minuteEnd >= :minute"
                , AvailablePeriod.class);
        query.setParameter("dayOfWeek", dayOfWeek);
        List<AvailablePeriod> resultList = query.getResultList();
        return resultList;
    }
    
    public List<AvailablePeriod> getAvailablePeriod() {
        return findAvailablePeriodEntities();
    }

    public List<AvailablePeriod> findAvailablePeriodEntities() {
        return findAvailablePeriodEntities(true, -1, -1);
    }

    public List<AvailablePeriod> findAvailablePeriodEntities(int maxResults, int firstResult) {
        return findAvailablePeriodEntities(false, maxResults, firstResult);
    }

    private List<AvailablePeriod> findAvailablePeriodEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvailablePeriod.class));
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

    public AvailablePeriod findAvailablePeriod(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvailablePeriod.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvailablePeriodCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvailablePeriod> rt = cq.from(AvailablePeriod.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
