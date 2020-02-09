/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import ru.p03.makpsb.core.entity.dict.Raion;
import ru.p03.makpsb.core.util.RaionUtil;
import ru.p03.makpsb.core.util.SchedulerUtils;
import ru.p03.makpsb.core.util.Translit;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.ConversionUtils;
import ru.p03.vmvp.utils.FormatUtils;

/**
 *
 * @author timofeevan
 */
public class ShedulerRaionDateInterval {
    
    private static final String TEST_TEST_DIR = "C:\\makpsb_report";
    
    protected String tmpDir;
    protected String resultFileName;
    protected String testDir = TEST_TEST_DIR;
    
    protected Date scheduledDate;
    protected String hour;
    protected String min;
    protected Boolean allRaion;
    
    protected Date dateBegin;
    protected Date dateEnd;
    protected ru.p03.makpsb.core.entity.dict.Raion raion;
    protected Map<String, Object> reportParameters;
    
    protected Integer addedMinutes = 0;
    protected Integer addedHours = 0;
    
    protected Class jobClass;
    
    protected String jobName;
    protected String jobGroup;
    protected String triggerName;
    protected String triggerGroup;
    
    public void fillJobTriggerInfo(Class jobClass, String jobName, String jobGroup, String triggerName, String triggerGroup){
        setJobName(jobName);
        setJobGroup(jobGroup);
        setTriggerName(triggerName);
        setTriggerGroup(triggerGroup);
        setJobClass(jobClass);
    }
    
    public void fillSchedulerParameters(Date scheduledDate, String hour, String min, Boolean allRaion){
        setScheduledDate(scheduledDate);
        setHour(hour);
        setMin(min);
        setAllRaion(allRaion);
    }
    
    public void fillSchedulerParameters(HttpServletRequest request){
        Date reqScheduledDate = ConversionUtils.toDate(request.getParameter("scheduled_date"));
        String reqHour = request.getParameter("hour");
        String reqMin = request.getParameter("min"); 
        boolean reqAllRaion = WebConversionUtil.instance().toBoolean(request.getParameter("all_raion"));
        fillSchedulerParameters(reqScheduledDate, reqHour, reqMin, reqAllRaion);
    }
    
    public void fillReportParam(Date dateBegin, Date dateEnd, ru.p03.makpsb.core.entity.dict.Raion raion, 
            Map<String, Object> reportParameters){
        setDateBegin(dateBegin);
        setDateEnd(dateEnd);
        setRaion(raion);
        setReportParameters(reportParameters);
    }
    
