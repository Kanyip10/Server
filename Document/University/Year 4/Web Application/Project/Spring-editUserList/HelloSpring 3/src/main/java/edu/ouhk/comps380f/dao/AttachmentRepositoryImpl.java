/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Attachment;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
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
public class AttachmentRepositoryImpl implements AttachmentRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class AttachmentRowMapper implements RowMapper<Attachment> {

        public Attachment mapRow(ResultSet rs, int i) throws SQLException {
            Attachment anAttachment = new Attachment();
            anAttachment.setId(rs.getInt("id"));
            anAttachment.setName(rs.getString("attachmentName"));
            anAttachment.setMimeContentType(rs.getString("contentType"));
            anAttachment.setContents(rs.getBytes("content"));
            return anAttachment;
        }
    }
    


    private static final String SQL_INSERT_ATTACHMENT
            = "INSERT INTO attachments(attachmentName, contentType, content, topicId) VALUES(?,?,?,?)";

    //Create a new topic attachment in database 
    @Override
    public void create(Attachment attachment) {
        jdbcOp.update(SQL_INSERT_ATTACHMENT,
                attachment.getName(),
                attachment.getMimeContentType(),
                attachment.getContents(),
                attachment.getTopicId());
    }

    private static final String SQL_INSERT_REPLY_ATTACHMENT
            = "INSERT INTO attachments(attachmentName, contentType, content, replyId) VALUES(?,?,?,?)";

    //Create a new reply attachment in database 
    @Override
    public void createRA(Attachment attachment) {
        jdbcOp.update(SQL_INSERT_REPLY_ATTACHMENT,
                attachment.getName(),
                attachment.getMimeContentType(),
                attachment.getContents(),
                attachment.getReplyId());
    }

    private static final String SQL_SELECT_ALL_ATTACHMENT
            = "SELECT * FROM attachments";

    //Get all Attachment from the database
    @Override
    public List<Attachment> findAll() {
        List<Attachment> attachments = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_ATTACHMENT);

        for (Map<String, Object> row : rows) {
            Attachment attachment = new Attachment();
            int id = (int) row.get("id");
            attachment.setId(id);
            String attachmentName = (String) row.get("attachment");
            attachment.setName(attachmentName);
            String mimeContentType = (String) row.get("contentType");
            attachment.setMimeContentType(mimeContentType);
            int topicId = (int) row.get("topicId");
            attachment.setTopicId(topicId);
            byte[] content = null;
            //content[0] = (byte) row.get("content");
            attachment.setContents(content);
            attachments.add(attachment);
        }
        return attachments;
    }

    private static final String SQL_SELECT_ONE_ATTACHMENT
            = "SELECT * FROM attachments WHERE topicId =?";

    //Get an Attachment from the database by topic id
    @Override
    public Attachment findOne(int id) {
        return jdbcOp.queryForObject(SQL_SELECT_ONE_ATTACHMENT, new AttachmentRowMapper(), id);
    }
    
    private static final String SQL_SELECT_ONE_REPLY_ATTACHMENT
            = "SELECT * FROM attachments WHERE replyId =?";

    //Get an Attachment from the database by reply id
    @Override
    public Attachment findOneR(int id) {
        return jdbcOp.queryForObject(SQL_SELECT_ONE_REPLY_ATTACHMENT, new AttachmentRowMapper(), id);
    }
    
    private static final String SQL_SELECT_ONE_ATTACHMENT_CONTENT
            = "SELECT * FROM attachments WHERE attachmentName =?";
    
    //Get an topic Attachment from the database by file name 
    @Override
    public Attachment findC(String name) {
        return jdbcOp.queryForObject(SQL_SELECT_ONE_ATTACHMENT_CONTENT, new AttachmentRowMapper(), name);
    }

    private static final String SQL_SELECT_ONE_REPLY_ATTACHMENT_CONTENT
            = "SELECT * FROM attachments WHERE attachmentName =?";
    
    //Get an reply Attachment from the database by file name 
    @Override
    public Attachment findR(String name) {
        return jdbcOp.queryForObject(SQL_SELECT_ONE_REPLY_ATTACHMENT_CONTENT, new AttachmentRowMapper(), name);
    }
    
    
    private static final String SQL_SELECT_ATTACHMENT_FROM_TOPIC
            = "SELECT attachments.id, attachments.attachmentName, attachments.contentType, attachments.content,"
            + "attachments.topicId"
            + " FROM attachments , topic "
            + "WHERE attachments.topicID = topic.id AND topic.id = ?";

    //Get all Attachment from the particular topic
    @Override
    public List<Attachment> findTopicAttachment(int tId) {
        List<Attachment> attachments = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ATTACHMENT_FROM_TOPIC, tId);

        for (Map<String, Object> row : rows) {
            Attachment attachment = new Attachment();
            int id = (int) row.get("id");
            attachment.setId(id);
            String attachmentName = (String) row.get("attachmentName");
            attachment.setName(attachmentName);
            String mimeContentType = (String) row.get("contentType");
            attachment.setMimeContentType(mimeContentType);
            int topicId = (int) row.get("topicId");
            attachment.setTopicId(topicId);
            /*
            Blob blob = (Blob)row.get("content");
            try{
                InputStream in = blob.getBinaryStream();
                int len = (int)blob.length();
                long pos = 1;
                byte[] bytes = blob.getBytes(pos, len);
                in.close();
                attachment.setContents(bytes);
            } catch (Exception e) {
	        System.out.println(e.getMessage());
            }
*/

            attachments.add(attachment);
        }
        return attachments;
    }

    private static final String SQL_SELECT_ATTACHMENT_FROM_REPLY
            = "SELECT attachments.id, attachments.attachmentName, attachments.contentType, attachments.content,"
            + "attachments.replyId"
            + " FROM attachments, reply "
            + "WHERE attachments.replyID = reply.id AND reply.id = ?";

    //Get all Attachment from the particular topic
    @Override
    public List<Attachment> findReplyAttachment(int rId) {
        List<Attachment> attachments = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ATTACHMENT_FROM_REPLY, rId);

        for (Map<String, Object> row : rows) {
            Attachment attachment = new Attachment();
            int id = (int) row.get("id");
            attachment.setId(id);
            String attachmentName = (String) row.get("attachmentName");
            attachment.setName(attachmentName);
            String mimeContentType = (String) row.get("contentType");
            attachment.setMimeContentType(mimeContentType);
            int topicId = (int) row.get("topicId");
            attachment.setTopicId(topicId);
            //byte[] content = {(byte) row.get("content")};
            //attachment.setContents(content);
            attachments.add(attachment);
        }
        return attachments;
    }
}
