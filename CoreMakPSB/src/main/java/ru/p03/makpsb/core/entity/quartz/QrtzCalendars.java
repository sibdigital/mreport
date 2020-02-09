/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.quartz;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "QRTZ_CALENDARS", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QrtzCalendars.findAll", query = "SELECT q FROM QrtzCalendars q")})
public class QrtzCalendars implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "SCHED_NAME")
    private String schedName;
    @Id
    @Basic(optional = false)
    @Column(name = "CALENDAR_NAME")
    private String calendarName;
    @Basic(optional = false)
    @Lob
    @Column(name = "CALENDAR")
    private Serializable calendar;

    public QrtzCalendars() {
    }

    public QrtzCalendars(String calendarName) {
        this.calendarName = calendarName;
    }

    public QrtzCalendars(String calendarName, String schedName, Serializable calendar) {
        this.calendarName = calendarName;
        this.schedName = schedName;
        this.calendar = calendar;
    }

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public Serializable getCalendar() {
        return calendar;
    }

    public void setCalendar(Serializable calendar) {
        this.calendar = calendar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calendarName != null ? calendarName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QrtzCalendars)) {
            return false;
        }
        QrtzCalendars other = (QrtzCalendars) object;
        if ((this.calendarName == null && other.calendarName != null) || (this.calendarName != null && !this.calendarName.equals(other.calendarName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.quartz.QrtzCalendars[ calendarName=" + calendarName + " ]";
    }

}
