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
@Table(name = "JotReferJot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JotReferJot.findAll", query = "SELECT j FROM JotReferJot j"),
    @NamedQuery(name = "JotReferJot.findByIdJot", query = "SELECT j FROM JotReferJot j WHERE j.jotReferJotPK.idJot = :idJot"),
    @NamedQuery(name = "JotReferJot.findByIdJotReferred", query = "SELECT j FROM JotReferJot j WHERE j.jotReferJotPK.idJotReferred = :idJotReferred"),
    @NamedQuery(name = "JotReferJot.findByWeight", query = "SELECT j FROM JotReferJot j WHERE j.weight = :weight"),
    @NamedQuery(name = "JotReferJot.findByComment", query = "SELECT j FROM JotReferJot j WHERE j.comment = :comment")})
public class JotReferJot implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JotReferJotPK jotReferJotPK;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "idJotReferred", referencedColumnName = "idJot", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jot jot;
    @JoinColumn(name = "idJot", referencedColumnName = "idJot", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jot jot1;

    public JotReferJot() {
    }

    public JotReferJot(JotReferJotPK jotReferJotPK) {
        this.jotReferJotPK = jotReferJotPK;
    }

    public JotReferJot(int idJot, int idJotReferred) {
        this.jotReferJotPK = new JotReferJotPK(idJot, idJotReferred);
    }

    public JotReferJotPK getJotReferJotPK() {
        return jotReferJotPK;
    }

    public void setJotReferJotPK(JotReferJotPK jotReferJotPK) {
        this.jotReferJotPK = jotReferJotPK;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Jot getJot() {
        return jot;
    }

    public void setJot(Jot jot) {
        this.jot = jot;
    }

    public Jot getJot1() {
        return jot1;
    }

    public void setJot1(Jot jot1) {
        this.jot1 = jot1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jotReferJotPK != null ? jotReferJotPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JotReferJot)) {
            return false;
        }
        JotReferJot other = (JotReferJot) object;
        if ((this.jotReferJotPK == null && other.jotReferJotPK != null) || (this.jotReferJotPK != null && !this.jotReferJotPK.equals(other.jotReferJotPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.JotReferJot[ jotReferJotPK=" + jotReferJotPK + " ]";
    }
    
}
