/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.service.Cvitantion;
import ru.p03.makpsb.core.entity.service.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class CvitantionJpaController implements Serializable {

    public CvitantionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cvitantion cvitantion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cvitantion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cvitantion cvitantion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cvitantion = em.merge(cvitantion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cvitantion.getId();
                if (findCvitantion(id) == null) {
                    throw new NonexistentEntityException("The cvitantion with id " + id + " no longer exists.");
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
            Cvitantion cvitantion;
            try {
                cvitantion = em.getReference(Cvitantion.class, id);
                cvitantion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cvitantion with id " + id + " no longer exists.", enfe);
            }
            em.remove(cvitantion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cvitantion> findCvitantionEntities() {
        return findCvitantionEntities(true, -1, -1);
    }

    public List<Cvitantion> findCvitantionEntities(int maxResults, int firstResult) {
        return findCvitantionEntities(false, maxResults, firstResult);
    }

    private List<Cvitantion> findCvitantionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cvitantion.class));
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

    public Cvitantion findCvitantion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cvitantion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCvitantionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cvitantion> rt = cq.from(Cvitantion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
