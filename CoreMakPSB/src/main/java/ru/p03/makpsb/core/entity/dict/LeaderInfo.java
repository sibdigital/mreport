/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.dict;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@SqlResultSetMapping(name="LeaderInfoResults", 
    entities={ 
        @EntityResult(entityClass = ru.p03.makpsb.core.entity.dict.LeaderInfo.class, fields={
            @FieldResult(name="id", column="ID"),
            @FieldResult(name="idWorker", column="ID_WORKER"), 
            @FieldResult(name="idPosition", column="ID_POSITION"),
            @FieldResult(name="idDepart", column="ID_DEPART"), 
            @FieldResult(name="idRaion", column="ID_RAION"), 
            @FieldResult(name="dateBegin", column="DATE_BEGIN"),
            @FieldResult(name="dateEnd", column="DATE_END"), 
            @FieldResult(name="isLeader", column="IS_LEADER"),
            @FieldResult(name="fam", column="FAM"), 
            @FieldResult(name="im", column="IM"),
            @FieldResult(name="otc", column="OTC"), 
            @FieldResult(name="positionName", column="POSITION_NAME"),
            @FieldResult(name="departName", column="DEPART_NAME"), 
            @FieldResult(name="raionName", column="RAION_NAME"),
            @FieldResult(name="nomer", column="NOMER")
        })
    }
)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LeaderInfo implements Serializable {
    //ID, ID_WORKER, ID_POSITION, ID_DEPART, DATE_BEGIN, DATE_END, IS_LEADER
    //FAM, IM, OTC, POSITION_NAME, DEPART_NAME, RAION_NAME, NOMER
    protected static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "ID_WORKER")
    protected Long idWorker;

    @Column(name = "ID_POSITION")
    protected Long idPosition;

    @Column(name = "ID_DEPART")
    protected Long idDepart;
    
    @Column(name = "ID_RAION")
    protected Long idRaion;
    
    @Column(name = "DATE_BEGIN")
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateBegin;
    
    @Column(name = "DATE_END")
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateEnd;

    @Column(name = "IS_LEADER")
    protected Integer isLeader;

    @Column(name = "FAM")
    protected String fam;

    @Column(name = "IM")
    protected String im;

    @Column(name = "OTC")
    protected String otc;

    @Column(name = "POSITION_NAME")
    protected String positionName;
    
    @Column(name = "DEPART_NAME")
    protected String departName;
    
    @Column(name = "RAION_NAME")
    protected String raionName;
    
    @Column(name = "NOMER")
    protected Integer nomer;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idWorker
     */
    public Long getIdWorker() {
        return idWorker;
    }

    /**
     * @param idWorker the idWorker to set
     */
    public void setIdWorker(Long idWorker) {
        this.idWorker = idWorker;
    }

    /**
     * @return the idPosition
     */
    public Long getIdPosition() {
        return idPosition;
    }

    /**
     * @param idPosition the idPosition to set
     */
    public void setIdPosition(Long idPosition) {
        this.idPosition = idPosition;
    }

    /**
     * @return the idDepart
     */
    public Long getIdDepart() {
        return idDepart;
    }

    /**
     * @param idDepart the idDepart to set
     */
    public void setIdDepart(Long idDepart) {
        this.idDepart = idDepart;
    }

    /**
     * @return the dateBegin
     */
    public Date getDateBegin() {
        return dateBegin;
    }

    /**
     * @param dateBegin the dateBegin to set
     */
    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    /**
     * @return the dateEnd
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd the dateEnd to set
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * @return the isLeader
     */
    public Integer getIsLeader() {
        return isLeader;
    }

    /**
     * @param isLeader the isLeader to set
     */
    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    /**
     * @return the fam
     */
    public String getFam() {
        return fam;
    }

    /**
     * @param fam the fam to set
     */
    public void setFam(String fam) {
        this.fam = fam;
    }

    /**
     * @return the im
     */
    public String getIm() {
        return im;
    }

    /**
     * @param im the im to set
     */
    public void setIm(String im) {
        this.im = im;
    }

    /**
     * @return the otc
     */
    public String getOtc() {
        return otc;
    }

    /**
     * @param otc the otc to set
     */
    public void setOtc(String otc) {
        this.otc = otc;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the departName
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * @param departName the departName to set
     */
    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * @return the raionName
     */
    public String getRaionName() {
        return raionName;
    }

    /**
     * @param raionName the raionName to set
     */
    public void setRaionName(String raionName) {
        this.raionName = raionName;
    }

    /**
     * @return the nomer
     */
    public Integer getNomer() {
        return nomer;
    }

    /**
     * @param nomer the nomer to set
     */
    public void setNomer(Integer nomer) {
        this.nomer = nomer;
    }

    /**
     * @return the idRaion
     */
    public Long getIdRaion() {
        return idRaion;
    }

    /**
     * @param idRaion the idRaion to set
     */
    public void setIdRaion(Long idRaion) {
        this.idRaion = idRaion;
    }
}
