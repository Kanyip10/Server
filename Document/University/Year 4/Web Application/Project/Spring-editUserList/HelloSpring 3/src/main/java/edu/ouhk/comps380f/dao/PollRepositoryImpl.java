/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Poll;
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
/**
 *
 * @author KanYip
 */
public class PollRepositoryImpl implements PollRepository{
       private DataSource dataSource;
    private JdbcOperations jdbcOp;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class PollRowMapper implements RowMapper<Poll>{
        public Poll mapRow(ResultSet rs, int i) throws SQLException{
            Poll aPoll = new Poll();
            aPoll.setId(rs.getInt("id"));
            aPoll.setQuestion(rs.getString("question"));
            aPoll.setVoter(rs.getString("voter"));
            return aPoll;
        }
    }
    
    private static final String SQL_INSERT_POLL
        = "INSERT INTO poll(question, voter) VALUES(?,?)";
    private static final String SQL_INSERT_CHOICES
        = "INSERT INTO poll_choices (pollId, choice) VALUES (?, ?)";

    @Override
    public void create(Poll poll) {
        jdbcOp.update(SQL_INSERT_POLL,
        poll.getQuestion(),
        poll.getVoter());
        for (String choice : poll.getChoices()) {
            jdbcOp.update(SQL_INSERT_CHOICES, poll.getId(), choice);
        } 
    }

    private static final String SQL_SELECT_CHOICES
        = "SELECT choice FROM poll_choices WHERE pollId = ?";
    

    private static final String SQL_SELECT_POLL
        = "SELECT question, voter FROM poll WHERE pollId = ?";        
    
    @Override
    public Poll findPoll(String pollId) {
        Poll aPoll = jdbcOp.queryForObject(SQL_SELECT_POLL, new PollRowMapper(), pollId);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_CHOICES, pollId);
        for (Map<String, Object> row : rows) {
            aPoll.addChoice((String) row.get("choice")); 
        }
        return aPoll;
    }

    private static final String SQL_DELETE_USER
        = "DELETE FROM poll where pollId = ?"; 
    private static final String SQL_DELETE_ROLES
        = "DELETE FROM poll_choices where pollid = ?";
    
    @Override
    public void deletePoll(String pollId) {
        jdbcOp.update(SQL_DELETE_ROLES, pollId);
        jdbcOp.update(SQL_DELETE_USER, pollId);
    }
    

}
