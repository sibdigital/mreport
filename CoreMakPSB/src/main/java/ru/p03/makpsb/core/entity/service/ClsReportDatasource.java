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
@Table(name = "CLS_REPORT_DATASOURCE", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsReportDatasource.findAll", query = "SELECT c FROM ClsReportDatasource c")})
public class ClsReportDatasource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @JoinColumn(name = "ID_DATASOURCE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsDatasource idDatasource;
    @JoinColumn(name = "ID_REPORT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ReportInfo idReport;

    public ClsReportDatasource() {
    }

    public ClsReportDatasource(Long id) {
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

    public ClsDatasource getIdDatasource() {
        return idDatasource;
    }

    public void setIdDatasource(ClsDatasource idDatasource) {
        this.idDatasource = idDatasource;
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
        if (!(object instanceof ClsReportDatasource)) {
            return false;
        }
        ClsReportDatasource other = (ClsReportDatasource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.service.ClsReportDatasource[ id=" + id + " ]";
    }

}
