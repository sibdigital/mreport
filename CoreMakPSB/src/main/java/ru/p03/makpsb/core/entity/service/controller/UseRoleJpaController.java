/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service.controller;

import ru.p03.makpsb.core.entity.base.MPDAO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.service.Role;
import ru.p03.makpsb.core.entity.service.UseRole;
import ru.p03.makpsb.core.entity.service.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class UseRoleJpaController implements Serializable {

    public UseRoleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    } 
    
    public UseRoleJpaController() {
        this.emf = MPDAO.instance().getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UseRole useRole) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role idRole = useRole.getIdRole();
            if (idRole != null) {
                idRole = em.getReference(idRole.getClass(), idRole.getId());
                useRole.setIdRole(idRole);
            }
            em.persist(useRole);
            if (idRole != null) {
                idRole.getUseRoleCollection().add(useRole);
                idRole = em.merge(idRole);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UseRole useRole) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UseRole persistentUseRole = em.find(UseRole.class, useRole.getId());
            Role idRoleOld = persistentUseRole.getIdRole();
            Role idRoleNew = useRole.getIdRole();
            if (idRoleNew != null) {
                idRoleNew = em.getReference(idRoleNew.getClass(), idRoleNew.getId());
                useRole.setIdRole(idRoleNew);
            }
            useRole = em.merge(useRole);
            if (idRoleOld != null && !idRoleOld.equals(idRoleNew)) {
                idRoleOld.getUseRoleCollection().remove(useRole);
                idRoleOld = em.merge(idRoleOld);
            }
            if (idRoleNew != null && !idRoleNew.equals(idRoleOld)) {
                idRoleNew.getUseRoleCollection().add(useRole);
                idRoleNew = em.merge(idRoleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = useRole.getId();
                if (findUseRole(id) == null) {
                    throw new NonexistentEntityException("The useRole with id " + id + " no longer exists.");
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
            UseRole useRole;
            try {
                useRole = em.getReference(UseRole.class, id);
                useRole.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The useRole with id " + id + " no longer exists.", enfe);
            }
            Role idRole = useRole.getIdRole();
            if (idRole != null) {
                idRole.getUseRoleCollection().remove(useRole);
                idRole = em.merge(idRole);
            }
            em.remove(useRole);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UseRole> findUseRoleEntities() {
        return findUseRoleEntities(true, -1, -1);
    }

    public List<UseRole> findUseRoleEntities(int maxResults, int firstResult) {
        return findUseRoleEntities(false, maxResults, firstResult);
    }

    private List<UseRole> findUseRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UseRole.class));
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

    public UseRole findUseRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UseRole.class, id);
        } finally {
            em.close();
        }
    }

    public int getUseRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UseRole> rt = cq.from(UseRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
