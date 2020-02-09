/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.increm.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.p03.makpsb.core.entity.dict.exceptions.NonexistentEntityException;
import ru.p03.makpsb.increm.model.ClsPeriodType;
import ru.p03.makpsb.increm.model.ClsSignature;
import ru.p03.makpsb.increm.model.RegDetectedSignature;
import ru.p03.vmvp.utils.CommonUtils;

/**
 *
 * @author timofeevan
 */
public class RegDetectedSignatureRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final String persistenceUnit = "MPIncrem";

    public RegDetectedSignatureRepository() {
        this.emf = MPIncremDAO.instance().getEntityManagerFactory();//this.emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    public RegDetectedSignatureRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private void editHistorically(RegDetectedSignature _regDetectedSignature, EntityManager em) throws NonexistentEntityException, Exception {
        List<RegDetectedSignature> history = getHistory(_regDetectedSignature);
        for (RegDetectedSignature rds : history) {
            try {
                RegDetectedSignature persistentRegDetectedSignature = em.find(RegDetectedSignature.class, rds.getId());

                rds.setTimeFix(_regDetectedSignature.getTimeFix());

                ClsSignature idSignatureOld = persistentRegDetectedSignature.getIdSignature();
                ClsSignature idSignatureNew = rds.getIdSignature();
                if (idSignatureNew != null) {
                    idSignatureNew = em.getReference(idSignatureNew.getClass(), idSignatureNew.getId());
                    rds.setIdSignature(idSignatureNew);
                }
                rds = em.merge(rds);

            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public void create(RegDetectedSignature regDetectedSignature) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClsSignature idSignature = regDetectedSignature.getIdSignature();
            if (idSignature != null) {
                idSignature = em.getReference(idSignature.getClass(), idSignature.getId());
                regDetectedSignature.setIdSignature(idSignature);
            }
            em.persist(regDetectedSignature);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegDetectedSignature regDetectedSignature, boolean historically) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegDetectedSignature persistentRegDetectedSignature = em.find(RegDetectedSignature.class, regDetectedSignature.getId());
            ClsSignature idSignatureOld = persistentRegDetectedSignature.getIdSignature();
            ClsSignature idSignatureNew = regDetectedSignature.getIdSignature();
            if (idSignatureNew != null) {
                idSignatureNew = em.getReference(idSignatureNew.getClass(), idSignatureNew.getId());
                regDetectedSignature.setIdSignature(idSignatureNew);
            }
            regDetectedSignature = em.merge(regDetectedSignature);

            if (historically) {
                editHistorically(regDetectedSignature, em);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = regDetectedSignature.getId();
                if (findRegDetectedSignature(id) == null) {
                    throw new NonexistentEntityException("The regDetectedSignature with id " + id + " no longer exists.");
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
            RegDetectedSignature regDetectedSignature;
            try {
                regDetectedSignature = em.getReference(RegDetectedSignature.class, id);
                regDetectedSignature.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regDetectedSignature with id " + id + " no longer exists.", enfe);
            }
            em.remove(regDetectedSignature);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegDetectedSignature> findRegDetectedSignatureEntities() {
        return findRegDetectedSignatureEntities(true, -1, -1);
    }

    public List<RegDetectedSignature> findRegDetectedSignatureEntities(int maxResults, int firstResult) {
        return findRegDetectedSignatureEntities(false, maxResults, firstResult);
    }

    private List<RegDetectedSignature> findRegDetectedSignatureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegDetectedSignature.class));
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

    public RegDetectedSignature findRegDetectedSignature(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegDetectedSignature.class, id);
        } finally {
            em.close();
        }
    }

    public List<RegDetectedSignature> findRegDetectedSignatureEntities(Long idSignature, Date date, boolean onlyNoFixed) {
        EntityManager em = getEntityManager();
        List<RegDetectedSignature> resultList = new ArrayList<>();
        try {
            //'2017-02-19-23.59.59.000000000000'
            Query query = em.createNativeQuery("SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_SIGNATURE(?, ?, ?)) AS VALS", RegDetectedSignature.class);
            query.setParameter(1, CommonUtils.endDay(date));
            query.setParameter(2, idSignature);
            query.setParameter(3, onlyNoFixed ? 1 : 0);
            resultList = query.getResultList();
        } finally {
            em.close();
        }
        return resultList;
    }

    public List<RegDetectedSignature> findRegDetectedSignatureEntitiesOnType(Long idSignatureType, Date date, boolean onlyNoFixed) {
        EntityManager em = getEntityManager();
        List<RegDetectedSignature> resultList = new ArrayList<>();
        try {
            //'2017-02-19-23.59.59.000000000000'
            Query query = em.createNativeQuery("SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_TYPE(?, ?, ?)) AS VALS", RegDetectedSignature.class);
            query.setParameter(1, CommonUtils.endDay(date));
            query.setParameter(2, idSignatureType);
            query.setParameter(3, onlyNoFixed ? 1 : 0);
            resultList = query.getResultList();
        } finally {
            em.close();
        }
        return resultList;
    } //SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_SNILS(CURRENT_DATE, '110-347-118 96', 1)) AS VALS

    public List<RegDetectedSignature> findRegDetectedSignatureSnils(String snils, Date date, boolean onlyNoFixed) {
        EntityManager em = getEntityManager();
        List<RegDetectedSignature> resultList = new ArrayList<>();
        try {
            //'2017-02-19-23.59.59.000000000000'
            Query query = em.createNativeQuery("SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_SNILS(?, ?, ?)) AS VALS", RegDetectedSignature.class);
            query.setParameter(1, CommonUtils.endDay(date));
            query.setParameter(2, snils);
            query.setParameter(3, onlyNoFixed ? 1 : 0);
            resultList = query.getResultList();
        } finally {
            em.close();
        }
        return resultList;
    }

    public List<RegDetectedSignature> findRegDetectedSignatureEntities(Long idSignature, Long raion, String snils, Date date, boolean onlyNoFixed) {
        EntityManager em = getEntityManager();
        List<RegDetectedSignature> resultList = new ArrayList<>();
        try {
            //'2017-02-19-23.59.59.000000000000'
            String where = fillWhere(raion, snils);

            Query query = em.createNativeQuery("SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_SIGNATURE(?, ?, ?)) AS VALS " + where, RegDetectedSignature.class);
            query.setParameter(1, CommonUtils.endDay(date));
            query.setParameter(2, idSignature);
            query.setParameter(3, onlyNoFixed ? 1 : 0);
            if (raion != 0 && !snils.isEmpty()) {
                query.setParameter(4, raion);
                query.setParameter(5, snils);
            }
            if (raion == 0 && !snils.isEmpty()) {
                query.setParameter(4, snils);
            }
            if (raion != 0 && snils.isEmpty()) {
                query.setParameter(4, raion);
            }

            resultList = query.getResultList();
        } finally {
            em.close();
        }
        return resultList;
    }

    public List<RegDetectedSignature> findRegDetectedSignatureEntitiesOnType(Long idSignatureType, Long raion, String snils, Date date, boolean onlyNoFixed) {
        EntityManager em = getEntityManager();
        List<RegDetectedSignature> resultList = new ArrayList<>();
        try {
            //'2017-02-19-23.59.59.000000000000'
            String where = fillWhere(raion, snils);

            Query query = em.createNativeQuery("SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_TYPE(?, ?, ?)) AS VALS " + where, RegDetectedSignature.class);
            query.setParameter(1, CommonUtils.endDay(date));
            query.setParameter(2, idSignatureType);
            query.setParameter(3, onlyNoFixed ? 1 : 0);
            if (raion != 0 && !snils.isEmpty()) {
                query.setParameter(4, raion);
                query.setParameter(5, snils);
            }
            if (raion == 0 && !snils.isEmpty()) {
                query.setParameter(4, snils);
            }
            if (raion != 0 && snils.isEmpty()) {
                query.setParameter(4, raion);
            }

            resultList = query.getResultList();
        } finally {
            em.close();
        }
        return resultList;
    } //SELECT * FROM TABLE(MP_INCREM.SLICE_DETECTED_SIGNATURE_ON_SNILS(CURRENT_DATE, '110-347-118 96', 1)) AS VALS

    private String fillWhere(Long raion, String snils) {
        String where = "";
        if (raion != 0 && !snils.isEmpty()) {
            where = "where id_raion = ? and snils = ?";
        }
        if (raion == 0 && !snils.isEmpty()) {
            where = "where snils = ?";
        }
        if (raion != 0 && snils.isEmpty()) {
            where = "where id_raion =  ?";
        }
        return where;
    }

    public int getRegDetectedSignatureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegDetectedSignature> rt = cq.from(RegDetectedSignature.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<RegDetectedSignature> getHistory(RegDetectedSignature rds) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM MP_INCREM.REG_DETECTED_SIGNATURE"
                + " WHERE SNILS = ? AND FAM = ? AND IM = ? AND OTC = ? AND ID_SIGNATURE = ? AND ID_RAION = ?"
                + " AND IS_DELETED = 0 AND TIME_FIX IS NULL AND ID <> ?", RegDetectedSignature.class);
        query.setParameter(1, rds.getSnils());
        query.setParameter(2, rds.getFam());
        query.setParameter(3, rds.getIm());
        query.setParameter(4, rds.getOtc());
        query.setParameter(5, rds.getIdSignature().getId());
        query.setParameter(6, rds.getIdRaion());
        query.setParameter(7, rds.getId());

        List<RegDetectedSignature> resultList = query.getResultList();
        return resultList;
    }

}
