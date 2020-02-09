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
import ru.p03.makpsb.core.entity.quartz.QrtzPausedTriggerGrps;
import ru.p03.makpsb.core.entity.quartz.QrtzPausedTriggerGrpsPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzPausedTriggerGrpsJpaController implements Serializable {

    public QrtzPausedTriggerGrpsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzPausedTriggerGrps qrtzPausedTriggerGrps) throws PreexistingEntityException, Exception {
        if (qrtzPausedTriggerGrps.getQrtzPausedTriggerGrpsPK() == null) {
            qrtzPausedTriggerGrps.setQrtzPausedTriggerGrpsPK(new QrtzPausedTriggerGrpsPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(qrtzPausedTriggerGrps);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzPausedTriggerGrps(qrtzPausedTriggerGrps.getQrtzPausedTriggerGrpsPK()) != null) {
                throw new PreexistingEntityException("QrtzPausedTriggerGrps " + qrtzPausedTriggerGrps + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzPausedTriggerGrps qrtzPausedTriggerGrps) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            qrtzPausedTriggerGrps = em.merge(qrtzPausedTriggerGrps);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzPausedTriggerGrpsPK id = qrtzPausedTriggerGrps.getQrtzPausedTriggerGrpsPK();
                if (findQrtzPausedTriggerGrps(id) == null) {
                    throw new NonexistentEntityException("The qrtzPausedTriggerGrps with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzPausedTriggerGrpsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzPausedTriggerGrps qrtzPausedTriggerGrps;
            try {
                qrtzPausedTriggerGrps = em.getReference(QrtzPausedTriggerGrps.class, id);
                qrtzPausedTriggerGrps.getQrtzPausedTriggerGrpsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzPausedTriggerGrps with id " + id + " no longer exists.", enfe);
            }
            em.remove(qrtzPausedTriggerGrps);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzPausedTriggerGrps> findQrtzPausedTriggerGrpsEntities() {
        return findQrtzPausedTriggerGrpsEntities(true, -1, -1);
    }

    public List<QrtzPausedTriggerGrps> findQrtzPausedTriggerGrpsEntities(int maxResults, int firstResult) {
        return findQrtzPausedTriggerGrpsEntities(false, maxResults, firstResult);
    }

    private List<QrtzPausedTriggerGrps> findQrtzPausedTriggerGrpsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzPausedTriggerGrps.class));
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

    public QrtzPausedTriggerGrps findQrtzPausedTriggerGrps(QrtzPausedTriggerGrpsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzPausedTriggerGrps.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzPausedTriggerGrpsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzPausedTriggerGrps> rt = cq.from(QrtzPausedTriggerGrps.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
