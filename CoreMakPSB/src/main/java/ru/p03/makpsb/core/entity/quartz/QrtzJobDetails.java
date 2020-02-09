/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.quartz;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "QRTZ_JOB_DETAILS", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QrtzJobDetails.findAll", query = "SELECT q FROM QrtzJobDetails q")})
public class QrtzJobDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QrtzJobDetailsPK qrtzJobDetailsPK;
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "JOB_CLASS_NAME")
    private String jobClassName;
    @Basic(optional = false)
    @Column(name = "IS_DURABLE")
    private int isDurable;
    @Basic(optional = false)
    @Column(name = "IS_NONCONCURRENT")
    private int isNonconcurrent;
    @Basic(optional = false)
    @Column(name = "IS_UPDATE_DATA")
    private int isUpdateData;
    @Basic(optional = false)
    @Column(name = "REQUESTS_RECOVERY")
    private int requestsRecovery;
//    @Lob
//    @Column(name = "JOB_DATA")
//    private Serializable jobData;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qrtzJobDetails")
    private Collection<QrtzTriggers> qrtzTriggersCollection;

    public QrtzJobDetails() {
    }

    public QrtzJobDetails(QrtzJobDetailsPK qrtzJobDetailsPK) {
        this.qrtzJobDetailsPK = qrtzJobDetailsPK;
    }

    public QrtzJobDetails(QrtzJobDetailsPK qrtzJobDetailsPK, String jobClassName, int isDurable, int isNonconcurrent, int isUpdateData, int requestsRecovery) {
        this.qrtzJobDetailsPK = qrtzJobDetailsPK;
        this.jobClassName = jobClassName;
        this.isDurable = isDurable;
        this.isNonconcurrent = isNonconcurrent;
        this.isUpdateData = isUpdateData;
        this.requestsRecovery = requestsRecovery;
    }

    public QrtzJobDetails(String schedName, String jobName, String jobGroup) {
        this.qrtzJobDetailsPK = new QrtzJobDetailsPK(schedName, jobName, jobGroup);
    }

    public QrtzJobDetailsPK getQrtzJobDetailsPK() {
        return qrtzJobDetailsPK;
    }

    public void setQrtzJobDetailsPK(QrtzJobDetailsPK qrtzJobDetailsPK) {
        this.qrtzJobDetailsPK = qrtzJobDetailsPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public int getIsDurable() {
        return isDurable;
    }

    public void setIsDurable(int isDurable) {
        this.isDurable = isDurable;
    }

    public int getIsNonconcurrent() {
        return isNonconcurrent;
    }

    public void setIsNonconcurrent(int isNonconcurrent) {
        this.isNonconcurrent = isNonconcurrent;
    }

    public int getIsUpdateData() {
        return isUpdateData;
    }

    public void setIsUpdateData(int isUpdateData) {
        this.isUpdateData = isUpdateData;
    }

    public int getRequestsRecovery() {
        return requestsRecovery;
    }

    public void setRequestsRecovery(int requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }

//    public Serializable getJobData() {
//        return jobData;
//    }
//
//    public void setJobData(Serializable jobData) {
//        this.jobData = jobData;
//    }

    @XmlTransient
    public Collection<QrtzTriggers> getQrtzTriggersCollection() {
        return qrtzTriggersCollection;
    }

    public void setQrtzTriggersCollection(Collection<QrtzTriggers> qrtzTriggersCollection) {
        this.qrtzTriggersCollection = qrtzTriggersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qrtzJobDetailsPK != null ? qrtzJobDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QrtzJobDetails)) {
            return false;
        }
        QrtzJobDetails other = (QrtzJobDetails) object;
        if ((this.qrtzJobDetailsPK == null && other.qrtzJobDetailsPK != null) || (this.qrtzJobDetailsPK != null && !this.qrtzJobDetailsPK.equals(other.qrtzJobDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.quartz.QrtzJobDetails[ qrtzJobDetailsPK=" + qrtzJobDetailsPK + " ]";
    }

}
