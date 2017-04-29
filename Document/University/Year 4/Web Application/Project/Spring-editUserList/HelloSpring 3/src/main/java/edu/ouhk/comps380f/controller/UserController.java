/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;
import edu.ouhk.comps380f.dao.UserRepository;
import edu.ouhk.comps380f.model.Users;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
/**
 *
 * @author KanYip
 */
@Controller 
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = {"", "listUser"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("users", userRepo.findAll());
        return "listUser";
    }

    public static class Form {
        private String username;
        private String password;
        private String[] roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
        
        
    }

@RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("register", "userForm", new Form());
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form) throws IOException {
        Users user = new Users();
        user.setUserName(form.getUsername());
        user.setPassword(form.getPassword());
        for (String role : form.getRoles()) {
            user.addRole(role);
        }
        userRepo.create(user);
        return new RedirectView("listUser", true);
    }

    @RequestMapping(value = "listUser/delete/{name}", method = RequestMethod.GET)
    public View deleteTicket(@PathVariable("name")String name) {
        userRepo.deleteByName(name);
        return new RedirectView("/", true);
    }
    
    @RequestMapping(value = "listUser/editUser/{name}", method = RequestMethod.GET)
    public ModelAndView edit() {
        return new ModelAndView("editUser", "userForm", new Form());
    }
    
    @RequestMapping(value = "listUser/editUser/{name}", method = RequestMethod.POST)
    public View edit(Form form, @PathVariable("name") String name) throws IOException {
        Users user = new Users();
        //user.setUserName(form.getUsername());
        user.setPassword(form.getPassword());
        for (String role : form.getRoles()) {
            user.addRole(role);
        }
        userRepo.updateUser(user, name);
        return new RedirectView("/", true);
    }
}
