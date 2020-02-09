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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "CLS_SIGNATURE_HOLDER", catalog = "", schema = "MP_INCREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsSignatureHolder.findAll", query = "SELECT c FROM ClsSignatureHolder c")})
public class ClsSignatureHolder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "ID_SIGNATURE_DEPART")
    private long idSignatureDepart;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    //@Size(max = 255)
    @Column(name = "LOGIN")
    private String login;

    public ClsSignatureHolder() {
    }

    public ClsSignatureHolder(Long id) {
        this.id = id;
    }

    public ClsSignatureHolder(Long id, long idSignatureDepart) {
        this.id = id;
        this.idSignatureDepart = idSignatureDepart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdSignatureDepart() {
        return idSignatureDepart;
    }

    public void setIdSignatureDepart(long idSignatureDepart) {
        this.idSignatureDepart = idSignatureDepart;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        if (!(object instanceof ClsSignatureHolder)) {
            return false;
        }
        ClsSignatureHolder other = (ClsSignatureHolder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.ClsSignatureHolder[ id=" + id + " ]";
    }

}
