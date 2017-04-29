/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.text.html.parser.Entity;

/**
 *
 * @author Home
 */

public class Topic {

    public long id;
    private long replyNum;
    private String createrName;
    private String title;
    private String body;
    private long catId;
    private Map<String, Attachment> attachments = new LinkedHashMap<>();
    private Map<Long, Reply> replies = new LinkedHashMap<>();

    public long getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(long replyNum) {
        this.replyNum = replyNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    
    
    public Attachment getAttachment(String name) {
        return this.attachments.get(name);
    }
    
    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

    public boolean hasAttachment(String name) {
        return this.attachments.containsKey(name);
    }

    public Attachment deleteAttachment(String name) {
        return this.attachments.remove(name);
    }

    public Reply getReply(Long id) {
        return this.replies.get(id);
    }

    public Collection<Reply> getReplies() {
        return this.replies.values();
    }

    public void addReply(Reply reply) {
        this.replies.put(reply.getId(), reply);
    }

    public int getNumberOfReplies() {
        return this.replies.size();
    }

    public boolean hasReply(Long id) {
        return this.replies.containsKey(id);
    }

    public Reply deleteReply(Long id) {
        return this.replies.remove(id);
    }

}
