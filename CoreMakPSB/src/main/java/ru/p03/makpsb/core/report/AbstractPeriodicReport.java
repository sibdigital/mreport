/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.report;

import java.util.Date;
import org.joda.time.DateTime;
import ru.p03.makpsb.core.entity.service.ClsStandartFormingPeriod;

/**
 *
 * @author timofeevan
 */
public class AbstractPeriodicReport extends AbstractRaionPeriodReport{

    private static final long serialVersionUID = 1L;

    private AbstractRaionPeriodReport targetReport;
    
    private ClsStandartFormingPeriod standartFormingPeriod;
    
    @Override
    protected boolean perform() {   
        setDateBegin(getPeriodicalDateBegin());
        setDateEnd(getPeriodicalDateEnd());
        getTargetReport().setDateBegin(getDateBegin());
        getTargetReport().setDateEnd(getDateEnd());
        getTargetReport().setRaion(this.getRaion());
        return getTargetReport().perform();
    }

    @Override
    protected boolean beforePerform() throws Exception {
        return getTargetReport().beforePerform();
    }

    @Override
    protected boolean afterPerform() throws Exception {
       return getTargetReport().afterPerform();
    }

    @Override
    protected boolean createReportFile() {
        return getTargetReport().createReportFile();
    }
    
    private DateTime minimumDayValue(DateTime dateTime){
        return dateTime.hourOfDay().withMinimumValue()
                .minuteOfHour().withMinimumValue()
                .secondOfMinute().withMinimumValue()
                .millisOfSecond().withMinimumValue();
    }
    
    private DateTime maximumDayValue(DateTime dateTime){
        return dateTime.hourOfDay().withMaximumValue()
                .minuteOfHour().withMaximumValue()
                .secondOfMinute().withMaximumValue()
                .millisOfSecond().withMaximumValue();
    }
    
    private Date getPeriodicalDateBegin(){
        
        Date current = new Date();
        DateTime dateTime = new DateTime(current);
        long timestamp = 0L;
        String code = getStandartFormingPeriod().getCode();
        if (ClsStandartFormingPeriod.CURRENT_WEEK.equals(code)){            
            timestamp = minimumDayValue(dateTime)
                    .dayOfWeek().withMinimumValue().getMillis();

        }else if (ClsStandartFormingPeriod.CURRENT_MONTH.equals(code)){
            timestamp = minimumDayValue(dateTime)
                    .dayOfMonth().withMinimumValue().getMillis();

        }else if (ClsStandartFormingPeriod.LAST_WEEK.equals(code)){
            DateTime begin = minimumDayValue(dateTime)
                    .dayOfWeek().withMinimumValue();
            timestamp = minimumDayValue(begin.minusMillis(1))
                    .dayOfWeek().withMinimumValue().getMillis();
            
        }else if (ClsStandartFormingPeriod.LAST_MONTH.equals(code)){
            DateTime begin = minimumDayValue(dateTime)
                    .dayOfMonth().withMinimumValue();
            timestamp = minimumDayValue(begin.minusMillis(1))
                    .dayOfMonth().withMinimumValue().getMillis();
        }
        return new Date(timestamp);
    }
    
    private Date getPeriodicalDateEnd(){
        Date current = new Date();
        DateTime dateTime = new DateTime(current);
        long timestamp = 0L;
        String code = getStandartFormingPeriod().getCode();
        if (ClsStandartFormingPeriod.CURRENT_WEEK.equals(code)){
            timestamp = maximumDayValue(dateTime)
                    .dayOfWeek().withMaximumValue().getMillis();

        }else if (ClsStandartFormingPeriod.CURRENT_MONTH.equals(code)){
            timestamp = maximumDayValue(dateTime)
                    .dayOfMonth().withMaximumValue().getMillis();

        }else if (ClsStandartFormingPeriod.LAST_WEEK.equals(code)){
            dateTime = new DateTime(minimumDayValue(dateTime)
                    .dayOfWeek().withMinimumValue().getMillis());
            timestamp = dateTime.minusMillis(1).getMillis();
            
        }else if (ClsStandartFormingPeriod.LAST_MONTH.equals(code)){
            dateTime = new DateTime(minimumDayValue(dateTime)
                    .dayOfMonth().withMinimumValue().getMillis());
            timestamp = dateTime.minusMillis(1).getMillis();
        }
        return new Date(timestamp);
    }

    protected AbstractRaionPeriodReport getTargetReport() {
        return targetReport;
    }

    protected void setTargetReport(AbstractRaionPeriodReport targetReport) {
        this.targetReport = targetReport;
    }

    protected ClsStandartFormingPeriod getStandartFormingPeriod() {
        return standartFormingPeriod;
    }

    protected void setStandartFormingPeriod(ClsStandartFormingPeriod standartFormingPeriod) {
        this.standartFormingPeriod = standartFormingPeriod;
    }


}
