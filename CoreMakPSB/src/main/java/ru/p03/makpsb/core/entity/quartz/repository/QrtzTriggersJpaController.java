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
import ru.p03.makpsb.core.entity.quartz.QrtzSimpleTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzCronTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzSimpropTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzJobDetails;
import ru.p03.makpsb.core.entity.quartz.QrtzBlobTriggers;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.p03.makpsb.core.entity.quartz.QrtzTriggers;
import ru.p03.makpsb.core.entity.quartz.QrtzTriggersPK;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;
import ru.p03.makpsb.increm.repository.MPIncremDAO;

/**
 *
 * @author timofeevan
 */
public class QrtzTriggersJpaController implements Serializable {

    public QrtzTriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public QrtzTriggersJpaController() {
         this.emf = MPIncremDAO.instance().getEntityManagerFactory();//this.emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzTriggers qrtzTriggers) throws PreexistingEntityException, Exception {
        if (qrtzTriggers.getQrtzTriggersPK() == null) {
            qrtzTriggers.setQrtzTriggersPK(new QrtzTriggersPK());
        }
        qrtzTriggers.getQrtzTriggersPK().setSchedName(qrtzTriggers.getQrtzJobDetails().getQrtzJobDetailsPK().getSchedName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzSimpleTriggers qrtzSimpleTriggers = qrtzTriggers.getQrtzSimpleTriggers();
            if (qrtzSimpleTriggers != null) {
                qrtzSimpleTriggers = em.getReference(qrtzSimpleTriggers.getClass(), qrtzSimpleTriggers.getQrtzSimpleTriggersPK());
                qrtzTriggers.setQrtzSimpleTriggers(qrtzSimpleTriggers);
            }
            QrtzCronTriggers qrtzCronTriggers = qrtzTriggers.getQrtzCronTriggers();
            if (qrtzCronTriggers != null) {
                qrtzCronTriggers = em.getReference(qrtzCronTriggers.getClass(), qrtzCronTriggers.getQrtzCronTriggersPK());
                qrtzTriggers.setQrtzCronTriggers(qrtzCronTriggers);
            }
            QrtzSimpropTriggers qrtzSimpropTriggers = qrtzTriggers.getQrtzSimpropTriggers();
            if (qrtzSimpropTriggers != null) {
                qrtzSimpropTriggers = em.getReference(qrtzSimpropTriggers.getClass(), qrtzSimpropTriggers.getQrtzSimpropTriggersPK());
                qrtzTriggers.setQrtzSimpropTriggers(qrtzSimpropTriggers);
            }
            QrtzJobDetails qrtzJobDetails = qrtzTriggers.getQrtzJobDetails();
            if (qrtzJobDetails != null) {
                qrtzJobDetails = em.getReference(qrtzJobDetails.getClass(), qrtzJobDetails.getQrtzJobDetailsPK());
                qrtzTriggers.setQrtzJobDetails(qrtzJobDetails);
            }
            QrtzBlobTriggers qrtzBlobTriggers = qrtzTriggers.getQrtzBlobTriggers();
            if (qrtzBlobTriggers != null) {
                qrtzBlobTriggers = em.getReference(qrtzBlobTriggers.getClass(), qrtzBlobTriggers.getQrtzBlobTriggersPK());
                qrtzTriggers.setQrtzBlobTriggers(qrtzBlobTriggers);
            }
            em.persist(qrtzTriggers);
            if (qrtzSimpleTriggers != null) {
                QrtzTriggers oldQrtzTriggersOfQrtzSimpleTriggers = qrtzSimpleTriggers.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzSimpleTriggers != null) {
                    oldQrtzTriggersOfQrtzSimpleTriggers.setQrtzSimpleTriggers(null);
                    oldQrtzTriggersOfQrtzSimpleTriggers = em.merge(oldQrtzTriggersOfQrtzSimpleTriggers);
                }
                qrtzSimpleTriggers.setQrtzTriggers(qrtzTriggers);
                qrtzSimpleTriggers = em.merge(qrtzSimpleTriggers);
            }
            if (qrtzCronTriggers != null) {
                QrtzTriggers oldQrtzTriggersOfQrtzCronTriggers = qrtzCronTriggers.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzCronTriggers != null) {
                    oldQrtzTriggersOfQrtzCronTriggers.setQrtzCronTriggers(null);
                    oldQrtzTriggersOfQrtzCronTriggers = em.merge(oldQrtzTriggersOfQrtzCronTriggers);
                }
                qrtzCronTriggers.setQrtzTriggers(qrtzTriggers);
                qrtzCronTriggers = em.merge(qrtzCronTriggers);
            }
            if (qrtzSimpropTriggers != null) {
                QrtzTriggers oldQrtzTriggersOfQrtzSimpropTriggers = qrtzSimpropTriggers.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzSimpropTriggers != null) {
                    oldQrtzTriggersOfQrtzSimpropTriggers.setQrtzSimpropTriggers(null);
                    oldQrtzTriggersOfQrtzSimpropTriggers = em.merge(oldQrtzTriggersOfQrtzSimpropTriggers);
                }
                qrtzSimpropTriggers.setQrtzTriggers(qrtzTriggers);
                qrtzSimpropTriggers = em.merge(qrtzSimpropTriggers);
            }
            if (qrtzJobDetails != null) {
                qrtzJobDetails.getQrtzTriggersCollection().add(qrtzTriggers);
                qrtzJobDetails = em.merge(qrtzJobDetails);
            }
            if (qrtzBlobTriggers != null) {
                QrtzTriggers oldQrtzTriggersOfQrtzBlobTriggers = qrtzBlobTriggers.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzBlobTriggers != null) {
                    oldQrtzTriggersOfQrtzBlobTriggers.setQrtzBlobTriggers(null);
                    oldQrtzTriggersOfQrtzBlobTriggers = em.merge(oldQrtzTriggersOfQrtzBlobTriggers);
                }
                qrtzBlobTriggers.setQrtzTriggers(qrtzTriggers);
                qrtzBlobTriggers = em.merge(qrtzBlobTriggers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzTriggers(qrtzTriggers.getQrtzTriggersPK()) != null) {
                throw new PreexistingEntityException("QrtzTriggers " + qrtzTriggers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzTriggers qrtzTriggers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        qrtzTriggers.getQrtzTriggersPK().setSchedName(qrtzTriggers.getQrtzJobDetails().getQrtzJobDetailsPK().getSchedName());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzTriggers persistentQrtzTriggers = em.find(QrtzTriggers.class, qrtzTriggers.getQrtzTriggersPK());
            QrtzSimpleTriggers qrtzSimpleTriggersOld = persistentQrtzTriggers.getQrtzSimpleTriggers();
            QrtzSimpleTriggers qrtzSimpleTriggersNew = qrtzTriggers.getQrtzSimpleTriggers();
            QrtzCronTriggers qrtzCronTriggersOld = persistentQrtzTriggers.getQrtzCronTriggers();
            QrtzCronTriggers qrtzCronTriggersNew = qrtzTriggers.getQrtzCronTriggers();
            QrtzSimpropTriggers qrtzSimpropTriggersOld = persistentQrtzTriggers.getQrtzSimpropTriggers();
            QrtzSimpropTriggers qrtzSimpropTriggersNew = qrtzTriggers.getQrtzSimpropTriggers();
            QrtzJobDetails qrtzJobDetailsOld = persistentQrtzTriggers.getQrtzJobDetails();
            QrtzJobDetails qrtzJobDetailsNew = qrtzTriggers.getQrtzJobDetails();
            QrtzBlobTriggers qrtzBlobTriggersOld = persistentQrtzTriggers.getQrtzBlobTriggers();
            QrtzBlobTriggers qrtzBlobTriggersNew = qrtzTriggers.getQrtzBlobTriggers();
            List<String> illegalOrphanMessages = null;
            if (qrtzSimpleTriggersOld != null && !qrtzSimpleTriggersOld.equals(qrtzSimpleTriggersNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain QrtzSimpleTriggers " + qrtzSimpleTriggersOld + " since its qrtzTriggers field is not nullable.");
            }
            if (qrtzCronTriggersOld != null && !qrtzCronTriggersOld.equals(qrtzCronTriggersNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain QrtzCronTriggers " + qrtzCronTriggersOld + " since its qrtzTriggers field is not nullable.");
            }
            if (qrtzSimpropTriggersOld != null && !qrtzSimpropTriggersOld.equals(qrtzSimpropTriggersNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain QrtzSimpropTriggers " + qrtzSimpropTriggersOld + " since its qrtzTriggers field is not nullable.");
            }
            if (qrtzBlobTriggersOld != null && !qrtzBlobTriggersOld.equals(qrtzBlobTriggersNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain QrtzBlobTriggers " + qrtzBlobTriggersOld + " since its qrtzTriggers field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (qrtzSimpleTriggersNew != null) {
                qrtzSimpleTriggersNew = em.getReference(qrtzSimpleTriggersNew.getClass(), qrtzSimpleTriggersNew.getQrtzSimpleTriggersPK());
                qrtzTriggers.setQrtzSimpleTriggers(qrtzSimpleTriggersNew);
            }
            if (qrtzCronTriggersNew != null) {
                qrtzCronTriggersNew = em.getReference(qrtzCronTriggersNew.getClass(), qrtzCronTriggersNew.getQrtzCronTriggersPK());
                qrtzTriggers.setQrtzCronTriggers(qrtzCronTriggersNew);
            }
            if (qrtzSimpropTriggersNew != null) {
                qrtzSimpropTriggersNew = em.getReference(qrtzSimpropTriggersNew.getClass(), qrtzSimpropTriggersNew.getQrtzSimpropTriggersPK());
                qrtzTriggers.setQrtzSimpropTriggers(qrtzSimpropTriggersNew);
            }
            if (qrtzJobDetailsNew != null) {
                qrtzJobDetailsNew = em.getReference(qrtzJobDetailsNew.getClass(), qrtzJobDetailsNew.getQrtzJobDetailsPK());
                qrtzTriggers.setQrtzJobDetails(qrtzJobDetailsNew);
            }
            if (qrtzBlobTriggersNew != null) {
                qrtzBlobTriggersNew = em.getReference(qrtzBlobTriggersNew.getClass(), qrtzBlobTriggersNew.getQrtzBlobTriggersPK());
                qrtzTriggers.setQrtzBlobTriggers(qrtzBlobTriggersNew);
            }
            qrtzTriggers = em.merge(qrtzTriggers);
            if (qrtzSimpleTriggersNew != null && !qrtzSimpleTriggersNew.equals(qrtzSimpleTriggersOld)) {
                QrtzTriggers oldQrtzTriggersOfQrtzSimpleTriggers = qrtzSimpleTriggersNew.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzSimpleTriggers != null) {
                    oldQrtzTriggersOfQrtzSimpleTriggers.setQrtzSimpleTriggers(null);
                    oldQrtzTriggersOfQrtzSimpleTriggers = em.merge(oldQrtzTriggersOfQrtzSimpleTriggers);
                }
                qrtzSimpleTriggersNew.setQrtzTriggers(qrtzTriggers);
                qrtzSimpleTriggersNew = em.merge(qrtzSimpleTriggersNew);
            }
            if (qrtzCronTriggersNew != null && !qrtzCronTriggersNew.equals(qrtzCronTriggersOld)) {
                QrtzTriggers oldQrtzTriggersOfQrtzCronTriggers = qrtzCronTriggersNew.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzCronTriggers != null) {
                    oldQrtzTriggersOfQrtzCronTriggers.setQrtzCronTriggers(null);
                    oldQrtzTriggersOfQrtzCronTriggers = em.merge(oldQrtzTriggersOfQrtzCronTriggers);
                }
                qrtzCronTriggersNew.setQrtzTriggers(qrtzTriggers);
                qrtzCronTriggersNew = em.merge(qrtzCronTriggersNew);
            }
            if (qrtzSimpropTriggersNew != null && !qrtzSimpropTriggersNew.equals(qrtzSimpropTriggersOld)) {
                QrtzTriggers oldQrtzTriggersOfQrtzSimpropTriggers = qrtzSimpropTriggersNew.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzSimpropTriggers != null) {
                    oldQrtzTriggersOfQrtzSimpropTriggers.setQrtzSimpropTriggers(null);
                    oldQrtzTriggersOfQrtzSimpropTriggers = em.merge(oldQrtzTriggersOfQrtzSimpropTriggers);
                }
                qrtzSimpropTriggersNew.setQrtzTriggers(qrtzTriggers);
                qrtzSimpropTriggersNew = em.merge(qrtzSimpropTriggersNew);
            }
            if (qrtzJobDetailsOld != null && !qrtzJobDetailsOld.equals(qrtzJobDetailsNew)) {
                qrtzJobDetailsOld.getQrtzTriggersCollection().remove(qrtzTriggers);
                qrtzJobDetailsOld = em.merge(qrtzJobDetailsOld);
            }
            if (qrtzJobDetailsNew != null && !qrtzJobDetailsNew.equals(qrtzJobDetailsOld)) {
                qrtzJobDetailsNew.getQrtzTriggersCollection().add(qrtzTriggers);
                qrtzJobDetailsNew = em.merge(qrtzJobDetailsNew);
            }
            if (qrtzBlobTriggersNew != null && !qrtzBlobTriggersNew.equals(qrtzBlobTriggersOld)) {
                QrtzTriggers oldQrtzTriggersOfQrtzBlobTriggers = qrtzBlobTriggersNew.getQrtzTriggers();
                if (oldQrtzTriggersOfQrtzBlobTriggers != null) {
                    oldQrtzTriggersOfQrtzBlobTriggers.setQrtzBlobTriggers(null);
                    oldQrtzTriggersOfQrtzBlobTriggers = em.merge(oldQrtzTriggersOfQrtzBlobTriggers);
                }
                qrtzBlobTriggersNew.setQrtzTriggers(qrtzTriggers);
                qrtzBlobTriggersNew = em.merge(qrtzBlobTriggersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                QrtzTriggersPK id = qrtzTriggers.getQrtzTriggersPK();
                if (findQrtzTriggers(id) == null) {
                    throw new NonexistentEntityException("The qrtzTriggers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(QrtzTriggersPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzTriggers qrtzTriggers;
            try {
                qrtzTriggers = em.getReference(QrtzTriggers.class, id);
                qrtzTriggers.getQrtzTriggersPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzTriggers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            QrtzSimpleTriggers qrtzSimpleTriggersOrphanCheck = qrtzTriggers.getQrtzSimpleTriggers();
            if (qrtzSimpleTriggersOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This QrtzTriggers (" + qrtzTriggers + ") cannot be destroyed since the QrtzSimpleTriggers " + qrtzSimpleTriggersOrphanCheck + " in its qrtzSimpleTriggers field has a non-nullable qrtzTriggers field.");
            }
            QrtzCronTriggers qrtzCronTriggersOrphanCheck = qrtzTriggers.getQrtzCronTriggers();
            if (qrtzCronTriggersOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This QrtzTriggers (" + qrtzTriggers + ") cannot be destroyed since the QrtzCronTriggers " + qrtzCronTriggersOrphanCheck + " in its qrtzCronTriggers field has a non-nullable qrtzTriggers field.");
            }
            QrtzSimpropTriggers qrtzSimpropTriggersOrphanCheck = qrtzTriggers.getQrtzSimpropTriggers();
            if (qrtzSimpropTriggersOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This QrtzTriggers (" + qrtzTriggers + ") cannot be destroyed since the QrtzSimpropTriggers " + qrtzSimpropTriggersOrphanCheck + " in its qrtzSimpropTriggers field has a non-nullable qrtzTriggers field.");
            }
            QrtzBlobTriggers qrtzBlobTriggersOrphanCheck = qrtzTriggers.getQrtzBlobTriggers();
            if (qrtzBlobTriggersOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This QrtzTriggers (" + qrtzTriggers + ") cannot be destroyed since the QrtzBlobTriggers " + qrtzBlobTriggersOrphanCheck + " in its qrtzBlobTriggers field has a non-nullable qrtzTriggers field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            QrtzJobDetails qrtzJobDetails = qrtzTriggers.getQrtzJobDetails();
            if (qrtzJobDetails != null) {
                qrtzJobDetails.getQrtzTriggersCollection().remove(qrtzTriggers);
                qrtzJobDetails = em.merge(qrtzJobDetails);
            }
            em.remove(qrtzTriggers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzTriggers> findQrtzTriggersEntities() {
        return findQrtzTriggersEntities(true, -1, -1);
    }

    public List<QrtzTriggers> findQrtzTriggersEntities(int maxResults, int firstResult) {
        return findQrtzTriggersEntities(false, maxResults, firstResult);
    }

    private List<QrtzTriggers> findQrtzTriggersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzTriggers.class));
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
    
    public List<QrtzTriggers> getQrtzTriggers(String triggerName) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT p FROM QrtzTriggers p WHERE p.qrtzTriggersPK.triggerName = :triggerName", QrtzTriggers.class);//triggerName
        query.setParameter("triggerName", triggerName);
        return query.getResultList();
    }  

    public QrtzTriggers findQrtzTriggers(QrtzTriggersPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzTriggers.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzTriggersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzTriggers> rt = cq.from(QrtzTriggers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
