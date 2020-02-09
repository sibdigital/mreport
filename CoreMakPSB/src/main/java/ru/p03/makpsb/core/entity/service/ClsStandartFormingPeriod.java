/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "CLS_STANDART_FORMING_PERIOD", catalog = "", schema = "MAKPSB")
@XmlRootElement
public class ClsStandartFormingPeriod implements Serializable {
    
    public static final String LAST_WEEK = "LAST_WEEK";
    public static final String CURRENT_WEEK = "CURRENT_WEEK";
    public static final String LAST_MONTH = "LAST_MONTH";
    public static final String CURRENT_MONTH = "CURRENT_MONTH";  

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CODE")
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStandartFormingPeriod")
    private Collection<ClsReportPeriod> clsReportPeriodCollection;

    public ClsStandartFormingPeriod() {
    }

    public ClsStandartFormingPeriod(Long id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClsStandartFormingPeriod)) {
            return false;
        }
        ClsStandartFormingPeriod other = (ClsStandartFormingPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.service.ClsStandartFormingPeriod[ id=" + id + " ]";
    }

}
