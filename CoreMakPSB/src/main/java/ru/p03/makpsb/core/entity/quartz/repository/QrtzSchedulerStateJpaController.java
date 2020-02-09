/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.quartz.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.quartz.QrtzSchedulerState;
import ru.p03.makpsb.core.entity.quartz.QrtzSchedulerStatePK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzSchedulerStateJpaController implements Serializable {

    public QrtzSchedulerStateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzSchedulerState qrtzSchedulerState) throws PreexistingEntityException, Exception {
        if (qrtzSchedulerState.getQrtzSchedulerStatePK() == null) {
            qrtzSchedulerState.setQrtzSchedulerStatePK(new QrtzSchedulerStatePK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(qrtzSchedulerState);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzSchedulerState(qrtzSchedulerState.getQrtzSchedulerStatePK()) != null) {
                throw new PreexistingEntityException("QrtzSchedulerState " + qrtzSchedulerState + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzSchedulerState qrtzSchedulerState) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            qrtzSchedulerState = em.merge(qrtzSchedulerState);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzSchedulerStatePK id = qrtzSchedulerState.getQrtzSchedulerStatePK();
                if (findQrtzSchedulerState(id) == null) {
                    throw new NonexistentEntityException("The qrtzSchedulerState with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzSchedulerStatePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzSchedulerState qrtzSchedulerState;
            try {
                qrtzSchedulerState = em.getReference(QrtzSchedulerState.class, id);
                qrtzSchedulerState.getQrtzSchedulerStatePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzSchedulerState with id " + id + " no longer exists.", enfe);
            }
            em.remove(qrtzSchedulerState);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzSchedulerState> findQrtzSchedulerStateEntities() {
        return findQrtzSchedulerStateEntities(true, -1, -1);
    }

    public List<QrtzSchedulerState> findQrtzSchedulerStateEntities(int maxResults, int firstResult) {
        return findQrtzSchedulerStateEntities(false, maxResults, firstResult);
    }

    private List<QrtzSchedulerState> findQrtzSchedulerStateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzSchedulerState.class));
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

    public QrtzSchedulerState findQrtzSchedulerState(QrtzSchedulerStatePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzSchedulerState.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzSchedulerStateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzSchedulerState> rt = cq.from(QrtzSchedulerState.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
