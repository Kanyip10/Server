/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Attachment;
import java.util.List;

/**
 *
 * @author KanYip
 */
public interface AttachmentRepository {
    public void create(Attachment attachment);
    public void createRA(Attachment attachment);
    public List<Attachment> findAll();
    public Attachment findOne(int id);
    public Attachment findOneR(int id);
    public Attachment findC(String name);
    public Attachment findR(String name);
    public List<Attachment> findTopicAttachment(int topicid);
    public List<Attachment> findReplyAttachment(int replyid);
}
