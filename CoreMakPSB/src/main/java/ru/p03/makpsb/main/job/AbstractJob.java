/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.job;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.p03.vmvp.utils.CommonUtils;
import ru.p03.vmvp.utils.FormatUtils;

/**
 *
 * @author timofeevan
 */
public abstract class AbstractJob implements Job, Serializable{
    
    //protected Map<String, Object> params = new HashMap<>();
       
    public AbstractJob(){
        
    }

    protected void beforeExecute(JobExecutionContext jec){
        logTime("Начато выполнение регламентного задания");
        JobDetail jd = jec.getJobDetail();
        if (jd != null){
            log("Параметры:");
            JobDataMap jobDataMap = jd.getJobDataMap();
            for (Map.Entry<String, Object> entry : jobDataMap.entrySet()) { 
                log("       " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException{
        try{
            beforeExecute(jec); 
        }catch (UnsupportedOperationException uoe){
            
        }
        produce(jec);
        try{
            afterExecute(jec);
        }catch (UnsupportedOperationException uoe){
            
        }
    }
    
    protected abstract void produce(JobExecutionContext jec) throws JobExecutionException;
    
    protected void afterExecute(JobExecutionContext jec){
        logTime("Закончено выполнение регламентного задания");
    }
    
    public static JobDetail fill(JobDetail jobDetail, Map<String, Object> params){
        for (Map.Entry<String, Object> entry : params.entrySet()) { 
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }
        return jobDetail;
    }
    
    public static JobDetail fill(JobDetail jobDetail, Map.Entry<String, Object> ... params){
        for (Map.Entry<String, Object> entry : params) {
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }
        return jobDetail;
    }
    
    protected void logTime(String message){
        String msg = this.getClass().getName() + "      " + FormatUtils.formatAsDDMMYYY_HHMMSS(CommonUtils.now()) + " : " + message;
        log(this.getClass().getName(), msg);
    }
    
    protected void log(String loggerName, String message){
        Logger.getLogger(loggerName).log(Level.SEVERE, message);
    }
    
    protected void log(String message){
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, message);
    }
    
    
//    public Map<String, Object> getParameters(){
//        return params;
//    }

}
