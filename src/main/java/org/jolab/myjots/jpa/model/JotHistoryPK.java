/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author jolab
 */
@Embeddable
public class JotHistoryPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idJotHistory")
    private int idJotHistory;
    @Basic(optional = false)
    @Column(name = "idJot")
    private int idJot;

    public JotHistoryPK() {
    }

    public JotHistoryPK(int idJotHistory, int idJot) {
        this.idJotHistory = idJotHistory;
        this.idJot = idJot;
    }

    public int getIdJotHistory() {
        return idJotHistory;
    }

    public void setIdJotHistory(int idJotHistory) {
        this.idJotHistory = idJotHistory;
    }

    public int getIdJot() {
        return idJot;
    }

    public void setIdJot(int idJot) {
        this.idJot = idJot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idJotHistory;
        hash += (int) idJot;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotHistoryPK)) {
            return false;
        }
        JotHistoryPK other = (JotHistoryPK) object;
        if (this.idJotHistory != other.idJotHistory) {
            return false;
        }
        if (this.idJot != other.idJot) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotHistoryPK[ idJotHistory=" + idJotHistory + ", idJot=" + idJot + " ]";
    }
    
}
