/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.entity.quartz;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timofeevan
 */
@Entity
@Table(name = "QRTZ_LOCKS", catalog = "", schema = "MAKPSB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QrtzLocks.findAll", query = "SELECT q FROM QrtzLocks q")})
public class QrtzLocks implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QrtzLocksPK qrtzLocksPK;

    public QrtzLocks() {
    }

    public QrtzLocks(QrtzLocksPK qrtzLocksPK) {
        this.qrtzLocksPK = qrtzLocksPK;
    }

    public QrtzLocks(String schedName, String lockName) {
        this.qrtzLocksPK = new QrtzLocksPK(schedName, lockName);
    }

    public QrtzLocksPK getQrtzLocksPK() {
        return qrtzLocksPK;
    }

    public void setQrtzLocksPK(QrtzLocksPK qrtzLocksPK) {
        this.qrtzLocksPK = qrtzLocksPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qrtzLocksPK != null ? qrtzLocksPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QrtzLocks)) {
            return false;
        }
        QrtzLocks other = (QrtzLocks) object;
        if ((this.qrtzLocksPK == null && other.qrtzLocksPK != null) || (this.qrtzLocksPK != null && !this.qrtzLocksPK.equals(other.qrtzLocksPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.makpsb.core.entity.quartz.QrtzLocks[ qrtzLocksPK=" + qrtzLocksPK + " ]";
    }

}
