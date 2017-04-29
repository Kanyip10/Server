/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Reply;
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
public class ReplyRepositoryImpl implements ReplyRepository{
    
    private DataSource dataSource;
    private JdbcOperations jdbcOp;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

       
    private static final class ReplyRowMapper implements RowMapper<Reply>{
        public Reply mapRow(ResultSet rs, int i) throws SQLException{
            Reply aReply = new Reply();
            aReply.setId(rs.getInt("id"));
            aReply.setReplierName(rs.getString("replier"));
            aReply.setBody(rs.getString("body"));
            return aReply;
        }
    }
    
    
    private static final String SQL_INSERT_REPLY
            = "INSERT INTO reply(id, replier, topicId, body) VALUES(?,?,?,?)";
            
    //Create a new topic in database 
    @Override
    public void create(Reply reply) {
        jdbcOp.update(SQL_INSERT_REPLY,
                reply.getId(),
                reply.getReplierName(),
                reply.getTopicId(),
                reply.getBody());
    }
    
    private static final String SQL_SELECT_ALL_REPLY
            = "SELECT * FROM reply";
    
    
    //Get all Reply from the database
    @Override
    public List<Reply> findAll() {
        List<Reply> replies = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY);
        
        for(Map<String, Object> row : rows){
            Reply reply = new Reply();
            int id = (int) row.get("id");
            reply.setId(id);
            String replierName = (String) row.get("replier");
            reply.setReplierName(replierName);
            int topicId = (int) row.get("topicId");
            reply.setTopicId(topicId);
            String body = (String) row.get("body");
            reply.setBody(body);
            replies.add(reply);
        }
        return replies;
    }

    private static final String SQL_SELECT_ONE_REPLY
            = "SELECT * FROM reply WHERE id =?";
    
    
    //Get all Reply from the database
    @Override
    public Reply findOne(int id) {
        return jdbcOp.queryForObject(SQL_SELECT_ONE_REPLY,new ReplyRowMapper(),id);
    }
    

    
    private static final String SQL_SELECT_REPLY_FROM_TOPIC
            = "SELECT reply.id, reply.body, reply.replier FROM reply , topic "
            + "WHERE reply.topicID = topic.id AND topicID = ?";
    
    //Get all Reply from the particular topic
    @Override
    public List<Reply> findReply(int topicId){
        List<Reply> replies = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_REPLY_FROM_TOPIC,topicId);
        
        for(Map<String, Object> row : rows){
            Reply reply = new Reply();
            int id = (int) row.get("id");
            reply.setId(id);
            String replierName = (String) row.get("replier");
            reply.setReplierName(replierName);
            String body = (String) row.get("body");
            reply.setBody(body);
            replies.add(reply);
        }
        return replies;
    }
    
    private static final String SQL_LARGEST 
        ="SELECT count(*) FROM reply ";

    @Override
    public int findLargest() {
        return jdbcOp.queryForObject(SQL_LARGEST, Integer.class);
    }
}
