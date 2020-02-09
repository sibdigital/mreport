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
@Table(name = "SIGNATURE_TIMING", catalog = "", schema = "MP_INCREM")
@XmlRootElement
public class SignatureTiming implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    private long id;
    @Basic(optional = false)
    @Column(name = "ID_SIGNATURE")
    private long idSignature;
    @Basic(optional = false)
    @Column(name = "ID_SIGNATURE_TYPE")
    private long idSignatureType;
    @Basic(optional = false)
    @Column(name = "ID_PERIOD_TYPE")
    private long idPeriodType;
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
    @Lob
    @Column(name = "PERIOD_TYPE_NAME")
    private String periodTypeName;
    @Lob
    @Column(name = "SIGNATURE_NAME")
    private String signatureName;
    @Lob
    @Column(name = "SIGNATURE_TYPE_NAME")
    private String signatureTypeName;
    
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SignatureTiming() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSignature() {
        return idSignature;
    }

    public void setIdSignature(long idSignature) {
        this.idSignature = idSignature;
    }

    public long getIdPeriodType() {
        return idPeriodType;
    }

    public void setIdPeriodType(long idPeriodType) {
        this.idPeriodType = idPeriodType;
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

    public String getPeriodTypeName() {
        return periodTypeName;
    }

    public void setPeriodTypeName(String periodTypeName) {
        this.periodTypeName = periodTypeName;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getSignatureTypeName() {
        return signatureTypeName;
    }

    public void setSignatureTypeName(String signatureTypeName) {
        this.signatureTypeName = signatureTypeName;
    }

    /**
     * @return the idSignatureType
     */
    public long getIdSignatureType() {
        return idSignatureType;
    }

    /**
     * @param idSignatureType the idSignatureType to set
     */
    public void setIdSignatureType(long idSignatureType) {
        this.idSignatureType = idSignatureType;
    }

}
