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
public class Poll {
    private long id;
    private String question;
    private String voter;
    private List<String> choices = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }
    
    public List<String> getChoices() {
        return choices; 
    }
    
    public void addChoice(String choice) { 
        this.choices.add(choice);
    }
    
    public boolean hasChoice(String choice) { 
        return this.choices.contains(choice);
    }
}
