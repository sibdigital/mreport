/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service;

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
@Table(name = "CLS_REPORT_PERIOD", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsReportPeriod.findAll", query = "SELECT c FROM ClsReportPeriod c")})
public class ClsReportPeriod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_PERIOD_TYPE")
    private long idPeriodType;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "DAY_OF_MONTH")
    private Integer dayOfMonth;
    @Column(name = "DAY_OF_WEEK")
    private Integer dayOfWeek;
    @Column(name = "HOUR")
    private Integer hour;
    @Column(name = "MINUTE")
    private Integer minute;
    @Column(name = "IS_ACTIVE")
    private Integer isActive;
    @JoinColumn(name = "ID_STANDART_FORMING_PERIOD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsStandartFormingPeriod idStandartFormingPeriod;
    @JoinColumn(name = "ID_REPORT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ReportInfo idReport;

    public ClsReportPeriod() {
    }

    public ClsReportPeriod(Long id) {
        this.id = id;
    }

    public ClsReportPeriod(Long id, long idPeriodType) {
        this.id = id;
        this.idPeriodType = idPeriodType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdPeriodType() {
        return idPeriodType;
    }

    public void setIdPeriodType(long idPeriodType) {
        this.idPeriodType = idPeriodType;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public ClsStandartFormingPeriod getIdStandartFormingPeriod() {
        return idStandartFormingPeriod;
    }

    public void setIdStandartFormingPeriod(ClsStandartFormingPeriod idStandartFormingPeriod) {
        this.idStandartFormingPeriod = idStandartFormingPeriod;
    }

    public ReportInfo getIdReport() {
        return idReport;
    }

    public void setIdReport(ReportInfo idReport) {
        this.idReport = idReport;
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
        if (!(object instanceof ClsReportPeriod)) {
            return false;
        }
        ClsReportPeriod other = (ClsReportPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.service.ClsReportPeriod[ id=" + id + " ]";
    }

}
