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
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.p03.makpsb.core.entity.quartz.QrtzJobDetails;
import ru.p03.makpsb.core.entity.quartz.QrtzJobDetailsPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzJobDetailsJpaController implements Serializable {

    public QrtzJobDetailsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzJobDetails qrtzJobDetails) throws PreexistingEntityException, Exception {
        if (qrtzJobDetails.getQrtzJobDetailsPK() == null) {
            qrtzJobDetails.setQrtzJobDetailsPK(new QrtzJobDetailsPK());
        }
        if (qrtzJobDetails.getQrtzTriggersCollection() == null) {
            qrtzJobDetails.setQrtzTriggersCollection(new ArrayList<QrtzTriggers>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<QrtzTriggers> attachedQrtzTriggersCollection = new ArrayList<QrtzTriggers>();
            for (QrtzTriggers qrtzTriggersCollectionQrtzTriggersToAttach : qrtzJobDetails.getQrtzTriggersCollection()) {
                qrtzTriggersCollectionQrtzTriggersToAttach = em.getReference(qrtzTriggersCollectionQrtzTriggersToAttach.getClass(), qrtzTriggersCollectionQrtzTriggersToAttach.getQrtzTriggersPK());
                attachedQrtzTriggersCollection.add(qrtzTriggersCollectionQrtzTriggersToAttach);
            }
            qrtzJobDetails.setQrtzTriggersCollection(attachedQrtzTriggersCollection);
            em.persist(qrtzJobDetails);
            for (QrtzTriggers qrtzTriggersCollectionQrtzTriggers : qrtzJobDetails.getQrtzTriggersCollection()) {
                QrtzJobDetails oldQrtzJobDetailsOfQrtzTriggersCollectionQrtzTriggers = qrtzTriggersCollectionQrtzTriggers.getQrtzJobDetails();
                qrtzTriggersCollectionQrtzTriggers.setQrtzJobDetails(qrtzJobDetails);
                qrtzTriggersCollectionQrtzTriggers = em.merge(qrtzTriggersCollectionQrtzTriggers);
                if (oldQrtzJobDetailsOfQrtzTriggersCollectionQrtzTriggers != null) {
                    oldQrtzJobDetailsOfQrtzTriggersCollectionQrtzTriggers.getQrtzTriggersCollection().remove(qrtzTriggersCollectionQrtzTriggers);
                    oldQrtzJobDetailsOfQrtzTriggersCollectionQrtzTriggers = em.merge(oldQrtzJobDetailsOfQrtzTriggersCollectionQrtzTriggers);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzJobDetails(qrtzJobDetails.getQrtzJobDetailsPK()) != null) {
                throw new PreexistingEntityException("QrtzJobDetails " + qrtzJobDetails + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzJobDetails qrtzJobDetails) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzJobDetails persistentQrtzJobDetails = em.find(QrtzJobDetails.class, qrtzJobDetails.getQrtzJobDetailsPK());
            Collection<QrtzTriggers> qrtzTriggersCollectionOld = persistentQrtzJobDetails.getQrtzTriggersCollection();
            Collection<QrtzTriggers> qrtzTriggersCollectionNew = qrtzJobDetails.getQrtzTriggersCollection();
            List<String> illegalOrphanMessages = null;
            for (QrtzTriggers qrtzTriggersCollectionOldQrtzTriggers : qrtzTriggersCollectionOld) {
                if (!qrtzTriggersCollectionNew.contains(qrtzTriggersCollectionOldQrtzTriggers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain QrtzTriggers " + qrtzTriggersCollectionOldQrtzTriggers + " since its qrtzJobDetails field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<QrtzTriggers> attachedQrtzTriggersCollectionNew = new ArrayList<QrtzTriggers>();
            for (QrtzTriggers qrtzTriggersCollectionNewQrtzTriggersToAttach : qrtzTriggersCollectionNew) {
                qrtzTriggersCollectionNewQrtzTriggersToAttach = em.getReference(qrtzTriggersCollectionNewQrtzTriggersToAttach.getClass(), qrtzTriggersCollectionNewQrtzTriggersToAttach.getQrtzTriggersPK());
                attachedQrtzTriggersCollectionNew.add(qrtzTriggersCollectionNewQrtzTriggersToAttach);
            }
            qrtzTriggersCollectionNew = attachedQrtzTriggersCollectionNew;
            qrtzJobDetails.setQrtzTriggersCollection(qrtzTriggersCollectionNew);
            qrtzJobDetails = em.merge(qrtzJobDetails);
            for (QrtzTriggers qrtzTriggersCollectionNewQrtzTriggers : qrtzTriggersCollectionNew) {
                if (!qrtzTriggersCollectionOld.contains(qrtzTriggersCollectionNewQrtzTriggers)) {
                    QrtzJobDetails oldQrtzJobDetailsOfQrtzTriggersCollectionNewQrtzTriggers = qrtzTriggersCollectionNewQrtzTriggers.getQrtzJobDetails();
                    qrtzTriggersCollectionNewQrtzTriggers.setQrtzJobDetails(qrtzJobDetails);
                    qrtzTriggersCollectionNewQrtzTriggers = em.merge(qrtzTriggersCollectionNewQrtzTriggers);
                    if (oldQrtzJobDetailsOfQrtzTriggersCollectionNewQrtzTriggers != null && !oldQrtzJobDetailsOfQrtzTriggersCollectionNewQrtzTriggers.equals(qrtzJobDetails)) {
                        oldQrtzJobDetailsOfQrtzTriggersCollectionNewQrtzTriggers.getQrtzTriggersCollection().remove(qrtzTriggersCollectionNewQrtzTriggers);
                        oldQrtzJobDetailsOfQrtzTriggersCollectionNewQrtzTriggers = em.merge(oldQrtzJobDetailsOfQrtzTriggersCollectionNewQrtzTriggers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzJobDetailsPK id = qrtzJobDetails.getQrtzJobDetailsPK();
                if (findQrtzJobDetails(id) == null) {
                    throw new NonexistentEntityException("The qrtzJobDetails with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzJobDetailsPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzJobDetails qrtzJobDetails;
            try {
                qrtzJobDetails = em.getReference(QrtzJobDetails.class, id);
                qrtzJobDetails.getQrtzJobDetailsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzJobDetails with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<QrtzTriggers> qrtzTriggersCollectionOrphanCheck = qrtzJobDetails.getQrtzTriggersCollection();
            for (QrtzTriggers qrtzTriggersCollectionOrphanCheckQrtzTriggers : qrtzTriggersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This QrtzJobDetails (" + qrtzJobDetails + ") cannot be destroyed since the QrtzTriggers " + qrtzTriggersCollectionOrphanCheckQrtzTriggers + " in its qrtzTriggersCollection field has a non-nullable qrtzJobDetails field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(qrtzJobDetails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzJobDetails> findQrtzJobDetailsEntities() {
        return findQrtzJobDetailsEntities(true, -1, -1);
    }

    public List<QrtzJobDetails> findQrtzJobDetailsEntities(int maxResults, int firstResult) {
        return findQrtzJobDetailsEntities(false, maxResults, firstResult);
    }

    private List<QrtzJobDetails> findQrtzJobDetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzJobDetails.class));
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

    public QrtzJobDetails findQrtzJobDetails(QrtzJobDetailsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzJobDetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzJobDetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzJobDetails> rt = cq.from(QrtzJobDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
