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
import java.util.List;
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
@RequestMapping("{catId}/topic/")
public class TopicController {
    
    @Autowired
    TopicRepository topicRepo;
    @Autowired
    ReplyRepository replyRepo;
    @Autowired
    AttachmentRepository attachmentRepo;
    

    //List all the topic
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model,@PathVariable("catId") int catId) {
        model.addAttribute("topicDatabase", topicRepo.findTopics(catId));
        return "list";
    }
/*
    @RequestMapping(value = "{catId}/topic/list", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("catId") int catId) {
        //Like a request dispatcher
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("topic", topicRepo.findTopics(catId));

        return "list";
    }
    */
    
    //Create a topic with an HTML form
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create(@PathVariable("catId") int catId) {
        
        ModelAndView modelAndView =  new ModelAndView("add", "topicForm", new Form());
        modelAndView.addObject("catId", catId);
        return modelAndView;
    }

    public static class Form {

        private String createrName;
        private String title;
        private String body;
        private List<MultipartFile> attachments;

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

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }

    //Create a topic after receving the HTML Form
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form,@PathVariable("catId") int catId,Principal principal) throws IOException {
        Topic topic = new Topic();
        int topicId = this.getNextTopicId();
        topic.setId(topicId);
        System.out.println(topicId);
        topic.setCreaterName(principal.getName());
        topic.setTitle(form.getTitle());
        topic.setBody(form.getBody());
        topic.setCatId(catId);
        topicRepo.create(topic);
        for (MultipartFile filepart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setTopicId(topicId);
            attachment.setName(filepart.getOriginalFilename());
            attachment.setMimeContentType(filepart.getContentType());
            attachment.setContents(filepart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                attachmentRepo.create(attachment);
            }
        }

        //this.topicDatabase.put(topic.getId(), topic);
        return new RedirectView("list");
    }

    public int getNextTopicId() {
        return topicRepo.findLargest() + 1;
    }


/*

    
    @RequestMapping(value = "/{topicId}/delete", method = RequestMethod.GET)
    public View deletetopic(@PathVariable("topicId") long topicId) {
        Topic curr = this.topicDatabase.get(topicId);
        this.topicDatabase.remove(topicId);
        //return new ModelAndView("list", "delete", curr);
        return new RedirectView("/topic/list", true);
    }
    
    @RequestMapping(value = "/{topicId}/{replies.Id}/deleteReply", method = RequestMethod.GET)
    public View deleteReply(@PathVariable("replies.Id") long replyId, @PathVariable("topicId") long topicId) {
        //Reply curr = this.replyDatabase.get(replyId);
        this.topicDatabase.get(topicId).deleteReply(replyId);
        //return new ModelAndView("/topic/view/"  + Long.toString(topicId), "deleteReply", curr);
        return new RedirectView("/topic/view/" + Long.toString(topicId), true);
    }
*/
}
