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
import ru.p03.makpsb.core.entity.dict.controller.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.dict.controller.exceptions.PreexistingEntityException;
import ru.p03.makpsb.core.entity.service.StatAvg;

/**
 *
 * @author timofeevan
 */
public class StatAvgJpaController implements Serializable {

    public StatAvgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StatAvg statAvg) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(statAvg);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStatAvg(statAvg.getId()) != null) {
                throw new PreexistingEntityException("StatAvg " + statAvg + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StatAvg statAvg) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            statAvg = em.merge(statAvg);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = statAvg.getId();
                if (findStatAvg(id) == null) {
                    throw new NonexistentEntityException("The statAvg with id " + id + " no longer exists.");
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
            StatAvg statAvg;
            try {
                statAvg = em.getReference(StatAvg.class, id);
                statAvg.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statAvg with id " + id + " no longer exists.", enfe);
            }
            em.remove(statAvg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StatAvg> findStatAvgEntities() {
        return findStatAvgEntities(true, -1, -1);
    }

    public List<StatAvg> findStatAvgEntities(int maxResults, int firstResult) {
        return findStatAvgEntities(false, maxResults, firstResult);
    }

    private List<StatAvg> findStatAvgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StatAvg.class));
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

    public StatAvg findStatAvg(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StatAvg.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatAvgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StatAvg> rt = cq.from(StatAvg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
