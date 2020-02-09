/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.increm.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "MIN_DETECTED_SIGNATURE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MinDetectedSignature.findAll", query = "SELECT m FROM MinDetectedSignature m")})
public class MinDetectedSignature implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    private Long id;
    @Basic(optional = false)
    @Column(name = "ID_SIGNATURE")
    private Long idSignature;
    @Basic(optional = false)
    @Column(name = "ID_RAION")
    private Long idRaion;
    @Basic(optional = false)
    @Column(name = "TIME_DETECTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeDetected;
    @Basic(optional = false)
    @Column(name = "TIME_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreate;
    @Column(name = "FAM")
    private String fam;
    @Column(name = "IM")
    private String im;
    @Column(name = "OTC")
    private String otc;
    @Column(name = "SNILS")
    private String snils;
    @Column(name = "DATE_BORN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBorn;
    @Lob
    @Column(name = "ADD_MESSAGE")
    private String addMessage;

    public MinDetectedSignature() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSignature() {
        return idSignature;
    }

    public void setIdSignature(long idSignature) {
        this.idSignature = idSignature;
    }

    public long getIdRaion() {
        return idRaion;
    }

    public void setIdRaion(long idRaion) {
        this.idRaion = idRaion;
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

}
