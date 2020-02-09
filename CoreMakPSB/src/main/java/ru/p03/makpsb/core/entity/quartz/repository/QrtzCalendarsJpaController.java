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
import ru.p03.makpsb.core.entity.quartz.QrtzCalendars;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.NonexistentEntityException;
import ru.p03.makpsb.core.entity.quartz.repository.exceptions.PreexistingEntityException;

/**
 *
 * @author timofeevan
 */
public class QrtzCalendarsJpaController implements Serializable {

    public QrtzCalendarsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QrtzCalendars qrtzCalendars) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(qrtzCalendars);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQrtzCalendars(qrtzCalendars.getCalendarName()) != null) {
                throw new PreexistingEntityException("QrtzCalendars " + qrtzCalendars + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QrtzCalendars qrtzCalendars) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            qrtzCalendars = em.merge(qrtzCalendars);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = qrtzCalendars.getCalendarName();
                if (findQrtzCalendars(id) == null) {
                    throw new NonexistentEntityException("The qrtzCalendars with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QrtzCalendars qrtzCalendars;
            try {
                qrtzCalendars = em.getReference(QrtzCalendars.class, id);
                qrtzCalendars.getCalendarName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qrtzCalendars with id " + id + " no longer exists.", enfe);
            }
            em.remove(qrtzCalendars);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QrtzCalendars> findQrtzCalendarsEntities() {
        return findQrtzCalendarsEntities(true, -1, -1);
    }

    public List<QrtzCalendars> findQrtzCalendarsEntities(int maxResults, int firstResult) {
        return findQrtzCalendarsEntities(false, maxResults, firstResult);
    }

    private List<QrtzCalendars> findQrtzCalendarsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QrtzCalendars.class));
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

    public QrtzCalendars findQrtzCalendars(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QrtzCalendars.class, id);
        } finally {
            em.close();
        }
    }

    public int getQrtzCalendarsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QrtzCalendars> rt = cq.from(QrtzCalendars.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
