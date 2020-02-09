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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "REG_DETECTED_SIGNATURE_DTL_VALUES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegDetectedSignatureDtlValues.findAll", query = "SELECT r FROM RegDetectedSignatureDtlValues r")})
@Customizer(XMLDataCustomizerRegDetectedSignatureDtlValues.class)
public class RegDetectedSignatureDtlValues implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
//    @Lob
//    @Column(name = "DTL_DATA")
    private String dtlData;
//    @Lob
//    @Column(name = "DTL_XSLT")
    private String dtlXslt;
    @JoinColumn(name = "ID_REG_DETECTED_SIGNATURE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private RegDetectedSignature idRegDetectedSignature;

    public RegDetectedSignatureDtlValues() {
    }

    public RegDetectedSignatureDtlValues(Long id) {
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

    public String getDtlData() {
        return dtlData;
    }

    public void setDtlData(String dtlData) {
        this.dtlData = dtlData;
    }

    public String getDtlXslt() {
        return dtlXslt;
    }

    public void setDtlXslt(String dtlXslt) {
        this.dtlXslt = dtlXslt;
    }

    public RegDetectedSignature getIdRegDetectedSignature() {
        return idRegDetectedSignature;
    }

    public void setIdRegDetectedSignature(RegDetectedSignature idRegDetectedSignature) {
        this.idRegDetectedSignature = idRegDetectedSignature;
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
        if (!(object instanceof RegDetectedSignatureDtlValues)) {
            return false;
        }
        RegDetectedSignatureDtlValues other = (RegDetectedSignatureDtlValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.RegDetectedSignatureDtlValues[ id=" + id + " ]";
    }

}
