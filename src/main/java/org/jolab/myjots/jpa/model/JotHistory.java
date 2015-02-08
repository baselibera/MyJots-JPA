/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jolab
 */
@Entity
@Table(name = "JotHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JotHistory.findAll", query = "SELECT j FROM JotHistory j"),
    @NamedQuery(name = "JotHistory.findByIdJotHistory", query = "SELECT j FROM JotHistory j WHERE j.jotHistoryPK.idJotHistory = :idJotHistory"),
    @NamedQuery(name = "JotHistory.findByIdJot", query = "SELECT j FROM JotHistory j WHERE j.jotHistoryPK.idJot = :idJot"),
    @NamedQuery(name = "JotHistory.findBySavedate", query = "SELECT j FROM JotHistory j WHERE j.savedate = :savedate")})
public class JotHistory implements Serializable {
    @Lob
    private byte[] object;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JotHistoryPK jotHistoryPK;
    @Basic(optional = false)
    @Column(name = "savedate")
    private String savedate;
    @JoinColumn(name = "idJot", referencedColumnName = "idJot", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jot jot;

    public JotHistory() {
    }

    public JotHistory(JotHistoryPK jotHistoryPK) {
        this.jotHistoryPK = jotHistoryPK;
    }

    public JotHistory(JotHistoryPK jotHistoryPK, String savedate) {
        this.jotHistoryPK = jotHistoryPK;
        this.savedate = savedate;
    }

    public JotHistory(int idJotHistory, int idJot) {
        this.jotHistoryPK = new JotHistoryPK(idJotHistory, idJot);
    }

    public JotHistoryPK getJotHistoryPK() {
        return jotHistoryPK;
    }

    public void setJotHistoryPK(JotHistoryPK jotHistoryPK) {
        this.jotHistoryPK = jotHistoryPK;
    }


    public String getSavedate() {
        return savedate;
    }

    public void setSavedate(String savedate) {
        this.savedate = savedate;
    }

    public Jot getJot() {
        return jot;
    }

    public void setJot(Jot jot) {
        this.jot = jot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jotHistoryPK != null ? jotHistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotHistory)) {
            return false;
        }
        JotHistory other = (JotHistory) object;
        if ((this.jotHistoryPK == null && other.jotHistoryPK != null) || (this.jotHistoryPK != null && !this.jotHistoryPK.equals(other.jotHistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotHistory[ jotHistoryPK=" + jotHistoryPK + " ]";
    }

    public byte[] getObject() {
        return object;
    }

    public void setObject(byte[] object) {
        this.object = object;
    }
    
}
