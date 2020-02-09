/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.service;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "STAT_AVG", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatAvg.findAll", query = "SELECT s FROM StatAvg s")})
public class StatAvg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "RESOURCE")
    private String resource;
    @Column(name = "RES_MONTH")
    private Integer resMonth;
    @Column(name = "RES_DAYWEEK")
    private Integer resDayweek;
    @Column(name = "RES_HOUR")
    private Integer resHour;
    @Column(name = "PERIOD")
    private Integer period;
    @Column(name = "RAION")
    private Integer raion;
    @Column(name = "AVG_DURATION")
    private Integer avgDuration;
    @Id
    @Column(name = "ID")
    private Long id;

    public StatAvg() {
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getResMonth() {
        return resMonth;
    }

    public void setResMonth(Integer resMonth) {
        this.resMonth = resMonth;
    }

    public Integer getResDayweek() {
        return resDayweek;
    }

    public void setResDayweek(Integer resDayweek) {
        this.resDayweek = resDayweek;
    }

    public Integer getResHour() {
        return resHour;
    }

    public void setResHour(Integer resHour) {
        this.resHour = resHour;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getRaion() {
        return raion;
    }

    public void setRaion(Integer raion) {
        this.raion = raion;
    }

    public Integer getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(Integer avgDuration) {
        this.avgDuration = avgDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