    public void fillReportParam(HttpServletRequest request, Map<String, Object> reportParameters){
        Date reqDateBegin = ConversionUtils.toDate(request.getParameter("begin_date"));
        Date reqDateEnd = ConversionUtils.toDate(request.getParameter("end_date"));
        String reqRaion = request.getParameter("raion");
        Raion byNomer = RaionUtil.instance().byNomer(reqRaion);
        fillReportParam(reqDateBegin, reqDateEnd, byNomer, reportParameters);
    }
    
    
    protected void sheduleJob(Date scheduledDate, String hour, String min, Map<String, Object> params){
        Random r = new Random();
        Integer pp = r.nextInt(100);
        String p = pp.toString() + (params.get("raion") == null ? "N" : ("_r" + params.get("raion").toString() + "_"));      
        
        try {
            Thread.sleep(1L);
            JobDetail jobDetail = SchedulerUtils.getInstance().createJobDetail(getJobClass(), 
                    getJobName() + p + System.currentTimeMillis(), getJobGroup(), params);
            Thread.sleep(1L);
            CronTrigger cronTrigger = SchedulerUtils.getInstance().createCronTrigger(scheduledDate, hour, min, 
                    getTriggerName() + p + System.currentTimeMillis(), getTriggerGroup());             
        
            SchedulerUtils.getInstance().schedule(cronTrigger, jobDetail);
        } catch (SchedulerException | InterruptedException ex) {
            Logger.getLogger(ShedulerRaionDateInterval.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isCorrectFill(){
        return true;
    }
    
    private void printLog(String message){
        Logger.getLogger(ShedulerRaionDateInterval.class.getName()).log(Level.SEVERE, 
                "{0}: {1} job = {2} dateBegin = {3} dateEnd = {4} raion = {5}\n resultFileName = {6}; {7}", 
                new Object[]{raion, FormatUtils.formatAsDDMMYYY_HHMMSS(CommonUtils.now()), 
                this.getClass(), FormatUtils.formatAsDDMMYYY(dateBegin), FormatUtils.formatAsDDMMYYY(dateEnd), 
                raion, resultFileName, message});
    }
        
    public boolean createJob(){
        //String resultFileName = ""; 
        //String testDir = "C:\\test_shed_report"; // только для тестирования
        //String tmpdir = ((File)getServletContext().getAttribute("javax.servlet.context.tempdir")).getAbsolutePath();
        boolean result = true;
        if (CommonUtils.emptyOrNull(resultFileName) == true){
            resultFileName =  Main.getTempFileName(testDir + "\\", Translit.instance().translate(RaionUtil.instance().name(raion) + " район"),
                FormatUtils.formatAsDDMMYYY(dateBegin), FormatUtils.formatAsDDMMYYY(dateEnd)) + ".xls";
        }else{
            resultFileName =  Main.getTempFileName(testDir + "\\", resultFileName,
                    FormatUtils.formatAsDDMMYYY(dateBegin), FormatUtils.formatAsDDMMYYY(dateEnd)) + ".xls";
        }
               
        Map<String, Object> params = new HashMap<>();
        params.put("raion", raion.getNomer().toString());
        params.put("dateBegin", dateBegin);
        params.put("dateEnd", dateEnd);
        params.put("resultFileName", resultFileName);
        params.put("params", getReportParameters());
        
        if (allRaion == false){   
            printLog("begin add job");
            sheduleJob(scheduledDate, hour, min, params);
            printLog("end add job");
        }else{
            addedMinutes = ConversionUtils.toInteger(min);
            addedHours = ConversionUtils.toInteger(hour);
            List<ru.p03.makpsb.core.entity.dict.Raion> raions = RaionUtil.instance().raions();
            for (ru.p03.makpsb.core.entity.dict.Raion r : raions) {
                printLog("begin add job all raion");
                Map<String, Object> shedParams = new HashMap<>(params);
                shedParams.put("raion", r.getNomer().toString());       
                shedParams.put("resultFileName", Main.getTempFileName(testDir + "\\", 
                        Translit.instance().translate(RaionUtil.instance().name(r) + " район"),
                        FormatUtils.formatAsDDMMYYY(dateBegin), FormatUtils.formatAsDDMMYYY(dateEnd)) + ".xls");
                sheduleJob(scheduledDate, addedHours.toString(), addedMinutes.toString(), shedParams);
                //currentTime.plusMinutes(addedMinutes);
                if (r.getNomer() <= 10){ //наугад
                    addedMinutes += 1;
                }else if (r.getNomer() <= 21){
                    addedMinutes += 2;
                }else{
                    addedMinutes += 12;
                }
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
        }
        return result;
    }

    /**
     * @return the tmpDir
     */
    public String getTmpDir() {
        return tmpDir;
    }

    /**
     * @param tmpDir the tmpDir to set
     */
    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    /**
     * @return the testDir
     */
    public String getTestDir() {
        return testDir;
    }

    /**
     * @param testDir the testDir to set
     */
    public void setTestDir(String testDir) {
        this.testDir = testDir;
    }

    /**
     * @return the scheduledDate
     */
    public Date getScheduledDate() {
        return scheduledDate;
    }

    /**
     * @param scheduledDate the scheduledDate to set
     */
    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    /**
     * @return the hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * @return the min
     */
    public String getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(String min) {
        this.min = min;
    }

    /**
     * @return the allRaion
     */
    public Boolean getAllRaion() {
        return allRaion;
    }

    /**
     * @param allRaion the allRaion to set
     */
    public void setAllRaion(Boolean allRaion) {
        this.allRaion = allRaion;
    }

    /**
     * @return the dateBegin
     */
    public Date getDateBegin() {
        return dateBegin;
    }

    /**
     * @param dateBegin the dateBegin to set
     */
    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    /**
     * @return the dateEnd
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd the dateEnd to set
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * @return the raion
     */
    public ru.p03.makpsb.core.entity.dict.Raion getRaion() {
        return raion;
    }

    /**
     * @param raion the raion to set
     */
    public void setRaion(ru.p03.makpsb.core.entity.dict.Raion raion) {
        this.raion = raion;
    }

    /**
     * @return the reportParameters
     */
    public Map<String, Object> getReportParameters() {
        return reportParameters;
    }

    /**
     * @param reportParameters the reportParameters to set
     */
    public void setReportParameters(Map<String, Object> reportParameters) {
        this.reportParameters = reportParameters;
    }

    /**
     * @return the addedMinutes
     */
    public Integer getAddedMinutes() {
        return addedMinutes;
    }

    /**
     * @param addedMinutes the addedMinutes to set
     */
    public void setAddedMinutes(Integer addedMinutes) {
        this.addedMinutes = addedMinutes;
    }

    /**
     * @return the addedHours
     */
    public Integer getAddedHours() {
        return addedHours;
    }

    /**
     * @param addedHours the addedHours to set
     */
    public void setAddedHours(Integer addedHours) {
        this.addedHours = addedHours;
    }

    /**
     * @return the jobClass
     */
    public Class getJobClass() {
        return jobClass;
    }

    /**
     * @param jobClass the jobClass to set
     */
    public void setJobClass(Class jobClass) {
        this.jobClass = jobClass;
    }

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the jobGroup
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup the jobGroup to set
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * @return the triggerName
     */
    public String getTriggerName() {
        return triggerName;
    }

    /**
     * @param triggerName the triggerName to set
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    /**
     * @return the triggerGroup
     */
    public String getTriggerGroup() {
        return triggerGroup;
    }

    /**
     * @param triggerGroup the triggerGroup to set
     */
    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    /**
     * @return the resultFileName
     */
    public String getResultFileName() {
        return resultFileName;
    }

    /**
     * @param resultFileName the resultFileName to set
     */
    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}
