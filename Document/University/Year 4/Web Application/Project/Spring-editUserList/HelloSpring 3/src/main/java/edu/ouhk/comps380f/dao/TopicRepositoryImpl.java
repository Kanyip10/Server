/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Topic;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KanYip
 */
@Repository
public class TopicRepositoryImpl implements TopicRepository{
    
    private DataSource dataSource;
    private JdbcOperations jdbcOp;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }


    
    private static final class TopicRowMapper implements RowMapper<Topic>{
        public Topic mapRow(ResultSet rs, int i) throws SQLException{
            Topic aTopic = new Topic();
            aTopic.setId(rs.getInt("id"));
            aTopic.setCreaterName(rs.getString("creater"));
            aTopic.setTitle(rs.getString("title"));
            aTopic.setBody(rs.getString("body"));
            return aTopic;
        }
    }
        
    private static final String SQL_INSERT_TOPIC
            = "INSERT INTO topic(id, creater, title, body,catId) VALUES(?,?,?,?,?)";
            
    //Create a new topic in database 
    @Override
    public void create(Topic topic) {
        jdbcOp.update(SQL_INSERT_TOPIC,
                topic.getId(),
                topic.getCreaterName(),
                topic.getTitle(),
                topic.getBody(),
                topic.getCatId());
    }
    
    private static final String SQL_SELECT_ALL_TOPIC
            = "SELECT * FROM topic";
    
    
    //Get all Topic from the database
    @Override
    public List<Topic> findAll() {
        List<Topic> topics = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_TOPIC);
        
        for(Map<String, Object> row : rows){
            Topic topic = new Topic();
            int id = (int) row.get("id");
            topic.setId(id);
            String createrName = (String) row.get("creater");
            topic.setCreaterName(createrName);
            String title = (String) row.get("title");
            topic.setTitle(title);
            String body = (String) row.get("body");
            topic.setBody(body);
            topics.add(topic);
        }
        return topics;
    }

    private static final String SQL_SELECT_ONE_TOPIC
            = "SELECT id, creater, title, body FROM topic WHERE id = ?";
    
    
    //Get all Topic from the database
    public Topic findOne(int id) {
        return jdbcOp.queryForObject(SQL_SELECT_ONE_TOPIC,new TopicRowMapper(),id);
    }
    
        
    private static final String SQL_SELECT_TOPIC_FROM_CATEGORY
            = "SELECT topic.id, topic.creater, topic.title, topic.body FROM topic , category "
            + "WHERE topic.catId = category.id AND category.id = ?";
    
    //Get all Reply from the particular topic
    @Override
    public List<Topic> findTopics(int catId){
        List<Topic> topics = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_TOPIC_FROM_CATEGORY, catId);
        
        for(Map<String, Object> row : rows){
            Topic topic = new Topic();
            int id = (int) row.get("id");
            topic.setId(id);
            String createrName = (String) row.get("creater");
            topic.setCreaterName(createrName);
            String title = (String) row.get("title");
            topic.setTitle(title);
            String body = (String) row.get("body");
            topic.setBody(body);
            topics.add(topic);
        }
        return topics;
    }
    

    private static final String SQL_LARGEST 
        ="SELECT count(*) FROM topic";

    @Override
    public int findLargest() {
        return jdbcOp.queryForObject(SQL_LARGEST, Integer.class);
    }
}
