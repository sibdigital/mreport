/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.service.UseRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.p03.makpsb.core.entity.service.Role;
import ru.p03.makpsb.core.entity.service.controller.exceptions.IllegalOrphanException;
import ru.p03.makpsb.core.entity.service.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class RoleJpaController implements Serializable {

    public RoleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Role role) {
        if (role.getUseRoleCollection() == null) {
            role.setUseRoleCollection(new ArrayList<UseRole>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UseRole> attachedUseRoleCollection = new ArrayList<UseRole>();
            for (UseRole useRoleCollectionUseRoleToAttach : role.getUseRoleCollection()) {
                useRoleCollectionUseRoleToAttach = em.getReference(useRoleCollectionUseRoleToAttach.getClass(), useRoleCollectionUseRoleToAttach.getId());
                attachedUseRoleCollection.add(useRoleCollectionUseRoleToAttach);
            }
            role.setUseRoleCollection(attachedUseRoleCollection);
            em.persist(role);
            for (UseRole useRoleCollectionUseRole : role.getUseRoleCollection()) {
                Role oldIdRoleOfUseRoleCollectionUseRole = useRoleCollectionUseRole.getIdRole();
                useRoleCollectionUseRole.setIdRole(role);
                useRoleCollectionUseRole = em.merge(useRoleCollectionUseRole);
                if (oldIdRoleOfUseRoleCollectionUseRole != null) {
                    oldIdRoleOfUseRoleCollectionUseRole.getUseRoleCollection().remove(useRoleCollectionUseRole);
                    oldIdRoleOfUseRoleCollectionUseRole = em.merge(oldIdRoleOfUseRoleCollectionUseRole);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Role role) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role persistentRole = em.find(Role.class, role.getId());
            Collection<UseRole> useRoleCollectionOld = persistentRole.getUseRoleCollection();
            Collection<UseRole> useRoleCollectionNew = role.getUseRoleCollection();
            List<String> illegalOrphanMessages = null;
            for (UseRole useRoleCollectionOldUseRole : useRoleCollectionOld) {
                if (!useRoleCollectionNew.contains(useRoleCollectionOldUseRole)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UseRole " + useRoleCollectionOldUseRole + " since its idRole field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UseRole> attachedUseRoleCollectionNew = new ArrayList<UseRole>();
            for (UseRole useRoleCollectionNewUseRoleToAttach : useRoleCollectionNew) {
                useRoleCollectionNewUseRoleToAttach = em.getReference(useRoleCollectionNewUseRoleToAttach.getClass(), useRoleCollectionNewUseRoleToAttach.getId());
                attachedUseRoleCollectionNew.add(useRoleCollectionNewUseRoleToAttach);
            }
            useRoleCollectionNew = attachedUseRoleCollectionNew;
            role.setUseRoleCollection(useRoleCollectionNew);
            role = em.merge(role);
            for (UseRole useRoleCollectionNewUseRole : useRoleCollectionNew) {
                if (!useRoleCollectionOld.contains(useRoleCollectionNewUseRole)) {
                    Role oldIdRoleOfUseRoleCollectionNewUseRole = useRoleCollectionNewUseRole.getIdRole();
                    useRoleCollectionNewUseRole.setIdRole(role);
                    useRoleCollectionNewUseRole = em.merge(useRoleCollectionNewUseRole);
                    if (oldIdRoleOfUseRoleCollectionNewUseRole != null && !oldIdRoleOfUseRoleCollectionNewUseRole.equals(role)) {
                        oldIdRoleOfUseRoleCollectionNewUseRole.getUseRoleCollection().remove(useRoleCollectionNewUseRole);
                        oldIdRoleOfUseRoleCollectionNewUseRole = em.merge(oldIdRoleOfUseRoleCollectionNewUseRole);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = role.getId();
                if (findRole(id) == null) {
                    throw new NonexistentEntityException("The role with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role role;
            try {
                role = em.getReference(Role.class, id);
                role.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The role with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UseRole> useRoleCollectionOrphanCheck = role.getUseRoleCollection();
            for (UseRole useRoleCollectionOrphanCheckUseRole : useRoleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Role (" + role + ") cannot be destroyed since the UseRole " + useRoleCollectionOrphanCheckUseRole + " in its useRoleCollection field has a non-nullable idRole field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(role);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Role> findRoleEntities() {
        return findRoleEntities(true, -1, -1);
    }

    public List<Role> findRoleEntities(int maxResults, int firstResult) {
        return findRoleEntities(false, maxResults, firstResult);
    }

    private List<Role> findRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Role.class));
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

    public Role findRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Role> rt = cq.from(Role.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
