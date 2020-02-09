/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.repository;

import ru.p03.makpsb.core.entity.dict.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.dict.exceptions.NonexistentEntityException;
import ru.p03.makpsb.increm.model.ClsDetectPeriod;
import ru.p03.makpsb.increm.model.ClsPeriodType;
import ru.p03.makpsb.increm.model.ClsSignature;
import ru.p03.makpsb.increm.model.SignatureTiming;

/**
 *
 * @author timofeevan
 */
public class DetectPeriodRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    //protected final String persistenceUnit = "MPIncrem";

    public DetectPeriodRepository() {
        this.emf = MPIncremDAO.instance().getEntityManagerFactory();//Persistence.createEntityManagerFactory(persistenceUnit);
    }

    public DetectPeriodRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
//
//    public void create(ClsDetectPeriod clsDetectPeriod) {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            ClsPeriodType idPeriodType = clsDetectPeriod.getIdPeriodType();
//            if (idPeriodType != null) {
//                idPeriodType = em.getReference(idPeriodType.getClass(), idPeriodType.getId());
//                clsDetectPeriod.setIdPeriodType(idPeriodType);
//            }
//            ClsSignature idSignature = clsDetectPeriod.getIdSignature();
//            if (idSignature != null) {
//                idSignature = em.getReference(idSignature.getClass(), idSignature.getId());
//                clsDetectPeriod.setIdSignature(idSignature);
//            }
//            em.persist(clsDetectPeriod);
//            if (idPeriodType != null) {
//                idPeriodType.getClsDetectPeriodCollection().add(clsDetectPeriod);
//                idPeriodType = em.merge(idPeriodType);
//            }
//            if (idSignature != null) {
//                idSignature.getClsDetectPeriodCollection().add(clsDetectPeriod);
//                idSignature = em.merge(idSignature);
//            }
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//

    public void editSignature(SignatureTiming signatureTiming) throws Exception {
        if (signatureTiming == null) {
            throw new Exception("SignatureTiming is null");
        }
        ClsDetectPeriod cdp = findClsDetectPeriod(signatureTiming);
        if (cdp == null) {
            throw new Exception("No detect period");
        }

        cdp.setDayOfMonth(signatureTiming.getDayOfMonth());
        cdp.setDayOfWeek(signatureTiming.getDayOfWeek());
        cdp.setMinute(signatureTiming.getMinute());
        cdp.setHour(signatureTiming.getHour());
        cdp.setIsActive(signatureTiming.getIsActive());

        if (ClsPeriodType.DAILY.equals(cdp.getIdPeriodType().getCode())) {
            cdp.setDayOfMonth(null);
            cdp.setDayOfWeek(null);
        }

        edit(cdp);
    }

    public void edit(ClsDetectPeriod clsDetectPeriod) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClsDetectPeriod persistentClsDetectPeriod = em.find(ClsDetectPeriod.class, clsDetectPeriod.getId());
            ClsPeriodType idPeriodTypeOld = persistentClsDetectPeriod.getIdPeriodType();
            ClsPeriodType idPeriodTypeNew = clsDetectPeriod.getIdPeriodType();
            ClsSignature idSignatureOld = persistentClsDetectPeriod.getIdSignature();
            ClsSignature idSignatureNew = clsDetectPeriod.getIdSignature();
            if (idPeriodTypeNew != null) {
                idPeriodTypeNew = em.getReference(idPeriodTypeNew.getClass(), idPeriodTypeNew.getId());
                clsDetectPeriod.setIdPeriodType(idPeriodTypeNew);
            }
            if (idSignatureNew != null) {
                idSignatureNew = em.getReference(idSignatureNew.getClass(), idSignatureNew.getId());
                clsDetectPeriod.setIdSignature(idSignatureNew);
            }
            clsDetectPeriod = em.merge(clsDetectPeriod);
            if (idPeriodTypeOld != null && !idPeriodTypeOld.equals(idPeriodTypeNew)) {
                idPeriodTypeOld.getClsDetectPeriodCollection().remove(clsDetectPeriod);
                idPeriodTypeOld = em.merge(idPeriodTypeOld);
            }
            if (idPeriodTypeNew != null && !idPeriodTypeNew.equals(idPeriodTypeOld)) {
                idPeriodTypeNew.getClsDetectPeriodCollection().add(clsDetectPeriod);
                idPeriodTypeNew = em.merge(idPeriodTypeNew);
            }
//            if (idSignatureOld != null && !idSignatureOld.equals(idSignatureNew)) {
//                idSignatureOld.getClsDetectPeriodCollection().remove(clsDetectPeriod);
//                idSignatureOld = em.merge(idSignatureOld);
//            }
//            if (idSignatureNew != null && !idSignatureNew.equals(idSignatureOld)) {
//                idSignatureNew.getClsDetectPeriodCollection().add(clsDetectPeriod);
//                idSignatureNew = em.merge(idSignatureNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clsDetectPeriod.getId();
                if (findClsDetectPeriod(id) == null) {
                    throw new NonexistentEntityException("The clsDetectPeriod with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

//    public void destroy(Long id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            ClsDetectPeriod clsDetectPeriod;
//            try {
//                clsDetectPeriod = em.getReference(ClsDetectPeriod.class, id);
//                clsDetectPeriod.getId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The clsDetectPeriod with id " + id + " no longer exists.", enfe);
//            }
//            ClsPeriodType idPeriodType = clsDetectPeriod.getIdPeriodType();
//            if (idPeriodType != null) {
//                idPeriodType.getClsDetectPeriodCollection().remove(clsDetectPeriod);
//                idPeriodType = em.merge(idPeriodType);
//            }
//            ClsSignature idSignature = clsDetectPeriod.getIdSignature();
//            if (idSignature != null) {
//                idSignature.getClsDetectPeriodCollection().remove(clsDetectPeriod);
//                idSignature = em.merge(idSignature);
//            }
//            em.remove(clsDetectPeriod);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public List<ClsDetectPeriod> findClsDetectPeriodEntities() {
//        return findClsDetectPeriodEntities(true, -1, -1);
//    }
//
//    public List<ClsDetectPeriod> findClsDetectPeriodEntities(int maxResults, int firstResult) {
//        return findClsDetectPeriodEntities(false, maxResults, firstResult);
//    }
//
//    private List<ClsDetectPeriod> findClsDetectPeriodEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(ClsDetectPeriod.class));
//            Query q = em.createQuery(cq);
//            if (!all) {
//                q.setMaxResults(maxResults);
//                q.setFirstResult(firstResult);
//            }
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//
    public ClsDetectPeriod findClsDetectPeriod(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClsDetectPeriod.class, id);
        } finally {
            em.close();
        }
    }

    public ClsDetectPeriod findClsDetectPeriod(SignatureTiming signatureTiming) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClsDetectPeriod.class, signatureTiming.getId());
        } finally {
            em.close();
        }
    }
//
//    public int getClsDetectPeriodCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<ClsDetectPeriod> rt = cq.from(ClsDetectPeriod.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
//
}
