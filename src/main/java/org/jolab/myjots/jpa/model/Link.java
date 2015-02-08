/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jolab
 */
@Entity
@Table(name = "Link")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Link.findAll", query = "SELECT l FROM Link l"),
    @NamedQuery(name = "Link.findByIdLink", query = "SELECT l FROM Link l WHERE l.idLink = :idLink"),
    @NamedQuery(name = "Link.findByName", query = "SELECT l FROM Link l WHERE l.name = :name"),
    @NamedQuery(name = "Link.findByCreateDate", query = "SELECT l FROM Link l WHERE l.createDate = :createDate"),
    @NamedQuery(name = "Link.findByVerificatioDate", query = "SELECT l FROM Link l WHERE l.verificatioDate = :verificatioDate")})
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLink")
    private Integer idLink;
    @Basic(optional = false)
    @Lob
    @Column(name = "URL")
    private String url;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "verificatioDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verificatioDate;
    @JoinColumn(name = "idJot", referencedColumnName = "idJot")
    @ManyToOne(optional = false)
    private Jot idJot;

    public Link() {
    }

    public Link(Integer idLink) {
        this.idLink = idLink;
    }

    public Link(Integer idLink, String url, Date createDate) {
        this.idLink = idLink;
        this.url = url;
        this.createDate = createDate;
    }

    public Integer getIdLink() {
        return idLink;
    }

    public void setIdLink(Integer idLink) {
        this.idLink = idLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getVerificatioDate() {
        return verificatioDate;
    }

    public void setVerificatioDate(Date verificatioDate) {
        this.verificatioDate = verificatioDate;
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
        hash += (idLink != null ? idLink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Link)) {
            return false;
        }
        Link other = (Link) object;
        if ((this.idLink == null && other.idLink != null) || (this.idLink != null && !this.idLink.equals(other.idLink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.Link[ idLink=" + idLink + " ]";
    }
    
}
