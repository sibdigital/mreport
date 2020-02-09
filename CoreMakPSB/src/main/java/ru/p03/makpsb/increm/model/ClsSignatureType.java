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
import javax.persistence.Lob;
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
@Table(name = "CLS_SIGNATURE_TYPE", catalog = "", schema = "MP_INCREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsSignatureType.findAll", query = "SELECT c FROM ClsSignatureType c")})
public class ClsSignatureType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Lob
    @Column(name = "NAME")
    private String name;
    //@Size(max = 255)
    @Column(name = "CODE")
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSignatureType")
    private Collection<ClsSignatureDepart> clsSignatureDepartCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSignatureType")
    private Collection<ClsSignature> clsSignatureCollection;

    public ClsSignatureType() {
    }

    public ClsSignatureType(Long id) {
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
    public Collection<ClsSignatureDepart> getClsSignatureDepartCollection() {
        return clsSignatureDepartCollection;
    }

    public void setClsSignatureDepartCollection(Collection<ClsSignatureDepart> clsSignatureDepartCollection) {
        this.clsSignatureDepartCollection = clsSignatureDepartCollection;
    }

    @XmlTransient
    public Collection<ClsSignature> getClsSignatureCollection() {
        return clsSignatureCollection;
    }

    public void setClsSignatureCollection(Collection<ClsSignature> clsSignatureCollection) {
        this.clsSignatureCollection = clsSignatureCollection;
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
        if (!(object instanceof ClsSignatureType)) {
            return false;
        }
        ClsSignatureType other = (ClsSignatureType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.ClsSignatureType[ id=" + id + " ]";
    }

}
