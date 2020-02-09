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
public interface IRaionPeriod {

    /**
     * @return the dateBegin
     */
    Date getDateBegin();

    /**
     * @return the dateEnd
     */
    Date getDateEnd();

    /**
     * @return the raion
     */
    Raion getRaion();

    /**
     * @param dateBegin the dateBegin to set
     */
    void setDateBegin(Date dateBegin);

    /**
     * @param dateEnd the dateEnd to set
     */
    void setDateEnd(Date dateEnd);

    /**
     * @param raion the raion to set
     */
    void setRaion(Raion raion);
    
}
