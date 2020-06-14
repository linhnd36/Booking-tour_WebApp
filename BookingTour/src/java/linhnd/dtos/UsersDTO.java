/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.dtos;

import java.io.Serializable;

/**
 *
 * @author PC
 */
public class UsersDTO implements Serializable {

    private String UserID, Password, Name, FacebookID, FacebookLink, StatusId, RoleId;

    public UsersDTO() {
    }

    public String getUserID() {
        return UserID;
    }

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return Name;
    }

    public String getFacebookID() {
        return FacebookID;
    }

    public String getFacebookLink() {
        return FacebookLink;
    }

    public String getStatusId() {
        return StatusId;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setFacebookID(String FacebookID) {
        this.FacebookID = FacebookID;
    }

    public void setFacebookLink(String FacebookLink) {
        this.FacebookLink = FacebookLink;
    }

    public void setStatusId(String StatusId) {
        this.StatusId = StatusId;
    }

    public void setRoleId(String RoleId) {
        this.RoleId = RoleId;
    }

}
