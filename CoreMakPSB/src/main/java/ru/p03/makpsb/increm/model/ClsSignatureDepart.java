/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "CLS_SIGNATURE_DEPART", catalog = "", schema = "MP_INCREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsSignatureDepart.findAll", query = "SELECT c FROM ClsSignatureDepart c")})
public class ClsSignatureDepart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_DEPART")
    private BigInteger idDepart;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "LEVEL")
    private Integer level;
    @JoinColumn(name = "ID_SIGNATURE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsSignature idSignature;
    @JoinColumn(name = "ID_SIGNATURE_TYPE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsSignatureType idSignatureType;

    public ClsSignatureDepart() {
    }

    public ClsSignatureDepart(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(BigInteger idDepart) {
        this.idDepart = idDepart;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ClsSignature getIdSignature() {
        return idSignature;
    }

    public void setIdSignature(ClsSignature idSignature) {
        this.idSignature = idSignature;
    }

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
        if (!(object instanceof ClsSignatureDepart)) {
            return false;
        }
        ClsSignatureDepart other = (ClsSignatureDepart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.ClsSignatureDepart[ id=" + id + " ]";
    }

}
