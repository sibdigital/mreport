/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "REPORT_INFO", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReportInfo.findAll", query = "SELECT r FROM ReportInfo r")})
public class ReportInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Lob
    @Column(name = "VISIBLE_NAME")
    private String visibleName;
    @Column(name = "PATH")
    private String path;
    @Column(name = "CLASS_NAME")
    private String className;
    @Column(name = "ID_RESOURCE")
    private BigInteger idResource;
    @Column(name = "CODE")
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReport")
    private Collection<ClsReportPeriod> clsReportPeriodCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReport")
    private Collection<ClsReportDatasource> clsReportDatasourceCollection;

    public ReportInfo() {
    }

    public ReportInfo(Long id) {
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

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BigInteger getIdResource() {
        return idResource;
    }

    public void setIdResource(BigInteger idResource) {
        this.idResource = idResource;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public Collection<ClsReportPeriod> getClsReportPeriodCollection() {
        return clsReportPeriodCollection;
    }

    public void setClsReportPeriodCollection(Collection<ClsReportPeriod> clsReportPeriodCollection) {
        this.clsReportPeriodCollection = clsReportPeriodCollection;
    }

    @XmlTransient
    public Collection<ClsReportDatasource> getClsReportDatasourceCollection() {
        return clsReportDatasourceCollection;
    }

    public void setClsReportDatasourceCollection(Collection<ClsReportDatasource> clsReportDatasourceCollection) {
        this.clsReportDatasourceCollection = clsReportDatasourceCollection;
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
        if (!(object instanceof ReportInfo)) {
            return false;
        }
        ReportInfo other = (ReportInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.service.ReportInfo[ id=" + id + " ]";
    }

}
