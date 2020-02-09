/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.quartz.repository;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.quartz.QrtzTriggers;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.p03.makpsb.core.entity.quartz.QrtzSimpropTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzSimpropTriggersPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzSimpropTriggersJpaController implements Serializable {

    public QrtzSimpropTriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzSimpropTriggers qrtzSimpropTriggers) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (qrtzSimpropTriggers.getQrtzSimpropTriggersPK() == null) {
            qrtzSimpropTriggers.setQrtzSimpropTriggersPK(new QrtzSimpropTriggersPK());
        }
        qrtzSimpropTriggers.getQrtzSimpropTriggersPK().setTriggerGroup(qrtzSimpropTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        qrtzSimpropTriggers.getQrtzSimpropTriggersPK().setTriggerName(qrtzSimpropTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        qrtzSimpropTriggers.getQrtzSimpropTriggersPK().setSchedName(qrtzSimpropTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        List<String> illegalOrphanMessages = null;
        QrtzTriggers qrtzTriggersOrphanCheck = qrtzSimpropTriggers.getQrtzTriggers();
        if (qrtzTriggersOrphanCheck != null) {
            QrtzSimpropTriggers oldQrtzSimpropTriggersOfQrtzTriggers = qrtzTriggersOrphanCheck.getQrtzSimpropTriggers();
            if (oldQrtzSimpropTriggersOfQrtzTriggers != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersOrphanCheck + " already has an item of type QrtzSimpropTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzTriggers qrtzTriggers = qrtzSimpropTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers = em.getReference(qrtzTriggers.getClass(), qrtzTriggers.getQrtzTriggersPK());
                qrtzSimpropTriggers.setQrtzTriggers(qrtzTriggers);
            }
            em.persist(qrtzSimpropTriggers);
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzSimpropTriggers(qrtzSimpropTriggers);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzSimpropTriggers(qrtzSimpropTriggers.getQrtzSimpropTriggersPK()) != null) {
                throw new PreexistingEntityException("QrtzSimpropTriggers " + qrtzSimpropTriggers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzSimpropTriggers qrtzSimpropTriggers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        qrtzSimpropTriggers.getQrtzSimpropTriggersPK().setTriggerGroup(qrtzSimpropTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        qrtzSimpropTriggers.getQrtzSimpropTriggersPK().setTriggerName(qrtzSimpropTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        qrtzSimpropTriggers.getQrtzSimpropTriggersPK().setSchedName(qrtzSimpropTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzSimpropTriggers persistentQrtzSimpropTriggers = em.find(QrtzSimpropTriggers.class, qrtzSimpropTriggers.getQrtzSimpropTriggersPK());
            QrtzTriggers qrtzTriggersOld = persistentQrtzSimpropTriggers.getQrtzTriggers();
            QrtzTriggers qrtzTriggersNew = qrtzSimpropTriggers.getQrtzTriggers();
            List<String> illegalOrphanMessages = null;
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                QrtzSimpropTriggers oldQrtzSimpropTriggersOfQrtzTriggers = qrtzTriggersNew.getQrtzSimpropTriggers();
                if (oldQrtzSimpropTriggersOfQrtzTriggers != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersNew + " already has an item of type QrtzSimpropTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (qrtzTriggersNew != null) {
                qrtzTriggersNew = em.getReference(qrtzTriggersNew.getClass(), qrtzTriggersNew.getQrtzTriggersPK());
                qrtzSimpropTriggers.setQrtzTriggers(qrtzTriggersNew);
            }
            qrtzSimpropTriggers = em.merge(qrtzSimpropTriggers);
            if (qrtzTriggersOld != null && !qrtzTriggersOld.equals(qrtzTriggersNew)) {
                qrtzTriggersOld.setQrtzSimpropTriggers(null);
                qrtzTriggersOld = em.merge(qrtzTriggersOld);
            }
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                qrtzTriggersNew.setQrtzSimpropTriggers(qrtzSimpropTriggers);
                qrtzTriggersNew = em.merge(qrtzTriggersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzSimpropTriggersPK id = qrtzSimpropTriggers.getQrtzSimpropTriggersPK();
                if (findQrtzSimpropTriggers(id) == null) {
                    throw new NonexistentEntityException("The qrtzSimpropTriggers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzSimpropTriggersPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzSimpropTriggers qrtzSimpropTriggers;
            try {
                qrtzSimpropTriggers = em.getReference(QrtzSimpropTriggers.class, id);
                qrtzSimpropTriggers.getQrtzSimpropTriggersPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzSimpropTriggers with id " + id + " no longer exists.", enfe);
            }
            QrtzTriggers qrtzTriggers = qrtzSimpropTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzSimpropTriggers(null);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.remove(qrtzSimpropTriggers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzSimpropTriggers> findQrtzSimpropTriggersEntities() {
        return findQrtzSimpropTriggersEntities(true, -1, -1);
    }

    public List<QrtzSimpropTriggers> findQrtzSimpropTriggersEntities(int maxResults, int firstResult) {
        return findQrtzSimpropTriggersEntities(false, maxResults, firstResult);
    }

    private List<QrtzSimpropTriggers> findQrtzSimpropTriggersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzSimpropTriggers.class));
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

    public QrtzSimpropTriggers findQrtzSimpropTriggers(QrtzSimpropTriggersPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzSimpropTriggers.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzSimpropTriggersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzSimpropTriggers> rt = cq.from(QrtzSimpropTriggers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
