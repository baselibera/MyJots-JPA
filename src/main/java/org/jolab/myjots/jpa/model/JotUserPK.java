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
public class JotUserPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idAppUser")
    private int idAppUser;
    @Basic(optional = false)
    @Column(name = "idJot")
    private int idJot;

    public JotUserPK() {
    }

    public JotUserPK(int idAppUser, int idJot) {
        this.idAppUser = idAppUser;
        this.idJot = idJot;
    }

    public int getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(int idAppUser) {
        this.idAppUser = idAppUser;
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
        hash += (int) idAppUser;
        hash += (int) idJot;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotUserPK)) {
            return false;
        }
        JotUserPK other = (JotUserPK) object;
        if (this.idAppUser != other.idAppUser) {
            return false;
        }
        if (this.idJot != other.idJot) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotUserPK[ idAppUser=" + idAppUser + ", idJot=" + idJot + " ]";
    }
    
}
