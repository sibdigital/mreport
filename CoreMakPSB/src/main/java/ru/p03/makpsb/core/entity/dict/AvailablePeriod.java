/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "AVAILABLE_PERIOD", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AvailablePeriod.findAll", query = "SELECT a FROM AvailablePeriod a")})
public class AvailablePeriod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_RESOURCE")
    private long idResource;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "DAY_OF_WEEK")
    private Integer dayOfWeek;
    @Column(name = "HOUR_BEGIN")
    private Integer hourBegin;
    @Column(name = "HOUR_END")
    private Integer hourEnd;
    @Column(name = "MINUTE_BEGIN")
    private Integer minuteBegin;
    @Column(name = "MINUTE_END")
    private Integer minuteEnd;

    public AvailablePeriod() {
    }

    public AvailablePeriod(Long id) {
        this.id = id;
    }

    public AvailablePeriod(Long id, long idResource) {
        this.id = id;
        this.idResource = idResource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdResource() {
        return idResource;
    }

    public void setIdResource(long idResource) {
        this.idResource = idResource;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getHourBegin() {
        return hourBegin;
    }

    public void setHourBegin(Integer hourBegin) {
        this.hourBegin = hourBegin;
    }

    public Integer getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(Integer hourEnd) {
        this.hourEnd = hourEnd;
    }

    public Integer getMinuteBegin() {
        return minuteBegin;
    }

    public void setMinuteBegin(Integer minuteBegin) {
        this.minuteBegin = minuteBegin;
    }

    public Integer getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(Integer minuteEnd) {
        this.minuteEnd = minuteEnd;
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
        if (!(object instanceof AvailablePeriod)) {
            return false;
        }
        AvailablePeriod other = (AvailablePeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.dict.AvailablePeriod[ id=" + id + " ]";
    }

}
