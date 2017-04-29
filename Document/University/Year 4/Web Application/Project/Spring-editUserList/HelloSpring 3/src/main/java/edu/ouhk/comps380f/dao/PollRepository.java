/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Poll;
import java.util.List;

/**
 *
 * @author KanYip
 */
public interface PollRepository {
    public void create(Poll user);
    public Poll findPoll(String username); 
    public void deletePoll(String username);
}
