/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Reply;
import java.util.List;

/**
 *
 * @author KanYip
 */

public interface ReplyRepository {
    public void create(Reply reply);
    public List<Reply> findAll();
    public Reply findOne(int id);
    public List<Reply> findReply(int topicid);
    public int findLargest();
}
