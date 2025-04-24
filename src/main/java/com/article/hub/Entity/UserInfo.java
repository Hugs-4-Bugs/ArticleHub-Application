package com.article.hub.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NamedQuery(name = "UserInfo.getAllAppuser", query = "select new com.article.hub.Entity.UserInfo(id, name, email, status) from appuser where isDeletable = 'true' and email not in (:email)")
// in the above query the (id, name, email, status) is being used as the constructor

// query to update the appuser's status
@NamedQuery(name = "UserInfo.updateUserStatus", query = "update appuser set status=:status where id=:id and isDeletable='true'")


@Entity(name = "appuser")
@Table(name = "appuser")
//@Data
//@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String isDeletable;
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(String isDeletable) {
        this.isDeletable = isDeletable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // Default constructor
    public UserInfo() {

    }

    public UserInfo(int id, String name, String email, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }


    // since we have added @Data annotation so, we don't need to add the getters & setters maunally
}
