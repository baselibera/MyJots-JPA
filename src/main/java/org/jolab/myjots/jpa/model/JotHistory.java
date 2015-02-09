/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

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

/**
 *
 * @author jolab
 */
@Entity
@Table(name = "JotHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JotHistory.findAll", query = "SELECT j FROM JotHistory j"),
    @NamedQuery(name = "JotHistory.findByIdJotHistory", query = "SELECT j FROM JotHistory j WHERE j.idJotHistory = :idJotHistory"),
    @NamedQuery(name = "JotHistory.findByIdJot", query = "SELECT j FROM JotHistory j WHERE j.idJot.idJot = :idJot"),
    @NamedQuery(name = "JotHistory.findBySavedate", query = "SELECT j FROM JotHistory j WHERE j.savedate = :savedate")})
public class JotHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJotHistory")
    private Integer idJotHistory;
    @Lob
    @Column(name = "object")
    private byte[] object;
    @Basic(optional = false)
    @Column(name = "savedate")
    private String savedate;
    @JoinColumn(name = "idJot", referencedColumnName = "idJot")
    @ManyToOne(optional = false)
    private Jot idJot;

    public JotHistory() {
    }

    public JotHistory(Integer idJotHistory) {
        this.idJotHistory = idJotHistory;
    }

    public JotHistory(Integer idJotHistory, String savedate) {
        this.idJotHistory = idJotHistory;
        this.savedate = savedate;
    }

    public Integer getIdJotHistory() {
        return idJotHistory;
    }

    public void setIdJotHistory(Integer idJotHistory) {
        this.idJotHistory = idJotHistory;
    }

    public byte[] getObject() {
        return object;
    }

    public void setObject(byte[] object) {
        this.object = object;
    }

    public String getSavedate() {
        return savedate;
    }

    public void setSavedate(String savedate) {
        this.savedate = savedate;
    }

    public Jot getIdJot() {
        return idJot;
    }

    public void setIdJot(Jot idJot) {
        this.idJot = idJot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJotHistory != null ? idJotHistory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotHistory)) {
            return false;
        }
        JotHistory other = (JotHistory) object;
        if ((this.idJotHistory == null && other.idJotHistory != null) || (this.idJotHistory != null && !this.idJotHistory.equals(other.idJotHistory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotHistory[ idJotHistory=" + idJotHistory + " ]";
    }
    
}
