/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "CLS_SIGNATURE", catalog = "", schema = "MP_INCREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsSignature.findAll", query = "SELECT c FROM ClsSignature c")})
public class ClsSignature implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSignature")
    private Collection<RegDetectedSignature> regDetectedSignatureCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    //@Size(max = 255)
    @Column(name = "CODE_DYNREPORT")
    private String codeDynreport;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Lob
    @Column(name = "NAME")
    private String name;
    //@Size(max = 255)
    @Column(name = "CODE")
    private String code;
     @Column(name = "DESCRIPTION")
    private String description;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSignature")
//    private Collection<ClsSignatureDepart> clsSignatureDepartCollection;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSignature")
//    private Collection<RegDetectedSignature> regDetectedSignatureCollection;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSignature")
//    private Collection<ClsDetectPeriod> clsDetectPeriodCollection;
    @JoinColumn(name = "ID_SIGNATURE_TYPE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsSignatureType idSignatureType;

    public ClsSignature() {
    }

    public ClsSignature(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeDynreport() {
        return codeDynreport;
    }

    public void setCodeDynreport(String codeDynreport) {
        this.codeDynreport = codeDynreport;
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
    
        /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


//    @XmlTransient
//    public Collection<ClsSignatureDepart> getClsSignatureDepartCollection() {
//        return clsSignatureDepartCollection;
//    }
//
//    public void setClsSignatureDepartCollection(Collection<ClsSignatureDepart> clsSignatureDepartCollection) {
//        this.clsSignatureDepartCollection = clsSignatureDepartCollection;
//    }
//
//    @XmlTransient
//    public Collection<RegDetectedSignature> getRegDetectedSignatureCollection() {
//        return regDetectedSignatureCollection;
//    }
//
//    public void setRegDetectedSignatureCollection(Collection<RegDetectedSignature> regDetectedSignatureCollection) {
//        this.regDetectedSignatureCollection = regDetectedSignatureCollection;
//    }
//
//    @XmlTransient
//    public Collection<ClsDetectPeriod> getClsDetectPeriodCollection() {
//        return clsDetectPeriodCollection;
//    }
//
//    public void setClsDetectPeriodCollection(Collection<ClsDetectPeriod> clsDetectPeriodCollection) {
//        this.clsDetectPeriodCollection = clsDetectPeriodCollection;
//    }

    public ClsSignatureType getIdSignatureType() {
        return idSignatureType;
    }

    public void setIdSignatureType(ClsSignatureType idSignatureType) {
        this.idSignatureType = idSignatureType;
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
        if (!(object instanceof ClsSignature)) {
            return false;
        }
        ClsSignature other = (ClsSignature) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.ClsSignature[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RegDetectedSignature> getRegDetectedSignatureCollection() {
        return regDetectedSignatureCollection;
    }

    public void setRegDetectedSignatureCollection(Collection<RegDetectedSignature> regDetectedSignatureCollection) {
        this.regDetectedSignatureCollection = regDetectedSignatureCollection;
    }

}
