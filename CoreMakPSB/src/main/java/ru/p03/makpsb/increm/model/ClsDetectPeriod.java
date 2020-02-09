/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "CLS_DETECT_PERIOD", catalog = "", schema = "MP_INCREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsDetectPeriod.findAll", query = "SELECT c FROM ClsDetectPeriod c")})
public class ClsDetectPeriod implements Serializable {

  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "IS_ACTIVE")
    private Integer isActive;
    @Column(name = "DAY_OF_MONTH")
    private Integer dayOfMonth;
    @Column(name = "DAY_OF_WEEK")
    private Integer dayOfWeek;
    @Column(name = "HOUR")
    private Integer hour;
    @Column(name = "MINUTE")
    private Integer minute;
    @JoinColumn(name = "ID_PERIOD_TYPE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsPeriodType idPeriodType;
    @JoinColumn(name = "ID_SIGNATURE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsSignature idSignature;

    public ClsDetectPeriod() {
    }

    public ClsDetectPeriod(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public ClsPeriodType getIdPeriodType() {
        return idPeriodType;
    }

    public void setIdPeriodType(ClsPeriodType idPeriodType) {
        this.idPeriodType = idPeriodType;
    }

    public ClsSignature getIdSignature() {
        return idSignature;
    }

    public void setIdSignature(ClsSignature idSignature) {
        this.idSignature = idSignature;
    }
    
    /**
     * @return the isActive
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsDetectPeriod)) {
            return false;
        }
        ClsDetectPeriod other = (ClsDetectPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.ClsDetectPeriod[ id=" + id + " ]";
    }

}
