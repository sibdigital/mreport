/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.dict.JobCalendar;
import ru.p03.makpsb.core.entity.dict.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class JobCalendarJpaController implements Serializable {

    public JobCalendarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    public void create(JobCalendar jobCalendar) {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            em.persist(jobCalendar);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void edit(JobCalendar jobCalendar) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            jobCalendar = em.merge(jobCalendar);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Long id = jobCalendar.getId();
//                if (findJobCalendar(id) == null) {
//                    throw new NonexistentEntityException("The jobCalendar with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void destroy(Long id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            JobCalendar jobCalendar;
//            try {
//                jobCalendar = em.getReference(JobCalendar.class, id);
//                jobCalendar.getId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The jobCalendar with id " + id + " no longer exists.", enfe);
//            }
//            em.remove(jobCalendar);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

    public List<JobCalendar> findJobCalendarEntities() {
        return findJobCalendarEntities(true, -1, -1);
    }

    public List<JobCalendar> findJobCalendarEntities(int maxResults, int firstResult) {
        return findJobCalendarEntities(false, maxResults, firstResult);
    }

    private List<JobCalendar> findJobCalendarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JobCalendar.class));
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

    public JobCalendar findJobCalendar(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JobCalendar.class, id);
        } finally {
            em.close();
        }
    }

    public int getJobCalendarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JobCalendar> rt = cq.from(JobCalendar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
