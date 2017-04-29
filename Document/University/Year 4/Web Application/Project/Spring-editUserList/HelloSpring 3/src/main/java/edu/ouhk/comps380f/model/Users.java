/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KanYip
 */
public class Users {
    private long id;
    private String userName;
    private String password;
    private List<String> roles = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<String> getRoles() {
        return roles; 
    }
    
    public void addRole(String role) { 
        this.roles.add(role);
    }
    
    public boolean hasRole(String role) { 
        return this.roles.contains(role);
    }
}
