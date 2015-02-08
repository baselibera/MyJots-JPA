/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "Jot_User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JotUser.findAll", query = "SELECT j FROM JotUser j"),
    @NamedQuery(name = "JotUser.findByIdAppUser", query = "SELECT j FROM JotUser j WHERE j.jotUserPK.idAppUser = :idAppUser"),
    @NamedQuery(name = "JotUser.findByIdJot", query = "SELECT j FROM JotUser j WHERE j.jotUserPK.idJot = :idJot"),
    @NamedQuery(name = "JotUser.findByIsOwner", query = "SELECT j FROM JotUser j WHERE j.isOwner = :isOwner")})
public class JotUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JotUserPK jotUserPK;
    @Column(name = "isOwner")
    private Boolean isOwner;
    @JoinColumn(name = "idJot", referencedColumnName = "idJot", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jot jot;
    @JoinColumn(name = "idAppUser", referencedColumnName = "idAppUser", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public JotUser() {
    }

    public JotUser(JotUserPK jotUserPK) {
        this.jotUserPK = jotUserPK;
    }

    public JotUser(int idAppUser, int idJot) {
        this.jotUserPK = new JotUserPK(idAppUser, idJot);
    }

    public JotUserPK getJotUserPK() {
        return jotUserPK;
    }

    public void setJotUserPK(JotUserPK jotUserPK) {
        this.jotUserPK = jotUserPK;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public Jot getJot() {
        return jot;
    }

    public void setJot(Jot jot) {
        this.jot = jot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jotUserPK != null ? jotUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotUser)) {
            return false;
        }
        JotUser other = (JotUser) object;
        if ((this.jotUserPK == null && other.jotUserPK != null) || (this.jotUserPK != null && !this.jotUserPK.equals(other.jotUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotUser[ jotUserPK=" + jotUserPK + " ]";
    }
    
}
