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
import ru.p03.makpsb.core.entity.quartz.QrtzLocks;
import ru.p03.makpsb.core.entity.quartz.QrtzLocksPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzLocksJpaController implements Serializable {

    public QrtzLocksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzLocks qrtzLocks) throws PreexistingEntityException, Exception {
        if (qrtzLocks.getQrtzLocksPK() == null) {
            qrtzLocks.setQrtzLocksPK(new QrtzLocksPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(qrtzLocks);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzLocks(qrtzLocks.getQrtzLocksPK()) != null) {
                throw new PreexistingEntityException("QrtzLocks " + qrtzLocks + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzLocks qrtzLocks) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            qrtzLocks = em.merge(qrtzLocks);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzLocksPK id = qrtzLocks.getQrtzLocksPK();
                if (findQrtzLocks(id) == null) {
                    throw new NonexistentEntityException("The qrtzLocks with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzLocksPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzLocks qrtzLocks;
            try {
                qrtzLocks = em.getReference(QrtzLocks.class, id);
                qrtzLocks.getQrtzLocksPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzLocks with id " + id + " no longer exists.", enfe);
            }
            em.remove(qrtzLocks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzLocks> findQrtzLocksEntities() {
        return findQrtzLocksEntities(true, -1, -1);
    }

    public List<QrtzLocks> findQrtzLocksEntities(int maxResults, int firstResult) {
        return findQrtzLocksEntities(false, maxResults, firstResult);
    }

    private List<QrtzLocks> findQrtzLocksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzLocks.class));
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

    public QrtzLocks findQrtzLocks(QrtzLocksPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzLocks.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzLocksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzLocks> rt = cq.from(QrtzLocks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
