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
import ru.p03.makpsb.core.entity.quartz.QrtzFiredTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzFiredTriggersPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzFiredTriggersJpaController implements Serializable {

    public QrtzFiredTriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzFiredTriggers qrtzFiredTriggers) throws PreexistingEntityException, Exception {
        if (qrtzFiredTriggers.getQrtzFiredTriggersPK() == null) {
            qrtzFiredTriggers.setQrtzFiredTriggersPK(new QrtzFiredTriggersPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(qrtzFiredTriggers);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzFiredTriggers(qrtzFiredTriggers.getQrtzFiredTriggersPK()) != null) {
                throw new PreexistingEntityException("QrtzFiredTriggers " + qrtzFiredTriggers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzFiredTriggers qrtzFiredTriggers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            qrtzFiredTriggers = em.merge(qrtzFiredTriggers);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzFiredTriggersPK id = qrtzFiredTriggers.getQrtzFiredTriggersPK();
                if (findQrtzFiredTriggers(id) == null) {
                    throw new NonexistentEntityException("The qrtzFiredTriggers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzFiredTriggersPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzFiredTriggers qrtzFiredTriggers;
            try {
                qrtzFiredTriggers = em.getReference(QrtzFiredTriggers.class, id);
                qrtzFiredTriggers.getQrtzFiredTriggersPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzFiredTriggers with id " + id + " no longer exists.", enfe);
            }
            em.remove(qrtzFiredTriggers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzFiredTriggers> findQrtzFiredTriggersEntities() {
        return findQrtzFiredTriggersEntities(true, -1, -1);
    }

    public List<QrtzFiredTriggers> findQrtzFiredTriggersEntities(int maxResults, int firstResult) {
        return findQrtzFiredTriggersEntities(false, maxResults, firstResult);
    }

    private List<QrtzFiredTriggers> findQrtzFiredTriggersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzFiredTriggers.class));
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

    public QrtzFiredTriggers findQrtzFiredTriggers(QrtzFiredTriggersPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzFiredTriggers.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzFiredTriggersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzFiredTriggers> rt = cq.from(QrtzFiredTriggers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
