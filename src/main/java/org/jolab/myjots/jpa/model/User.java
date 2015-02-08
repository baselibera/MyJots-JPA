/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jolab
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByIdAppUser", query = "SELECT u FROM User u WHERE u.idAppUser = :idAppUser"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByEnabled", query = "SELECT u FROM User u WHERE u.enabled = :enabled")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAppUser")
    private Integer idAppUser;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToMany(mappedBy = "userList")
    private List<Role> roleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<JotUser> jotUserList;

    public User() {
    }

    public User(Integer idAppUser) {
        this.idAppUser = idAppUser;
    }

    public User(Integer idAppUser, String username, String email, boolean enabled) {
        this.idAppUser = idAppUser;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
    }

    public Integer getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(Integer idAppUser) {
        this.idAppUser = idAppUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @XmlTransient
    public List<JotUser> getJotUserList() {
        return jotUserList;
    }

    public void setJotUserList(List<JotUser> jotUserList) {
        this.jotUserList = jotUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAppUser != null ? idAppUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idAppUser == null && other.idAppUser != null) || (this.idAppUser != null && !this.idAppUser.equals(other.idAppUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jolab.myjots.jpa.model.User[ idAppUser=" + idAppUser + " ]";
    }
    
}
