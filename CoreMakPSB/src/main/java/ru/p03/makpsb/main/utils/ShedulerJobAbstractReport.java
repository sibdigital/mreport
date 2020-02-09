/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.reflect.FieldUtils;
//import javax.servlet.http.HttpServletRequest;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.report.AbstractRaionPeriodReport;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.util.RaionUtil;
import ru.p03.makpsb.core.util.SchedulerUtils;
import ru.p03.makpsb.core.util.Translit;
import ru.p03.makpsb.main.job.JobAbstractReport;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.ConversionUtils;
import ru.p03.vmvp.utils.FormatUtils;

/**
 *
 * @author timofeevan
 */
public class ShedulerJobAbstractReport {
    
    private static final String TEST_TEST_DIR = "C:\\makpsb_report";
    
    protected String tmpDir;
    protected String resultFileName;
    protected String testDir = TEST_TEST_DIR;
    
    protected Date scheduledDate;
    protected String hour;
    protected String min;
    protected Boolean allRaion;
      
    protected Integer addedMinutes = 0;
    protected Integer addedHours = 0;
    
    protected Class jobClass;
    
    protected String jobName;
    protected String jobGroup;
    protected String triggerName;
    protected String triggerGroup;
    
    protected JobAbstractReport jobAbstractReport;
    protected AbstractReport report;
    
    public void fillJobTriggerInfo(AbstractReport report, JobAbstractReport jobAbstractReport, String jobGroup, String triggerName, String triggerGroup){
        setJobName(report.getClass().getName());
        setJobGroup(jobGroup);
        setTriggerName(report.getClass().getName());//triggerName);
        setTriggerGroup(triggerGroup);
        setJobClass(jobAbstractReport.getClass());
        this.report = report;
    }
    
    public void fillSchedulerParameters(Date scheduledDate, String hour, String min, Boolean allRaion){
        setScheduledDate(scheduledDate);
        setHour(hour);
        setMin(min);
        setAllRaion(allRaion);
    }
    
    public void fillSchedulerParameters(Map<String, Object> params){
        Date reqScheduledDate = (Date)params.get("scheduled_date");
        String reqHour = (String)params.get("hour");
        String reqMin = (String)params.get("min"); 
        boolean reqAllRaion = toBoolean(Objects.toString(params.get("all_raion")));
        fillSchedulerParameters(reqScheduledDate, reqHour, reqMin, reqAllRaion);
    }
            
