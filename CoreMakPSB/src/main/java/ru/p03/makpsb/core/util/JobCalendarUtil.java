/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ru.p03.makpsb.core.entity.base.Config;
import ru.p03.makpsb.core.entity.base.MPDAO;
import ru.p03.makpsb.core.entity.dict.JobCalendar;
import ru.p03.makpsb.core.entity.dict.controller.JobCalendarJpaController;

/**
 *
 * @author timofeevan
 */
public class JobCalendarUtil {
       
    protected final String persistenceUnit;
    
    protected List<JobCalendar> dates = new ArrayList<>();
    
    private EntityManagerFactory emf;
    
    private final Config config;

    private JobCalendarUtil() {
        this.config = null;
        persistenceUnit = MPDAO.CORE_MAK_PSB_PU;
    }
    
    private JobCalendarUtil(Config config) {
        this.config = config;
        Set<String> puns = config.getPersistenceUnitNames();
        
        String pun = MPDAO.CORE_MAK_PSB_PU;
        for (String p : puns) {
            try { // в качестве юнита возьмем юнит с тем именеем, на котром сможет выполниться выборка районов. костыль
                //EntityManagerFactory testEmf = config.getEntityManagerFactory(p);
                JobCalendarJpaController controller = new JobCalendarJpaController(getEmf(p));
                dates = controller.findJobCalendarEntities();
                pun = p;
                break;
            } catch (Exception ex) {

            }
        }
        persistenceUnit = pun;
    }
    
    private EntityManagerFactory getEmf() {
        return getEmf(persistenceUnit);
    }
    
    private EntityManagerFactory getEmf(String persistenceUnit) {
        if (emf == null) {
            if (config == null) {
                emf = Persistence.createEntityManagerFactory(persistenceUnit);
            } else {
                emf = createEntityManagerFactory(persistenceUnit);
            }
        }
        return emf;
    }
    
    private EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) {
        boolean configIsNull = config == null;
        EntityManagerFactory _emf;

        if (configIsNull || (config.containsEntityManagerFactoryKey(persistenceUnitName)
                && config.getEntityManagerFactory(persistenceUnitName) == null)) {
            _emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        } else {
            _emf = config.getEntityManagerFactory(persistenceUnitName);
        }

        return _emf;
    }
    
    public List<JobCalendar> jobDates(){
        if (dates == null || dates.isEmpty() == true){
            JobCalendarJpaController controller = new JobCalendarJpaController(getEmf());
            dates = controller.findJobCalendarEntities();
        } 
        return dates;
    }
    
    public List<Date> dates(){
        List<Date> njd = new ArrayList<>();
        for (JobCalendar jd : jobDates()) {
            njd.add(jd.getNotJobDate());
        }
        return njd;
    }
    
    public Set<Date> datesAsSet(){
        Set<Date> njd = new HashSet<>();
        for (JobCalendar jd : jobDates()) {
            njd.add(jd.getNotJobDate());
        }
        return njd;
    }
    
    
    private static JobCalendarUtil INSTANCE = null;
    
    public static JobCalendarUtil instance(){
        if (INSTANCE == null){
            INSTANCE = new JobCalendarUtil();
        }
        return INSTANCE;
    }
    
    public static JobCalendarUtil instance(Config config) {
        return new JobCalendarUtil(config);
    }
}
