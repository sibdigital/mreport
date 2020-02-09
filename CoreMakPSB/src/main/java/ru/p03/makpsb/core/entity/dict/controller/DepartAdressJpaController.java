/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.dict.DepartAdress;
import ru.p03.makpsb.core.entity.dict.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class DepartAdressJpaController implements Serializable {

    public DepartAdressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    public void create(DepartAdress departAdress) {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            em.persist(departAdress);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void edit(DepartAdress departAdress) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            departAdress = em.merge(departAdress);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Long id = departAdress.getId();
//                if (findDepartAdress(id) == null) {
//                    throw new NonexistentEntityException("The departAdress with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

//    public void destroy(Long id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            DepartAdress departAdress;
//            try {
//                departAdress = em.getReference(DepartAdress.class, id);
//                departAdress.getId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The departAdress with id " + id + " no longer exists.", enfe);
//            }
//            em.remove(departAdress);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

    public List<DepartAdress> findDepartAdressEntities() {
        return findDepartAdressEntities(true, -1, -1);
    }

    public List<DepartAdress> findDepartAdressEntities(int maxResults, int firstResult) {
        return findDepartAdressEntities(false, maxResults, firstResult);
    }

    private List<DepartAdress> findDepartAdressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DepartAdress.class));
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

    public DepartAdress findDepartAdress(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DepartAdress.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartAdressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DepartAdress> rt = cq.from(DepartAdress.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
