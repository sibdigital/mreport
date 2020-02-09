/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.util.SchedulerUtils;
import ru.p03.makpsb.main.job.JobAbstractReport;

/**
 *
 * @author timofeevan
 */
public class JobIncremBuilder {
    private JobAbstractReport jobAbstractReport;
    private AbstractReport report;
    private String dayOfMonth;
    private String dayOfWeek;
    private Integer hour;
    private Integer minute;
    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private Class jobClass;
    private Map<String, Object> params = new HashMap<>();
    
    public boolean buildAndSchedule(){
        boolean result = true;
        
        try {
            Thread.sleep(1L);
            getParams().put("report", getReport());
            JobDetail jobDetail = SchedulerUtils.getInstance().createJobDetail(getJobClass(), 
                    getJobName(), getJobGroup(), getParams());
            Thread.sleep(1L);
            CronTrigger cronTrigger = SchedulerUtils.getInstance().createCronTrigger(getDayOfMonth(), 
                    getDayOfWeek(), getHour(), getMinute(), getJobName(), getJobGroup());             
        
            SchedulerUtils.getInstance().deleteJob(getJobName(), getJobGroup());
            SchedulerUtils.getInstance().schedule(cronTrigger, jobDetail);
        } catch (SchedulerException | InterruptedException ex) {
            Logger.getLogger(JobIncremBuilder.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    /**
     * @param jobAbstractReport the jobAbstractReport to set
     */
    public JobIncremBuilder setJobAbstractReport(JobAbstractReport jobAbstractReport) {
        this.jobAbstractReport = jobAbstractReport;
        return this;
    }

    /**
     * @param report the report to set
     */
    public JobIncremBuilder setReport(AbstractReport report) {
        this.report = report;
        return this;
    }

    /**
     * @param dayOfMonth the dayOfMonth to set
     */
    public JobIncremBuilder setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        return this;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public JobIncremBuilder setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    /**
     * @param hour the hour to set
     */
    public JobIncremBuilder setHour(Integer hour) {
        this.hour = hour;
        return this;
    }

    /**
     * @param minute the minute to set
     */
    public JobIncremBuilder setMinute(Integer minute) {
        this.minute = minute;
        return this;
    }

    /**
     * @param jobName the jobName to set
     */
    public JobIncremBuilder setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    /**
     * @param jobGroup the jobGroup to set
     */
    public JobIncremBuilder setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    /**
     * @param triggerName the triggerName to set
     */
    public JobIncremBuilder setTriggerName(String triggerName) {
        this.triggerName = triggerName;
        return this;
    }

    /**
     * @param triggerGroup the triggerGroup to set
     */
    public JobIncremBuilder setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
        return this;
    }

    /**
     * @param jobClass the jobClass to set
     */
    public JobIncremBuilder setJobClass(Class jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    /**
     * @return the jobAbstractReport
     */
    public JobAbstractReport getJobAbstractReport() {
        return jobAbstractReport;
    }

    /**
     * @return the report
     */
    public AbstractReport getReport() {
        return report;
    }

    /**
     * @return the dayOfMonth
     */
    public String getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * @return the dayOfWeek
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @return the hour
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * @return the minute
     */
    public Integer getMinute() {
        return minute;
    }

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @return the jobGroup
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @return the triggerName
     */
    public String getTriggerName() {
        return triggerName;
    }

    /**
     * @return the triggerGroup
     */
    public String getTriggerGroup() {
        return triggerGroup;
    }

    /**
     * @return the jobClass
     */
    public Class getJobClass() {
        return jobClass;
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        if (params == null){
            params = new HashMap<>();
        }
        return params;
    }

    /**
     * @param params the params to set
     */
    public JobIncremBuilder setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }
    
    
}
