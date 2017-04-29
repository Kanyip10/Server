/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Home
 */
@Controller
public class IndexController {

    @Autowired
    CategoryRepository categoryRepo;

    @RequestMapping("/")
    public View index() {
        return new RedirectView("/index", true);
    }

    //Create the category
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "index";
    }

    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }
}
