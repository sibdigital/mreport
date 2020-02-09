/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.lang3.reflect.FieldUtils;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.entity.dict.controller.StatAvgJpaController;
import ru.p03.makpsb.core.entity.dict.controller.StatJpaController;
import ru.p03.makpsb.core.entity.service.Stat;
import ru.p03.makpsb.core.entity.service.StatAvg;
import ru.p03.makpsb.core.report.AbstractRaionPeriodReport;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.dynreport.RaionPeriodDynReport;
import ru.p03.makpsb.main.utils.ShedulerJobAbstractReport;
import ru.p03.vmvp.utils.CommonUtils;

/**
 *
 * @author timofeevan
 */
public class StatUtil {
    protected final String persistenceUnit = "CoreMakPSBPU";
    private static StatUtil INSTANCE = null;

    public static StatUtil instance(){
        if (INSTANCE == null){
            INSTANCE = new StatUtil();
        }
        return INSTANCE;
    }
    
    private StatUtil(){

    }
    
    private<T extends AbstractReport> void setResource(T  report, Stat s){
        if (report instanceof RaionPeriodDynReport){
            s.setResource(((RaionPeriodDynReport)report).getActualDynReport().getName());
        }else{
            s.setResource(report.getClass().getName());
        }
    }
    
//    private void setResource(RaionPeriodDynReport report, Stat s){
//        s.setResource(report.getActualDynReport().getName());
//    }
        
    public<T extends AbstractReport> Stat beginStat(T report){
        Stat s = new Stat();
        try {
            s.setTimeBegin(CommonUtils.now());
            //s.setResource(report.getClass().getName());
            setResource(report, s);
            setRaion(report, s);
            setDateBegin(report, s);
            setDateEnd(report, s);
        } catch (Exception ex) {
            Logger.getLogger(StatUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    private AbstractReport setRaion(AbstractReport report, Stat stat, String fieldName){
        try{
            Field field = FieldUtils.getField(report.getClass(), fieldName, true);
            if (field != null){
                if (Objects.equals(field.getType(), Raion.class)){
                    Raion readField = (Raion)FieldUtils.readField(field, report, true);
                    if (readField != null){
                        stat.setRaion(readField.getNomer());
                    }
                }else if (Objects.equals(field.getType(), String.class)){
                    String readField = (String)FieldUtils.readField(field, report, true);
                    Raion byNomer = RaionUtil.instance().byNomer(readField);
                    if (byNomer != null){
                        stat.setRaion(byNomer.getNomer());
                    }
                } else if (Objects.equals(field.getType(), Integer.class)){
                    Integer readField = (Integer)FieldUtils.readField(field, report, true);
                    stat.setRaion(readField);
                }
            }
        }catch (Exception ex){
            Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }
    
    private AbstractReport setRaion(AbstractReport report, Stat stat){
        if (report instanceof AbstractRaionPeriodReport){
            AbstractRaionPeriodReport arpr = (AbstractRaionPeriodReport)report;
            if (arpr.getRaion() != null){
                stat.setRaion(arpr.getRaion().getNomer());
            }else{ //например динамические отчеты имеют незаполенный район
                stat.setRaion(0);
            }
        }else{
            setRaion(report, stat, "raion");
            setRaion(report, stat, "numRaion");
            setRaion(report, stat, "ranum"); 
            setRaion(report, stat, "ra"); 
            setRaion(report, stat, "raNum");
        }
        return report;
    }
    
    private AbstractReport setDateBegin(AbstractReport report, Stat stat, String fieldName){
        try{
            Field field = FieldUtils.getField(report.getClass(), fieldName, true);
            if (field != null){
                if (Objects.equals(field.getType(), Date.class)){
                    Date readField = (Date)FieldUtils.readField(field, report, true);
                    if (readField != null){
                        stat.setDateBegin(readField);
                    }
                }else if (Objects.equals(field.getType(), Calendar.class) || Objects.equals(field.getType(), GregorianCalendar.class)){
                    Calendar readField = (Calendar)FieldUtils.readField(field, report, true);
                    if (readField != null){
                        stat.setDateBegin(readField.getTime());
                    }
                } else if (Objects.equals(field.getType(), Integer.class)){
                    Integer readField = (Integer)FieldUtils.readField(field, report, true);
                    stat.setRaion(readField);
                }
            }
        }catch (Exception ex){
            Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }
    
    private AbstractReport setDateBegin(AbstractReport report, Stat stat){
        if (report instanceof AbstractRaionPeriodReport){
            AbstractRaionPeriodReport arpr = (AbstractRaionPeriodReport)report;
            stat.setDateBegin(arpr.getDateBegin());
        }else{
            setDateBegin(report, stat, "dateBegin");
            setDateBegin(report, stat, "datebegin");
            setDateBegin(report, stat, "dateBeg");
            setDateBegin(report, stat, "datebeg");
            setDateBegin(report, stat, "beginDate"); 
            setDateBegin(report, stat, "begindate"); 
        }
        return report;
    }
    
    private AbstractReport setDateEnd(AbstractReport report, Stat stat, String fieldName){
        try{
            Field field = FieldUtils.getField(report.getClass(), fieldName, true);
            if (field != null){
                if (Objects.equals(field.getType(), Date.class)){
                    Date readField = (Date)FieldUtils.readField(field, report, true);
                    if (readField != null){
                        stat.setDateEnd(readField);
                    }
                }else if (Objects.equals(field.getType(), Calendar.class) || Objects.equals(field.getType(), GregorianCalendar.class)){
                    Calendar readField = (Calendar)FieldUtils.readField(field, report, true);
                    if (readField != null){
                        stat.setDateEnd(readField.getTime());
                    }
                } else if (Objects.equals(field.getType(), Integer.class)){
                    Integer readField = (Integer)FieldUtils.readField(field, report, true);
                    stat.setRaion(readField);
                }
            }
        }catch (Exception ex){
            Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }
    
    private AbstractReport setDateEnd(AbstractReport report, Stat stat){
        if (report instanceof AbstractRaionPeriodReport){
            AbstractRaionPeriodReport arpr = (AbstractRaionPeriodReport)report;
            stat.setDateEnd(arpr.getDateEnd());
        }else{
            setDateEnd(report, stat, "dateEnd");
            setDateEnd(report, stat, "dateend");
            setDateEnd(report, stat, "endDate"); 
            setDateEnd(report, stat, "enddate"); 
        }
        return report;
    }
    
    public Stat endStat(Stat s){
        if (s != null){
            try {
                s.setTimeEnd(CommonUtils.now());
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
                StatJpaController sjc = new StatJpaController(emf);            
                sjc.edit(s);
            } catch (Exception ex) {
                Logger.getLogger(StatUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }
    
    public List<StatAvg> statAvg(String resource, int month, int dayWeek, int hour, int period, int raion){
        List<StatAvg> list = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        String sql = ResourceUtil.inctance().resource_asString(StatAvg.class, "/ru/p03/makpsb/core/entity.sql");
        
        Query nquery = em.createNativeQuery(sql, StatAvg.class);
        nquery.setParameter(1, month);
        nquery.setParameter(2, dayWeek);
        nquery.setParameter(3, hour);
        nquery.setParameter(4, period);
        nquery.setParameter(5, raion);
        nquery.setParameter(6, resource);
        list = nquery.getResultList();
        return list;

    }
}
