/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.dynreport;

import ru.p03.common.test.TestConfig;
import ru.p03.common.test.TestConfigBuilder;
import ru.p03.common.test.TestPersistenceProvider;
import ru.p03.makpsb.core.entity.dict.Raion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author timofeevan
 */
public class SQLDynReportJpaController implements Serializable {
    
    public static final String DATE_BEGIN = "dateBegin";
    public static final String DATE_END = "dateEnd";
    public static final String RAION = "raion";
    
    protected final String persistenceUnit = "CoreMakPSBPU";

    private final ActualDynReport actualDynReport;
    
    public SQLDynReportJpaController(ActualDynReport adr){
        super();
        this.actualDynReport = adr;
        if (adr.getDatasource() == null || adr.getDatasource().isEmpty()){
            createEmf();
        }else{
            this.emf = Persistence.createEntityManagerFactory(adr.getPersistenceunit());
        }

    }

    private void createEmf(){
        TestConfig testConfig = new TestConfigBuilder()
                .setDriver(this.actualDynReport.getDriver())
                .setUser(this.actualDynReport.getUser())
                .setPassword(this.actualDynReport.getPassword())
                .setDbUrl(this.actualDynReport.getUrl())
                .build();

        String puName =  this.actualDynReport.getPersistenceunit();
        TestPersistenceProvider provider = new TestPersistenceProvider(testConfig, puName);
        this.emf = provider.getEntityManagerFactory();

    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List list() {
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(actualDynReport.getSqlText());//"EdvManResults"
        List resultList = nquery.getResultList();
        return resultList;
    }
    
    public List list(Date dateBegin) {
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(actualDynReport.getSqlText());//"EdvManResults"
        nquery.setParameter(DATE_BEGIN, dateBegin);
        List resultList = nquery.getResultList();
        return resultList;
    }
    
    public List list(Date dateBegin, Date dateEnd) {
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(actualDynReport.getSqlText());//"EdvManResults"
        nquery.setParameter(DATE_BEGIN, dateBegin);
        nquery.setParameter(DATE_END, dateEnd);
        List resultList = nquery.getResultList();
        return resultList;
    }
    
    public List list(Date dateBegin, Date dateEnd, Raion r) {
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(actualDynReport.getSqlText());//"EdvManResults"
        nquery.setParameter(DATE_BEGIN, dateBegin);
        nquery.setParameter(DATE_END, dateEnd);
        nquery.setParameter(RAION, r.getNomer());
        List resultList = nquery.getResultList();
        return resultList;
    }
    
    public List list(Raion r) {
        EntityManager em = getEntityManager();
        Query nquery = em.createNativeQuery(actualDynReport.getSqlText());//"EdvManResults"
        nquery.setParameter(RAION, r.getNomer());
        List resultList = nquery.getResultList();
        return resultList;
    }
    
}
