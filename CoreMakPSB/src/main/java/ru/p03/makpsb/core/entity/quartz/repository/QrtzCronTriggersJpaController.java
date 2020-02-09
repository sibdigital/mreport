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
import ru.p03.makpsb.core.entity.quartz.QrtzCronTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzCronTriggersPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzCronTriggersJpaController implements Serializable {

    public QrtzCronTriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzCronTriggers qrtzCronTriggers) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (qrtzCronTriggers.getQrtzCronTriggersPK() == null) {
            qrtzCronTriggers.setQrtzCronTriggersPK(new QrtzCronTriggersPK());
        }
        qrtzCronTriggers.getQrtzCronTriggersPK().setTriggerName(qrtzCronTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        qrtzCronTriggers.getQrtzCronTriggersPK().setTriggerGroup(qrtzCronTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        qrtzCronTriggers.getQrtzCronTriggersPK().setSchedName(qrtzCronTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        List<String> illegalOrphanMessages = null;
        QrtzTriggers qrtzTriggersOrphanCheck = qrtzCronTriggers.getQrtzTriggers();
        if (qrtzTriggersOrphanCheck != null) {
            QrtzCronTriggers oldQrtzCronTriggersOfQrtzTriggers = qrtzTriggersOrphanCheck.getQrtzCronTriggers();
            if (oldQrtzCronTriggersOfQrtzTriggers != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersOrphanCheck + " already has an item of type QrtzCronTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzTriggers qrtzTriggers = qrtzCronTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers = em.getReference(qrtzTriggers.getClass(), qrtzTriggers.getQrtzTriggersPK());
                qrtzCronTriggers.setQrtzTriggers(qrtzTriggers);
            }
            em.persist(qrtzCronTriggers);
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzCronTriggers(qrtzCronTriggers);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzCronTriggers(qrtzCronTriggers.getQrtzCronTriggersPK()) != null) {
                throw new PreexistingEntityException("QrtzCronTriggers " + qrtzCronTriggers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzCronTriggers qrtzCronTriggers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        qrtzCronTriggers.getQrtzCronTriggersPK().setTriggerName(qrtzCronTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        qrtzCronTriggers.getQrtzCronTriggersPK().setTriggerGroup(qrtzCronTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        qrtzCronTriggers.getQrtzCronTriggersPK().setSchedName(qrtzCronTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzCronTriggers persistentQrtzCronTriggers = em.find(QrtzCronTriggers.class, qrtzCronTriggers.getQrtzCronTriggersPK());
            QrtzTriggers qrtzTriggersOld = persistentQrtzCronTriggers.getQrtzTriggers();
            QrtzTriggers qrtzTriggersNew = qrtzCronTriggers.getQrtzTriggers();
            List<String> illegalOrphanMessages = null;
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                QrtzCronTriggers oldQrtzCronTriggersOfQrtzTriggers = qrtzTriggersNew.getQrtzCronTriggers();
                if (oldQrtzCronTriggersOfQrtzTriggers != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersNew + " already has an item of type QrtzCronTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (qrtzTriggersNew != null) {
                qrtzTriggersNew = em.getReference(qrtzTriggersNew.getClass(), qrtzTriggersNew.getQrtzTriggersPK());
                qrtzCronTriggers.setQrtzTriggers(qrtzTriggersNew);
            }
            qrtzCronTriggers = em.merge(qrtzCronTriggers);
            if (qrtzTriggersOld != null && !qrtzTriggersOld.equals(qrtzTriggersNew)) {
                qrtzTriggersOld.setQrtzCronTriggers(null);
                qrtzTriggersOld = em.merge(qrtzTriggersOld);
            }
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                qrtzTriggersNew.setQrtzCronTriggers(qrtzCronTriggers);
                qrtzTriggersNew = em.merge(qrtzTriggersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzCronTriggersPK id = qrtzCronTriggers.getQrtzCronTriggersPK();
                if (findQrtzCronTriggers(id) == null) {
                    throw new NonexistentEntityException("The qrtzCronTriggers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzCronTriggersPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzCronTriggers qrtzCronTriggers;
            try {
                qrtzCronTriggers = em.getReference(QrtzCronTriggers.class, id);
                qrtzCronTriggers.getQrtzCronTriggersPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzCronTriggers with id " + id + " no longer exists.", enfe);
            }
            QrtzTriggers qrtzTriggers = qrtzCronTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzCronTriggers(null);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.remove(qrtzCronTriggers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzCronTriggers> findQrtzCronTriggersEntities() {
        return findQrtzCronTriggersEntities(true, -1, -1);
    }

    public List<QrtzCronTriggers> findQrtzCronTriggersEntities(int maxResults, int firstResult) {
        return findQrtzCronTriggersEntities(false, maxResults, firstResult);
    }

    private List<QrtzCronTriggers> findQrtzCronTriggersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzCronTriggers.class));
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

    public QrtzCronTriggers findQrtzCronTriggers(QrtzCronTriggersPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzCronTriggers.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzCronTriggersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzCronTriggers> rt = cq.from(QrtzCronTriggers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
