/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.main.job;

import org.quartz.*;
import ru.p03.makpsb.core.report.AbstractReport;
import ru.p03.makpsb.core.util.ResourceUtil;
import ru.p03.vmvp.utils.CommonUtils;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author timofeevan
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class JobAbstractReport extends AbstractJob implements Serializable{

    private static final String REPORT_KEY_NAME = "report";
    private static final String ENV_REPORT_DIRECTORY = "java:/comp/env/mpcore/jobReportDirectory";
    
    public static final String NUM_EXECUTIONS = "NumExecutions";
    public static final String EXECUTION_DELAY = "ExecutionDelay";
    
    private static final int MAX_REFIRE = 10;
    
    private boolean useOverrideDirectory = true;
    
    public JobAbstractReport(){
        super();
    }
    
    @Override
    protected void produce(JobExecutionContext jec) throws JobExecutionException {
        try{
            AbstractReport report = null;
            Object oreport = jec.getJobDetail().getJobDataMap().get(REPORT_KEY_NAME);
            if (oreport != null){
                report = (AbstractReport)oreport;
            }else{
                logTime("Невозможно выполнить регламентное задание JobAbstractReport без установленного отчета");
                throw new UnsupportedOperationException("Невозможно выполнить регламентное задание JobAbstractReport без установленного отчета");
            }
            if (report != null){
                if (useOverrideDirectory == true){
                    setOverrideDirectory(report);
                }
                boolean success = report.make();        
                if (success == true){
                    success = report.makeReportFile();
                    if (success == true){
                        logTime("Файл отчета" + report.getClass() + " успешно сформирован. Имя " + report.reportFileName());
                    }
                }else{
                    logTime("При выполнении отчета " + report.getClass() + " возникла ошибка на этапе выборки данных");
                }
            }else{
                logTime("Невозможно выполнить регламентное задание JobAbstractReport без отчета типа extends AbstractReport");
                throw new UnsupportedOperationException("Невозможно выполнить регламентное задание JobAbstractReport без отчета типа extends AbstractReport");
            }
        }catch (Exception ex){
            if (ex instanceof UnsupportedOperationException){
                throw ex;
            }else{
                onException(jec, ex);
            }
        }
    }   
    
    private void onException(JobExecutionContext jec, Exception ex) throws JobExecutionException{
        int executeCount = executeCount(jec);
        logTime("Произошла ошибка, задание будет перустановлено в очередь");
        if (executeCount <= MAX_REFIRE){
            setExecuteCount(jec, ++executeCount);
            long executionDelay = executionDelay(jec);
            setExecutionDelay(jec, executionDelay);
            JobExecutionException e2 = new JobExecutionException(ex);
            e2.refireImmediately();
            throw e2;
        }else{
            logTime("Регламентное задание превысило число возможных перезапусков при ошибке");
        }
    }
    
    private void setOverrideDirectory(AbstractReport report){
        Object environment = ResourceUtil.inctance().getEnvironment(ENV_REPORT_DIRECTORY);
        if (environment != null){
            String env = environment.toString();
            String sname = report.getClass().getSimpleName();
            Integer year = CommonUtils.year(CommonUtils.now());
            Integer month = CommonUtils.monthOfYear(CommonUtils.now());
            Integer day = CommonUtils.dayOfMonth(CommonUtils.now());
            String path = env + File.separator + sname + File.separator + year.toString() + File.separator + month.toString() + File.separator + day.toString();
            report.setReportPath(path, report.getSimpleFileName());
        }
    }
    
    private int executeCount(JobExecutionContext jec){
        JobDataMap map = jec.getJobDetail().getJobDataMap();

        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }
        return executeCount;
    }
    
    private long executionDelay(JobExecutionContext jec){
        JobDataMap map = jec.getJobDetail().getJobDataMap();

        long delay = 100_000;
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }
        return delay;
    }

    private void setExecuteCount(JobExecutionContext jec, int executeCount){
        JobDataMap map = jec.getJobDetail().getJobDataMap();
        map.put(NUM_EXECUTIONS, executeCount);
    }
    private void setExecutionDelay(JobExecutionContext jec, long delay){
        JobDataMap map = jec.getJobDetail().getJobDataMap();
        map.put(EXECUTION_DELAY, delay);
    }
}
