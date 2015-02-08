/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jolab
 */
@Entity
@Table(name = "Jot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jot.findAll", query = "SELECT j FROM Jot j"),
    @NamedQuery(name = "Jot.findByIdJot", query = "SELECT j FROM Jot j WHERE j.idJot = :idJot"),
    @NamedQuery(name = "Jot.findByTitle", query = "SELECT j FROM Jot j WHERE j.title = :title"),
    @NamedQuery(name = "Jot.findByStatus", query = "SELECT j FROM Jot j WHERE j.status = :status"),
    @NamedQuery(name = "Jot.findByMimeType", query = "SELECT j FROM Jot j WHERE j.mimeType = :mimeType"),
    @NamedQuery(name = "Jot.findByCreateTime", query = "SELECT j FROM Jot j WHERE j.createTime = :createTime"),
    @NamedQuery(name = "Jot.findByUpdateTime", query = "SELECT j FROM Jot j WHERE j.updateTime = :updateTime")})
public class Jot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJot")
    private Integer idJot;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "body")
    private String body;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    @Column(name = "mimeType")
    private String mimeType;
    
    //@Basic(optional = false)
    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @JoinTable(name = "Jot_Tag", joinColumns = {
        @JoinColumn(name = "idJot", referencedColumnName = "idJot")}, inverseJoinColumns = {
        @JoinColumn(name = "idTag", referencedColumnName = "idTag")})
    @ManyToMany
    private List<Tag> tagList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idJot")
    private List<Link> linkList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jot")
    private List<JotHistory> jotHistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jot")
    private List<JotUser> jotUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jot")
    private List<JotReferJot> jotReferJotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jot1")
    private List<JotReferJot> jotReferJotList1;

    public Jot() {
    }

    public Jot(Integer idJot) {
        this.idJot = idJot;
    }

    public Jot(Integer idJot, String title, String body, int status, Date createTime) {
        this.idJot = idJot;
        this.title = title;
        this.body = body;
        this.status = status;
        this.createTime = createTime;
    }

    public Integer getIdJot() {
        return idJot;
    }

    public void setIdJot(Integer idJot) {
        this.idJot = idJot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @XmlTransient
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @XmlTransient
    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    @XmlTransient
    public List<JotHistory> getJotHistoryList() {
        return jotHistoryList;
    }

    public void setJotHistoryList(List<JotHistory> jotHistoryList) {
        this.jotHistoryList = jotHistoryList;
    }

    @XmlTransient
    public List<JotUser> getJotUserList() {
        return jotUserList;
    }

    public void setJotUserList(List<JotUser> jotUserList) {
        this.jotUserList = jotUserList;
    }

    @XmlTransient
    public List<JotReferJot> getJotReferJotList() {
        return jotReferJotList;
    }

    public void setJotReferJotList(List<JotReferJot> jotReferJotList) {
        this.jotReferJotList = jotReferJotList;
    }

    @XmlTransient
    public List<JotReferJot> getJotReferJotList1() {
        return jotReferJotList1;
    }

    public void setJotReferJotList1(List<JotReferJot> jotReferJotList1) {
        this.jotReferJotList1 = jotReferJotList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJot != null ? idJot.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jot)) {
            return false;
        }
        Jot other = (Jot) object;
        if ((this.idJot == null && other.idJot != null) || (this.idJot != null && !this.idJot.equals(other.idJot))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.Jot[ idJot=" + idJot + " ]";
    }
    
}
