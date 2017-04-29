/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Users;
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
public class UserRepositoryImpl implements UserRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class UserRowMapper implements RowMapper<Users> {

        public Users mapRow(ResultSet rs, int i) throws SQLException {
            Users aUser = new Users();
            aUser.setId(rs.getInt("id"));
            aUser.setUserName(rs.getString("username"));
            aUser.setPassword(rs.getString("password"));
            return aUser;
        }
    }

    private static final String SQL_INSERT_USER
            = "INSERT INTO users(username, password) VALUES(?,?)";
    private static final String SQL_INSERT_ROLE
            = "INSERT INTO user_roles (username, role) VALUES (?, ?)";

    @Override
    public void create(Users user) {
        jdbcOp.update(SQL_INSERT_USER,
                user.getUserName(),
                user.getPassword());
        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE, user.getUserName(), role);
        }
    }

    private static final String SQL_SELECT_ALL_USER
            = "SELECT * FROM users";
    private static final String SQL_SELECT_ROLES
            = "SELECT username, role FROM user_roles WHERE username = ?";

    @Override
    public List<Users> findAll() {
        List<Users> users = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_USER);

        for (Map<String, Object> row : rows) {
            Users aUser = new Users();
            int id = (int) row.get("userId");
            aUser.setId(id);
            String userName = (String) row.get("username");
            aUser.setUserName(userName);
            String password = (String) row.get("password");
            aUser.setPassword(password);
            List<Map<String, Object>> roleRows
                    = jdbcOp.queryForList(SQL_SELECT_ROLES, userName);
            for (Map<String, Object> roleRow : roleRows) {
                aUser.addRole((String) roleRow.get("role"));
            }
            users.add(aUser);
        }
        return users;
    }

    private static final String SQL_SELECT_USER
            = "SELECT username, password FROM users WHERE username = ?";

    @Override
    public Users findByUsername(String userName) {
        Users aUser = jdbcOp.queryForObject(SQL_SELECT_USER, new UserRowMapper(), userName);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ROLES, userName);
        for (Map<String, Object> row : rows) {
            aUser.addRole((String) row.get("role"));
        }
        return aUser;
    }
    
    private static final String SQL_SELECT_USER_BY_ID
            = "SELECT username, password FROM users WHERE id = ?";
    
    @Override
    public Users findById(int id) {
        Users aUser = jdbcOp.queryForObject(SQL_SELECT_USER_BY_ID, new UserRowMapper(), id);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ROLES, id);
        for (Map<String, Object> row : rows) {
            aUser.addRole((String) row.get("role"));
        }
        return aUser;
    }
/*
    private static final String SQL_DELETE_USER
            = "DELETE FROM users where userid = ?";
    private static final String SQL_DELETE_ROLES
            = "DELETE FROM user_roles where userid = ?";

    @Override
    public void deleteById(int id) {
        jdbcOp.update(SQL_DELETE_ROLES, id);
        jdbcOp.update(SQL_DELETE_USER, id);
    }
  */  
    private static final String SQL_DELETE_USER_BY_NAME
            = "DELETE FROM users where userName = ?";
    private static final String SQL_DELETE_ROLES_BY_NAME
            = "DELETE FROM user_roles where userName = ?";

    @Override
    public void deleteByName(String name) {
        jdbcOp.update(SQL_DELETE_ROLES_BY_NAME, name);
        jdbcOp.update(SQL_DELETE_USER_BY_NAME, name);
    }
/*
    private static final String SQL_UPDATE_USER
            = "UPDATE users "
            + "SET userName = ?, password = ? "
            + "WHERE userName = ?";
    private static final String SQL_UPDATE_ROLE
            = "UPDATE user_roles "
            + "SET userName = ?, role = ? "
            + "WHERE userName = ?";   
    
    @Override
    public void updateUser(Users user, String name) {
        jdbcOp.update(SQL_UPDATE_USER, user.getUserName(), user.getPassword(), name);
        jdbcOp.update(SQL_UPDATE_ROLE, user.getUserName(), user.getRoles(), name);
    }
*/
    private static final String SQL_UPDATE_USER
            = "UPDATE users "
            + "SET password = ? "
            + "WHERE userName = ?";
    private static final String SQL_UPDATE_ROLE
            = "UPDATE user_roles "
            + "SET role = ? "
            + "WHERE userName = ?"; 
    @Override
    public void updateUser(Users user, String name) {
        jdbcOp.update(SQL_UPDATE_USER, user.getPassword(), name);
        for(String role:user.getRoles()){
            jdbcOp.update(SQL_UPDATE_ROLE, role, name);
        }
    }

}