    protected synchronized boolean sheduleJob(Date scheduledDate, String hour, String min, Map<String, Object> params){
        boolean result = true;

        try {
            String millis = String.valueOf(System.currentTimeMillis());
            //Thread.sleep(1L);
            JobDetail jobDetail = SchedulerUtils.getInstance().createJobDetail(getJobClass(), 
                    getJobName() + "_" + millis, getJobGroup(), params);
            Thread.sleep(1L);
            CronTrigger cronTrigger = SchedulerUtils.getInstance().createCronTrigger(scheduledDate, hour, min, 
                    getTriggerName() + "_" + millis, getTriggerGroup());             
        
            SchedulerUtils.getInstance().schedule(cronTrigger, jobDetail);
        } catch (SchedulerException | InterruptedException ex) {
            Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }
    
    public boolean isCorrectFill(){
        return getScheduledDate() != null && getHour() != null && getMin() != null 
                && getHour().isEmpty() == false && getMin().isEmpty() == false;
    }
    
    private void printLog(String message){
//        Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, 
//                "{0}: {1} job = {2} dateBegin = {3} dateEnd = {4} raion = {5}\n resultFileName = {6}; {7}", 
//                new Object[]{raion, FormatUtils.formatAsDDMMYYY_HHMMSS(CommonUtils.now()), 
//                this.getClass(), FormatUtils.formatAsDDMMYYY(dateBegin), FormatUtils.formatAsDDMMYYY(dateEnd), 
//                raion, resultFileName, message});
    }
        
    public boolean createJob(){

        boolean result = true;
 
        Map<String, Object> params = new HashMap<>();
        params.put("report", report);
        
        if (allRaion == false){   
            printLog("begin add job");
            result &= sheduleJob(scheduledDate, hour, min, params);
            printLog("end add job");
        }else{
            result &= sheduleAllRaions();
        }
        return result;
    }
    
    private int minutes(Raion r){
        int result = 2;
        if (r.getNomer() <= 10){ //наугад
            result = 1;
        }else if (r.getNomer() <= 20){
            result = 1;
        }else if (r.getNomer() == 22){
            result = 2;
        }else{
            result = 2;
        }
        return result;
    }
    
    private AbstractReport setRaion(AbstractReport report, Raion r){
        if (report instanceof AbstractRaionPeriodReport){
            AbstractRaionPeriodReport arpr = (AbstractRaionPeriodReport)report;
            arpr.setRaion(r);
        }else{
            setRaion(report, r, "raion");
            setRaion(report, r, "numRaion");
            setRaion(report, r, "ranum"); 
            setRaion(report, r, "ra"); 
            setRaion(report, r, "raNum");
        }
        return report;
    }
    
    private AbstractReport setRaion(AbstractReport report, Raion r, String fieldName){
        try{
            Field field = FieldUtils.getField(report.getClass(), fieldName, true);
            if (field != null){
                if (Objects.equals(field.getType(), Raion.class)){
                    FieldUtils.writeField(field, report, r, true);
                }else if (Objects.equals(field.getType(), String.class)){
                    FieldUtils.writeField(field, report, r.getNomer().toString(), true);
                } else if (Objects.equals(field.getType(), Integer.class)){
                    FieldUtils.writeField(field, report, r.getNomer(), true);
                }
            }
        }catch (Exception ex){
            Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }
    
    private boolean sheduleAllRaions(){
        boolean result = true;
        addedMinutes = ConversionUtils.toInteger(min);
        addedHours = ConversionUtils.toInteger(hour);
        List<Raion> raions = RaionUtil.instance().raions();
        Collections.reverse(raions);
        try{
            for (Raion r : raions) {
                printLog("begin add job all raion");              
                AbstractReport clonedReport = org.apache.commons.lang3.SerializationUtils.clone(report);
                setRaion(clonedReport, r);
                String sfn = clonedReport.getSimpleFileName().replace(".xls", "");
                String rfilename = Translit.instance().translate(sfn + "_" + r.getName() + " район_" + FormatUtils.formatAsDDMMYYY_HHMMSS(CommonUtils.now()), true, true);
                clonedReport.setReportPath(clonedReport.getDirectory(), rfilename + ".xls");
                Map<String, Object> params = new HashMap<>();
                params.put("report", clonedReport);
                sheduleJob(scheduledDate, addedHours.toString(), addedMinutes.toString(), params);
                //currentTime.plusMinutes(addedMinutes);
                addedMinutes += minutes(r);
                if (addedMinutes >= 60){
                    addedHours++;
                    addedMinutes = 0;
                    if (addedHours > 23){
                        addedHours = 0;
                        scheduledDate = CommonUtils.addDay(scheduledDate, 1);
                    }
                }
                printLog("end add job all raion");
            }
        }catch (Exception ex){
            Logger.getLogger(ShedulerJobAbstractReport.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    private boolean toBoolean (String value){
        return MPConversion.instance().toBoolean(value);
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public String getTestDir() {
        return testDir;
    }

    public void setTestDir(String testDir) {
        this.testDir = testDir;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Boolean getAllRaion() {
        return allRaion;
    }

    public void setAllRaion(Boolean allRaion) {
        this.allRaion = allRaion;
    }

    public Integer getAddedMinutes() {
        return addedMinutes;
    }

    public void setAddedMinutes(Integer addedMinutes) {
        this.addedMinutes = addedMinutes;
    }

    public Integer getAddedHours() {
        return addedHours;
    }

    public void setAddedHours(Integer addedHours) {
        this.addedHours = addedHours;
    }

    public Class getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}
