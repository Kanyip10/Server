/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Users;
import java.util.List;

/**
 *
 * @author KanYip
 */
public interface UserRepository {
    public void create(Users user);
    public List<Users> findAll();
    public Users findByUsername(String username); 
    public Users findById(int id); 
    //public void deleteById(int id);
    public void deleteByName(String name);
    public void updateUser(Users user, String name);
}
