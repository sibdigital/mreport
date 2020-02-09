/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import ru.p03.makpsb.core.entity.service.StatAvg;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.ConversionUtils;

/**
 *
 * @author timofeevan
 */
public class SchedulerUtils {
    
    protected final String persistenceUnit = "CoreMakPSBPU";
    
    private SchedulerUtils(){
        
    }
    
    public static SchedulerUtils getInstance(){
        return new SchedulerUtils();
    }
    
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jk = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jk)){
            scheduler.deleteJob(jk);
        }
    }
    
    public Date schedule(SchedulerFactory schedulerFactory, Trigger trigger, JobDetail jobDetail) throws SchedulerException{
        Scheduler scheduler = schedulerFactory.getScheduler();
        Date scheduleJob = scheduler.scheduleJob(jobDetail, trigger); 
        scheduler.start();
        return scheduleJob;
    }
    
    public Date schedule(Trigger trigger, JobDetail jobDetail) throws SchedulerException{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedule(schedulerFactory, trigger, jobDetail);
    }
    
    public JobDetail createJobDetail(Class clazz, String jobName, String jobGroup,  Map<String, Object> params){
        JobBuilder builder = JobBuilder.newJob(clazz);
        builder.withIdentity(jobName, jobGroup);         
        JobDataMap dataMap = new JobDataMap(params);
        builder.usingJobData(dataMap);
        builder.requestRecovery(true);
        builder.storeDurably(true);
        JobDetail jobDetail = builder.build();
        return jobDetail;
    }
    
    public CronTrigger createCronTrigger(Date date, String hour, String minute, String jobName, String jobGroup){       
         //cron format: http://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
            //http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06
            // sec min hour dayofmonth month dayofweek year
            //0 15 10 ? * MON-FRI 	Fire at 10:15 AM every Monday, Tuesday, Wednesday, Thursday and Friday
            //0 0/5 * * * ?
            //All misfired executions are immediately executed, then the trigger runs back on schedule.
            //Example scenario: the executions scheduled at 9 and 10 AM are executed immediately. The next scheduled execution (at 11 AM) runs on time.
        CronExpression ce = SchedulerUtils.getInstance().toCronExpression(date, hour, minute);           
        //CronExpression ce = new CronExpression(cron);//"0 09 13 * * ?");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(ce)
                                .withMisfireHandlingInstructionIgnoreMisfires())
                .build();
        DateTime dt = new DateTime(date.getTime());
        DateTime withTime = dt.plusHours(ConversionUtils.toInteger(hour)).plusMinutes(ConversionUtils.toInteger(minute));
        if (withTime.isBeforeNow() == true){
            Logger.getLogger(SchedulerUtils.class.getName()).log(Level.WARNING, "date {0} before current date  - NO JOB", withTime);
        }
        return trigger;
    }
    
    
    public CronExpression toCronExpression(Date date, String hour, String minute){
        CronExpression ce = null;
        try {
            // sec min hour dayofmonth month dayofweek year
            String cronExpression = "0 ";
            Integer dayOfMonth = CommonUtils.dayOfMonth(date);
            Integer monthOfYear = CommonUtils.monthOfYear(date);
            Integer year = CommonUtils.year(date);
            cronExpression += (minute + " " + hour + " " + dayOfMonth + " " + monthOfYear + " ? " + year);  
            ce = new CronExpression(cronExpression);
        } catch (ParseException ex) {
            Logger.getLogger(SchedulerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ce;
    }
    
    private String cronDayFromNumberDay(String day){
        //Days-of-Week can be specified as values between 1 and 7 (1 = Sunday)
        String cronDay = day;
        
        if (day.equals("1")){
            cronDay = "2";
        }else if (day.equals("2")){
            cronDay = "3";
        }else if (day.equals("3")){
            cronDay = "4";
        }else if (day.equals("4")){
            cronDay = "5";
        }else if (day.equals("5")){
            cronDay = "6";
        }else if (day.equals("6")){
            cronDay = "7";
        }else if (day.equals("7")){
            cronDay = "1";
        }
        return cronDay;
    }
    
    public CronTrigger createCronTrigger(String dayOfMonth, String dayOfWeek, Integer hour, Integer minute, String jobName, String jobGroup){       
         //cron format: http://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
            //http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06
            // sec min hour dayofmonth month dayofweek year
            //0 15 10 ? * MON-FRI 	Fire at 10:15 AM every Monday, Tuesday, Wednesday, Thursday and Friday
            //0 0/5 * * * ?
            //All misfired executions are immediately executed, then the trigger runs back on schedule.
            //Example scenario: the executions scheduled at 9 and 10 AM are executed immediately. The next scheduled execution (at 11 AM) runs on time.
        CronExpression ce = SchedulerUtils.getInstance().toCronExpression(dayOfMonth, dayOfWeek, hour, minute);           
        //CronExpression ce = new CronExpression(cron);//"0 09 13 * * ?");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(ce)
                                .withMisfireHandlingInstructionIgnoreMisfires())
                .build();
        return trigger;
    }
    
    public CronExpression toCronExpression(String dayOfMonth, String dayOfWeek, String hour, String minute){
        CronExpression ce = null;
        try {
            // sec min hour dayofmonth month dayofweek year
            //// sec min hour dayofmonth month dayofweek year
            //0 1 23 1 * 1
            String _dayOfMonth = dayOfMonth == null ? "?" : dayOfMonth;
            String _dayOfWeek = dayOfWeek == null ? "?" : dayOfWeek;
            String cronExpression = "0 " + minute + " " + hour + " " + _dayOfMonth + " " + "*" + " " + _dayOfWeek + " *";  
            ce = new CronExpression(cronExpression);
        } catch (ParseException ex) {
            Logger.getLogger(SchedulerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ce;
    }
    
    public CronExpression toCronExpression(String dayOfMonth, String dayOfWeek, Integer hour, Integer minute){
        return toCronExpression(dayOfMonth == null ? null : String.valueOf(dayOfMonth), 
               dayOfWeek == null ? null :  cronDayFromNumberDay(dayOfWeek), 
               String.valueOf(hour), String.valueOf(minute));
    }
    
    public List<Map<String, Object>> triggersInfo(Class clazz){       
        return triggersInfo(clazz.getName() + "%");
    }
    
    public List<Map<String, Object>> triggersInfo(String triggerName){
        List<Object[]> list = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emf.createEntityManager();
        String sql = ResourceUtil.inctance().resource_asString(StatAvg.class, "/ru/p03/makpsb/core/entity/sql/get_triggers.sql");
        
        Query nquery = em.createNativeQuery(sql);
        String name = triggerName;
        nquery.setParameter(1, name);
        list = nquery.getResultList();
        for (Object[] objects : list) {
            if (objects.length > 3 && objects[3] != null){ 
                String cronExpr = (String)objects[3];
                String[] splited = cronExpr.split(" ");
                Map<String, Object> map = new HashMap<>();
                map.put("nextDate", cronToStr(splited));
                result.add(map);
            }
        }
        return result;
    }
    
    private String cronToStr(String[] splited){
        //          C М  Ч  Д  М ДН ГГГ
        //          0 1  2  3  4 5 6
        // example: 0 35 16 17 3 ? 2017
        String year = (splited.length > 6 ? splited[6] : "");
        String month = (splited.length > 4 ? splited[4] : "");
        String dayOfMonth = (splited.length > 3 ? splited[3] : "") + " ";
        String dayOfWeek = (splited.length > 5 ? splited[5] : "") + " ";
        String hour = (splited.length > 2 ? StringUtils.leftPad(splited[2], 2, "0") : "") ;
        String minute = (splited.length > 1 ? StringUtils.leftPad(splited[1], 2, "0") : "");
        
        year = "*".equals(year) || "?".equals(year) ? "ежегодно" : ("год: " + year);
        month = "*".equals(month) || "?".equals(month) ? "ежемесячно" : ( " месяц: " + StringUtils.leftPad(month, 2, "0"));
        dayOfMonth = "*".equals(dayOfMonth) || "?".equals(dayOfMonth) ? "каждый день" : dayOfMonth;
        
        String day = "";
        if ("?".equals(dayOfWeek.trim())){
            day = " день: " + dayOfMonth;
        }else if ("*".equals(dayOfWeek.trim())){
            day = " день: " + dayOfMonth;
        }else{
            day = " еженедельно, в дни недели: " + dayOfWeek;
        }
//        if (!"*".equals(dayOfWeek) && !"?".equals(dayOfWeek)){
//            day = " еженедельно, в дни недели: " + dayOfWeek;
//        }else {
//            day = " день: " + dayOfMonth;
//        }
                
        
        String result = year + ", " +  month + ", " + day + "; время: " + hour + ":" + minute;
        return result;
    }
    
//    private Properties getProperties(){
//        Properties prop = new Properties();
//        
//        prop.put("org.quartz.scheduler.instanceName", "MPSheduler");
//        prop.put("org.quartz.scheduler.instanceId", "MPShedulerId");
//        
//        prop.put("org.quartz.threadPool.threadCount", "5");
//        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
//        
//        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");//JobStoreTX JobStoreCMT
//        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
//        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
//        //prop.put("org.quartz.jobStore.DataSource", "MPCore");
//        
//        prop.put("org.quartz.jobStore.dataSource", "java:/comp/env/jdbc/MPCore");
//        prop.put("org.quartz.dataSource.MPCore.jndiURL", "java:/comp/env/jdbc/MPCore");
//        
//      
//        
//        //prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
//        //prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
////        org.quartz.jobStore.class	org.quartz.impl.jdbcjobstore.JobStoreTX (or JobStoreCMT)
////        org.quartz.jobStore.tablePrefix	QRTZ_ (дополнительный, настраиваемый)
////        org.quartz.jobStore.driverDelegateClass	org.quartz.impl.jdbcjobstore.StdJDBCDelegate
////        org.quartz.jobStore.dataSource	qzDS (настраиваемый)
////        org.quartz.dataSource.qzDS.driver	com.ibm.db2.jcc.DB2Driver (может быть любой другой драйвер базы данных)
////        org.quartz.dataSource.qzDS.url	jdbc:db2://localhost:50000/QZ_SMPL (настраиваемый)
////        org.quartz.dataSource.qzDS.user	db2inst1 (помещает userid в вашу базу данных)
////        org.quartz.dataSource.qzDS.password	pass4dbadmin (помещает ваш пароль для пользователя)
////        org.quartz.dataSource.qzDS.maxConnections	30
//        return prop;
//    }

}
