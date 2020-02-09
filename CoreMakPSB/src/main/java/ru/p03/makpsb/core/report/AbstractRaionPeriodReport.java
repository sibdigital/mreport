/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.core.report;

import java.util.Date;
import ru.p03.makpsb.core.entity.dict.Raion;

/**
 *
 * @author timofeevan
 */
public abstract class AbstractRaionPeriodReport extends AbstractReport implements IRaionPeriod{
    protected Date dateBegin;
    protected Date dateEnd;
    protected Raion raion;

    private static final long serialVersionUID = 5977352965595197032L;
    
    private static class DummyAbstractReport extends AbstractRaionPeriodReport{
        @Override
        protected boolean perform() {
            return false;
        }

        @Override
        protected boolean beforePerform(){
            return false;
        }

        @Override
        protected boolean afterPerform() {
            return false;
        }

        @Override
        protected boolean createReportFile() {
            return false;
        }       
    }
    
    public static AbstractRaionPeriodReport getDummyInstance(){
        return new DummyAbstractReport();
    }

    @Override
    public Date getDateBegin() {
        return dateBegin;
    }

    @Override
    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    @Override
    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public Raion getRaion() {
        return raion;
    }

    @Override
    public void setRaion(Raion raion) {
        this.raion = raion;
    }
}
