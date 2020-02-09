/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.entity.dict.exceptions.NonexistentEntityException;

/**
 *
 * @author timofeevan
 */
public class RaionJpaController implements Serializable {

    public RaionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Raion> findRaionEntities() {
        return findRaionEntities(true, -1, -1);
    }

    public List<Raion> findRaionEntities(int maxResults, int firstResult) {
        return findRaionEntities(false, maxResults, firstResult);
    }

    private List<Raion> findRaionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root from = cq.from(Raion.class);
            cq.select(from);
            
            List<Order> orderList = new ArrayList();
            orderList.add(em.getCriteriaBuilder().asc(from.get("nomer")));
            cq.orderBy(orderList);
            
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

    public Raion findRaion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Raion.class, id);
        } finally {
            em.close();
        }
    }

    public int getRaionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Raion> rt = cq.from(Raion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
