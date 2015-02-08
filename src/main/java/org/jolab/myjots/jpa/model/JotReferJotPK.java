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
public class JotReferJotPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idJot")
    private int idJot;
    @Basic(optional = false)
    @Column(name = "idJotReferred")
    private int idJotReferred;

    public JotReferJotPK() {
    }

    public JotReferJotPK(int idJot, int idJotReferred) {
        this.idJot = idJot;
        this.idJotReferred = idJotReferred;
    }

    public int getIdJot() {
        return idJot;
    }

    public void setIdJot(int idJot) {
        this.idJot = idJot;
    }

    public int getIdJotReferred() {
        return idJotReferred;
    }

    public void setIdJotReferred(int idJotReferred) {
        this.idJotReferred = idJotReferred;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idJot;
        hash += (int) idJotReferred;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotReferJotPK)) {
            return false;
        }
        JotReferJotPK other = (JotReferJotPK) object;
        if (this.idJot != other.idJot) {
            return false;
        }
        if (this.idJotReferred != other.idJotReferred) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotReferJotPK[ idJot=" + idJot + ", idJotReferred=" + idJotReferred + " ]";
    }
    
}
