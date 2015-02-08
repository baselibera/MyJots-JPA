/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jolab
 */
@Entity
@Table(name = "tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
    @NamedQuery(name = "Tag.findByIdTag", query = "SELECT t FROM Tag t WHERE t.idTag = :idTag"),
    @NamedQuery(name = "Tag.findByValue", query = "SELECT t FROM Tag t WHERE t.value = :value"),
    @NamedQuery(name = "Tag.findByDescription", query = "SELECT t FROM Tag t WHERE t.description = :description"),
    @NamedQuery(name = "Tag.findByCategory", query = "SELECT t FROM Tag t WHERE t.category = :category")})
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTag")
    private Integer idTag;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @ManyToMany(mappedBy = "tagList")
    private List<Jot> jotList;

    public Tag() {
    }

    public Tag(Integer idTag) {
        this.idTag = idTag;
    }

    public Tag(Integer idTag, String value) {
        this.idTag = idTag;
        this.value = value;
    }

    public Integer getIdTag() {
        return idTag;
    }

    public void setIdTag(Integer idTag) {
        this.idTag = idTag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlTransient
    public List<Jot> getJotList() {
        return jotList;
    }

    public void setJotList(List<Jot> jotList) {
        this.jotList = jotList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTag != null ? idTag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.idTag == null && other.idTag != null) || (this.idTag != null && !this.idTag.equals(other.idTag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.Tag[ idTag=" + idTag + " ]";
    }
    
}
