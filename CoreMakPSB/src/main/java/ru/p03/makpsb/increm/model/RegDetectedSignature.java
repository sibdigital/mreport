/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement; 
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "REG_DETECTED_SIGNATURE", catalog = "", schema = "MP_INCREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegDetectedSignature.findAll", query = "SELECT r FROM RegDetectedSignature r")})
public class RegDetectedSignature implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRegDetectedSignature")
    private Collection<RegDetectedSignatureDtlValues> regDetectedSignatureDtlValuesCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "ID_RAION")
    private long idRaion;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "TIME_DETECTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeDetected;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "TIME_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreate;
    @Column(name = "TIME_FIX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFix;
    //@Size(max = 255)
    @Column(name = "FAM")
    private String fam;
    //@Size(max = 255)
    @Column(name = "IM")
    private String im;
    //@Size(max = 255)
    @Column(name = "OTC")
    private String otc;
    //@Size(max = 14)
    @Column(name = "SNILS")
    private String snils;
    @Column(name = "DATE_BORN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBorn;
    @Lob
    @Column(name = "ADD_MESSAGE")
    private String addMessage;
    @JoinColumn(name = "ID_SIGNATURE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClsSignature idSignature;
    
    public RegDetectedSignature() {
    }

    public RegDetectedSignature(Long id) {
        this.id = id;
    }
    
    public RegDetectedSignature(ClsSignature signature, Date timeDetected, Date timeCreate) {
        this.timeDetected = timeDetected;
        this.timeCreate = timeCreate; 
        this.idSignature = signature; 
        this.isDeleted = 0;
    }

    public RegDetectedSignature(Long id, long idRaion, Date timeDetected, Date timeCreate) {
        this.id = id;
        this.idRaion = idRaion;
        this.timeDetected = timeDetected;
        this.timeCreate = timeCreate; 
    }
    
    public RegDetectedSignature(Long id, long idRaion, Date timeDetected, Date timeCreate, String snils, String fam, String im, String otc) {
        this.id = id;
        this.idRaion = idRaion;
        this.timeDetected = timeDetected;
        this.timeCreate = timeCreate; 
        this.snils = snils;
        this.fam = fam;
        this.im = im;
        this.otc = otc;
        this.isDeleted = 0;
    }
    
    public RegDetectedSignature(Long id, long idRaion, Integer isDeleted, Date timeDetected, Date timeCreate, 
            String snils, String fam, String im, String otc, Date dateBorn, String addMessage, ClsSignature clsSignature) {
        this.id = id;
        this.idRaion = idRaion;
        this.timeDetected = timeDetected;
        this.timeCreate = timeCreate; 
        this.snils = snils;
        this.fam = fam;
        this.im = im;
        this.otc = otc;
        this.isDeleted = isDeleted;
        this.addMessage = addMessage;
        this.idSignature = clsSignature;
        this.dateBorn = dateBorn;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(Date timeDetected) {
        this.timeDetected = timeDetected;
    }

    public Date getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Date timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getOtc() {
        return otc;
    }

    public void setOtc(String otc) {
        this.otc = otc;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getAddMessage() {
        return addMessage;
    }

    public void setAddMessage(String addMessage) {
        this.addMessage = addMessage;
    }

    public ClsSignature getIdSignature() {
        return idSignature;
    }

    public void setIdSignature(ClsSignature idSignature) {
        this.idSignature = idSignature;
    }
    
        /**
     * @return the timeFix
     */
    public Date getTimeFix() {
        return timeFix;
    }

    /**
     * @param timeFix the timeFix to set
     */
    public void setTimeFix(Date timeFix) {
        this.timeFix = timeFix;
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
        if (!(object instanceof RegDetectedSignature)) {
            return false;
        }
        RegDetectedSignature other = (RegDetectedSignature) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.increm.model.RegDetectedSignature[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RegDetectedSignatureDtlValues> getRegDetectedSignatureDtlValuesCollection() {
        return regDetectedSignatureDtlValuesCollection;
    }

    public void setRegDetectedSignatureDtlValuesCollection(Collection<RegDetectedSignatureDtlValues> regDetectedSignatureDtlValuesCollection) {
        this.regDetectedSignatureDtlValuesCollection = regDetectedSignatureDtlValuesCollection;
    }

}
