/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.AttachmentRepository;
import edu.ouhk.comps380f.dao.ReplyRepository;
import edu.ouhk.comps380f.dao.TopicRepository;
import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Reply;
import edu.ouhk.comps380f.model.Topic;
import edu.ouhk.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Home
 */
@Controller
@RequestMapping("{catId}/topic/view/{topicId}")
public class ReplyController {

        
    @Autowired
    TopicRepository topicRepo;
    @Autowired
    ReplyRepository replyRepo;
    @Autowired
    AttachmentRepository attachmentRepo;
    

   //View a topic of a particular topic ID...maybe list all the reply of that topic ID also?
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("topicId") int topicId) {
        Topic topic = topicRepo.findOne(topicId);

        if (topic == null) {
            return new ModelAndView(new RedirectView("/topic/list", true));
        }
        
        //Like a request dispatcher
        ModelAndView modelAndView = new ModelAndView("view", "replyForm", new RForm());
        modelAndView.addObject("topidId", Long.toString(topicId));
        modelAndView.addObject("topic", topic);
        modelAndView.addObject("topicAttachments", attachmentRepo.findTopicAttachment(topicId));
        modelAndView.addObject("replies", replyRepo.findReply(topicId));
        //modelAndView.addObject("replyAttachments", attachmentRepo.findReplyAttachment(6));
        //System.out.println(attachmentRepo.findReplyAttachment(6));

        //modelAndView.addObject("reply", replylist)??;
        return modelAndView;
    }    
    
    
    //Downloading a file attachment in a particular topic ID......
     @RequestMapping(value = "attachment/{attachment:.+}",method = RequestMethod.GET)
    public View download(@PathVariable("topicId") int topicId,
            @PathVariable("attachment") String name) {
        
        Topic topic = topicRepo.findOne(topicId);
        if (topic != null) {
            Attachment attachment = attachmentRepo.findC(name);
            if (attachment != null) {
                    return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        
        return new RedirectView("/", true);
    }
    
        //Downloading a file attachment in a particular reply ID......
     @RequestMapping(value = "{replyId}/attachment/{attachment:.+}",method = RequestMethod.GET)
    public View downloadReplyAttachment(@PathVariable("replyId") int replyId,
            @PathVariable("attachment") String name,ModelMap model) {
        Reply reply = replyRepo.findOne(replyId);
        if (reply != null) {
            Attachment attachment = attachmentRepo.findC(name);
            if (attachment != null) {
                    return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        
        return new RedirectView("/", true);
    }
    
    /*
    @RequestMapping(value = "view/{topicId}", method = RequestMethod.GET)
    public ModelAndView createReply() {
        return new ModelAndView("reply", "replyForm", new RForm());
    }
     */
    //create a reply after receving the HTML form
    @RequestMapping(value = "", method = RequestMethod.POST)
    public View createReply(RForm form, @PathVariable("topicId") int topicId,Principal principal) throws IOException {
        Reply reply = new Reply();
        Topic topic = topicRepo.findOne(topicId);
        int replyId = this.getNextReplyId();
        reply.setId(replyId);
        reply.setTopicId(topicId);
        reply.setReplierName(principal.getName());
        //reply.setTitle(form.getTitle());
        reply.setBody(form.getBody());
        replyRepo.create(reply);
        for (MultipartFile filepart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setReplyId(replyId);
            attachment.setName(filepart.getOriginalFilename());
            attachment.setMimeContentType(filepart.getContentType());
            attachment.setContents(filepart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                attachmentRepo.createRA(attachment);
            }
        }
        //topicRepo.findOne(topicId).addReply(reply);

        return new RedirectView(""+topicId, true);
    }

    public int getNextReplyId() {
        return replyRepo.findLargest() + 1;
    }

    //A reply form class
    public static class RForm {

        private String replierName;
        private String body;
        private List<MultipartFile> attachments;

        public String getReplierName() {
            return replierName;
        }

        public void setReplierName(String replierName) {
            this.replierName = replierName;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }

}
