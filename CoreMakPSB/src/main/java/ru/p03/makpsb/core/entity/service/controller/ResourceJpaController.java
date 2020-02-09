/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.service.Resource;
import ru.p03.makpsb.core.entity.service.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class ResourceJpaController implements Serializable {

    public ResourceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resource resource) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(resource);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resource resource) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            resource = em.merge(resource);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = resource.getId();
                if (findResource(id) == null) {
                    throw new NonexistentEntityException("The resource with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Resource resource;
            try {
                resource = em.getReference(Resource.class, id);
                resource.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resource with id " + id + " no longer exists.", enfe);
            }
            em.remove(resource);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resource> findResourceEntities() {
        return findResourceEntities(true, -1, -1);
    }

    public List<Resource> findResourceEntities(int maxResults, int firstResult) {
        return findResourceEntities(false, maxResults, firstResult);
    }

    private List<Resource> findResourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resource.class));
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

    public Resource findResource(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resource.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resource> rt = cq.from(Resource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
