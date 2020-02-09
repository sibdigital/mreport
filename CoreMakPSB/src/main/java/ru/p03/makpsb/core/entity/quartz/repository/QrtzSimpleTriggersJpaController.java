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
import ru.p03.makpsb.core.entity.quartz.QrtzSimpleTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzSimpleTriggersPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzSimpleTriggersJpaController implements Serializable {

    public QrtzSimpleTriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzSimpleTriggers qrtzSimpleTriggers) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (qrtzSimpleTriggers.getQrtzSimpleTriggersPK() == null) {
            qrtzSimpleTriggers.setQrtzSimpleTriggersPK(new QrtzSimpleTriggersPK());
        }
        qrtzSimpleTriggers.getQrtzSimpleTriggersPK().setTriggerGroup(qrtzSimpleTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        qrtzSimpleTriggers.getQrtzSimpleTriggersPK().setSchedName(qrtzSimpleTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        qrtzSimpleTriggers.getQrtzSimpleTriggersPK().setTriggerName(qrtzSimpleTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        List<String> illegalOrphanMessages = null;
        QrtzTriggers qrtzTriggersOrphanCheck = qrtzSimpleTriggers.getQrtzTriggers();
        if (qrtzTriggersOrphanCheck != null) {
            QrtzSimpleTriggers oldQrtzSimpleTriggersOfQrtzTriggers = qrtzTriggersOrphanCheck.getQrtzSimpleTriggers();
            if (oldQrtzSimpleTriggersOfQrtzTriggers != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersOrphanCheck + " already has an item of type QrtzSimpleTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzTriggers qrtzTriggers = qrtzSimpleTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers = em.getReference(qrtzTriggers.getClass(), qrtzTriggers.getQrtzTriggersPK());
                qrtzSimpleTriggers.setQrtzTriggers(qrtzTriggers);
            }
            em.persist(qrtzSimpleTriggers);
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzSimpleTriggers(qrtzSimpleTriggers);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzSimpleTriggers(qrtzSimpleTriggers.getQrtzSimpleTriggersPK()) != null) {
                throw new PreexistingEntityException("QrtzSimpleTriggers " + qrtzSimpleTriggers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzSimpleTriggers qrtzSimpleTriggers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        qrtzSimpleTriggers.getQrtzSimpleTriggersPK().setTriggerGroup(qrtzSimpleTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        qrtzSimpleTriggers.getQrtzSimpleTriggersPK().setSchedName(qrtzSimpleTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        qrtzSimpleTriggers.getQrtzSimpleTriggersPK().setTriggerName(qrtzSimpleTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzSimpleTriggers persistentQrtzSimpleTriggers = em.find(QrtzSimpleTriggers.class, qrtzSimpleTriggers.getQrtzSimpleTriggersPK());
            QrtzTriggers qrtzTriggersOld = persistentQrtzSimpleTriggers.getQrtzTriggers();
            QrtzTriggers qrtzTriggersNew = qrtzSimpleTriggers.getQrtzTriggers();
            List<String> illegalOrphanMessages = null;
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                QrtzSimpleTriggers oldQrtzSimpleTriggersOfQrtzTriggers = qrtzTriggersNew.getQrtzSimpleTriggers();
                if (oldQrtzSimpleTriggersOfQrtzTriggers != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersNew + " already has an item of type QrtzSimpleTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (qrtzTriggersNew != null) {
                qrtzTriggersNew = em.getReference(qrtzTriggersNew.getClass(), qrtzTriggersNew.getQrtzTriggersPK());
                qrtzSimpleTriggers.setQrtzTriggers(qrtzTriggersNew);
            }
            qrtzSimpleTriggers = em.merge(qrtzSimpleTriggers);
            if (qrtzTriggersOld != null && !qrtzTriggersOld.equals(qrtzTriggersNew)) {
                qrtzTriggersOld.setQrtzSimpleTriggers(null);
                qrtzTriggersOld = em.merge(qrtzTriggersOld);
            }
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                qrtzTriggersNew.setQrtzSimpleTriggers(qrtzSimpleTriggers);
                qrtzTriggersNew = em.merge(qrtzTriggersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzSimpleTriggersPK id = qrtzSimpleTriggers.getQrtzSimpleTriggersPK();
                if (findQrtzSimpleTriggers(id) == null) {
                    throw new NonexistentEntityException("The qrtzSimpleTriggers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzSimpleTriggersPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzSimpleTriggers qrtzSimpleTriggers;
            try {
                qrtzSimpleTriggers = em.getReference(QrtzSimpleTriggers.class, id);
                qrtzSimpleTriggers.getQrtzSimpleTriggersPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzSimpleTriggers with id " + id + " no longer exists.", enfe);
            }
            QrtzTriggers qrtzTriggers = qrtzSimpleTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzSimpleTriggers(null);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.remove(qrtzSimpleTriggers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzSimpleTriggers> findQrtzSimpleTriggersEntities() {
        return findQrtzSimpleTriggersEntities(true, -1, -1);
    }

    public List<QrtzSimpleTriggers> findQrtzSimpleTriggersEntities(int maxResults, int firstResult) {
        return findQrtzSimpleTriggersEntities(false, maxResults, firstResult);
    }

    private List<QrtzSimpleTriggers> findQrtzSimpleTriggersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzSimpleTriggers.class));
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

    public QrtzSimpleTriggers findQrtzSimpleTriggers(QrtzSimpleTriggersPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzSimpleTriggers.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzSimpleTriggersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzSimpleTriggers> rt = cq.from(QrtzSimpleTriggers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
