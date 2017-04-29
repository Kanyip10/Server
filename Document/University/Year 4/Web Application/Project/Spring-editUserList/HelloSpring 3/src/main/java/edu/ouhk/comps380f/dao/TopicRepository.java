/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;


import edu.ouhk.comps380f.model.Topic;
import java.util.List;



 
/**
 *
 * @author KanYip
 */
public interface TopicRepository {
    public void create(Topic topic);
    public List<Topic> findAll();
    public Topic findOne(int id);
    public List<Topic> findTopics(int catId);
    public int findLargest();
}
