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
import ru.p03.makpsb.core.entity.quartz.QrtzBlobTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzBlobTriggersPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzBlobTriggersJpaController implements Serializable {

    public QrtzBlobTriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzBlobTriggers qrtzBlobTriggers) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (qrtzBlobTriggers.getQrtzBlobTriggersPK() == null) {
            qrtzBlobTriggers.setQrtzBlobTriggersPK(new QrtzBlobTriggersPK());
        }
        qrtzBlobTriggers.getQrtzBlobTriggersPK().setSchedName(qrtzBlobTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        qrtzBlobTriggers.getQrtzBlobTriggersPK().setTriggerName(qrtzBlobTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        qrtzBlobTriggers.getQrtzBlobTriggersPK().setTriggerGroup(qrtzBlobTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        List<String> illegalOrphanMessages = null;
        QrtzTriggers qrtzTriggersOrphanCheck = qrtzBlobTriggers.getQrtzTriggers();
        if (qrtzTriggersOrphanCheck != null) {
            QrtzBlobTriggers oldQrtzBlobTriggersOfQrtzTriggers = qrtzTriggersOrphanCheck.getQrtzBlobTriggers();
            if (oldQrtzBlobTriggersOfQrtzTriggers != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersOrphanCheck + " already has an item of type QrtzBlobTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzTriggers qrtzTriggers = qrtzBlobTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers = em.getReference(qrtzTriggers.getClass(), qrtzTriggers.getQrtzTriggersPK());
                qrtzBlobTriggers.setQrtzTriggers(qrtzTriggers);
            }
            em.persist(qrtzBlobTriggers);
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzBlobTriggers(qrtzBlobTriggers);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzBlobTriggers(qrtzBlobTriggers.getQrtzBlobTriggersPK()) != null) {
                throw new PreexistingEntityException("QrtzBlobTriggers " + qrtzBlobTriggers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzBlobTriggers qrtzBlobTriggers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        qrtzBlobTriggers.getQrtzBlobTriggersPK().setSchedName(qrtzBlobTriggers.getQrtzTriggers().getQrtzTriggersPK().getSchedName());
        qrtzBlobTriggers.getQrtzBlobTriggersPK().setTriggerName(qrtzBlobTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerName());
        qrtzBlobTriggers.getQrtzBlobTriggersPK().setTriggerGroup(qrtzBlobTriggers.getQrtzTriggers().getQrtzTriggersPK().getTriggerGroup());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzBlobTriggers persistentQrtzBlobTriggers = em.find(QrtzBlobTriggers.class, qrtzBlobTriggers.getQrtzBlobTriggersPK());
            QrtzTriggers qrtzTriggersOld = persistentQrtzBlobTriggers.getQrtzTriggers();
            QrtzTriggers qrtzTriggersNew = qrtzBlobTriggers.getQrtzTriggers();
            List<String> illegalOrphanMessages = null;
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                QrtzBlobTriggers oldQrtzBlobTriggersOfQrtzTriggers = qrtzTriggersNew.getQrtzBlobTriggers();
                if (oldQrtzBlobTriggersOfQrtzTriggers != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The QrtzTriggers " + qrtzTriggersNew + " already has an item of type QrtzBlobTriggers whose qrtzTriggers column cannot be null. Please make another selection for the qrtzTriggers field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (qrtzTriggersNew != null) {
                qrtzTriggersNew = em.getReference(qrtzTriggersNew.getClass(), qrtzTriggersNew.getQrtzTriggersPK());
                qrtzBlobTriggers.setQrtzTriggers(qrtzTriggersNew);
            }
            qrtzBlobTriggers = em.merge(qrtzBlobTriggers);
            if (qrtzTriggersOld != null && !qrtzTriggersOld.equals(qrtzTriggersNew)) {
                qrtzTriggersOld.setQrtzBlobTriggers(null);
                qrtzTriggersOld = em.merge(qrtzTriggersOld);
            }
            if (qrtzTriggersNew != null && !qrtzTriggersNew.equals(qrtzTriggersOld)) {
                qrtzTriggersNew.setQrtzBlobTriggers(qrtzBlobTriggers);
                qrtzTriggersNew = em.merge(qrtzTriggersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzBlobTriggersPK id = qrtzBlobTriggers.getQrtzBlobTriggersPK();
                if (findQrtzBlobTriggers(id) == null) {
                    throw new NonexistentEntityException("The qrtzBlobTriggers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzBlobTriggersPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzBlobTriggers qrtzBlobTriggers;
            try {
                qrtzBlobTriggers = em.getReference(QrtzBlobTriggers.class, id);
                qrtzBlobTriggers.getQrtzBlobTriggersPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzBlobTriggers with id " + id + " no longer exists.", enfe);
            }
            QrtzTriggers qrtzTriggers = qrtzBlobTriggers.getQrtzTriggers();
            if (qrtzTriggers != null) {
                qrtzTriggers.setQrtzBlobTriggers(null);
                qrtzTriggers = em.merge(qrtzTriggers);
            }
            em.remove(qrtzBlobTriggers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzBlobTriggers> findQrtzBlobTriggersEntities() {
        return findQrtzBlobTriggersEntities(true, -1, -1);
    }

    public List<QrtzBlobTriggers> findQrtzBlobTriggersEntities(int maxResults, int firstResult) {
        return findQrtzBlobTriggersEntities(false, maxResults, firstResult);
    }

    private List<QrtzBlobTriggers> findQrtzBlobTriggersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzBlobTriggers.class));
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

    public QrtzBlobTriggers findQrtzBlobTriggers(QrtzBlobTriggersPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzBlobTriggers.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzBlobTriggersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzBlobTriggers> rt = cq.from(QrtzBlobTriggers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
