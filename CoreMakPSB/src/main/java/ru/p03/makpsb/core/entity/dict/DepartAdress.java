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
@Table(name = "DEPART_ADRESS", schema = "MAKPSB")
@XmlRootElement
public class DepartAdress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_RAION")
    private long idRaion;
    @Basic(optional = false)
    @Column(name = "ID_DEPART")
    private long idDepart;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Lob
    @Column(name = "ADRESS")
    private String adress;

    public DepartAdress() {
    }

    public DepartAdress(Long id) {
        this.id = id;
    }

    public DepartAdress(Long id, long idRaion, long idDepart) {
        this.id = id;
        this.idRaion = idRaion;
        this.idDepart = idDepart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdRaion() {
        return idRaion;
    }

    public void setIdRaion(long idRaion) {
        this.idRaion = idRaion;
    }

    public long getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(long idDepart) {
        this.idDepart = idDepart;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
        if (!(object instanceof DepartAdress)) {
            return false;
        }
        DepartAdress other = (DepartAdress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.dict.DepartAdress[ id=" + id + " ]";
    }

}
