/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Category;
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
public class CategoryRepositoryImpl implements CategoryRepository{
    
    private DataSource dataSource;
    private JdbcOperations jdbcOp;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }
    
    private static final String SQL_SELECT_ALL_CATEGORY
            = "SELECT * FROM category";
  
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_CATEGORY);
        
        for(Map<String, Object> row : rows){
            Category category = new Category();
            int id = (int) row.get("id");
            category.setId(id);
            String catName = (String) row.get("catName");
            category.setCatName(catName);
            categories.add(category);
        }
        return categories;
    }
    
    private static final class CategoryRowMapper implements RowMapper<Category>{

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setCatName(rs.getString("catName"));
            return category;
        }
        
    }
    
    private static final String SQL_SELECT_CATEGORY
            = "SELECT * FROM category WHERE id = ?";
    
    @Override
    public Category findByCatName(String catName) {
        Category category = jdbcOp.queryForObject(SQL_SELECT_CATEGORY, 
                new CategoryRowMapper(), catName);
        return category;
    }
    
}
