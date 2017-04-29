/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.controller.ReplyController.RForm;
import edu.ouhk.comps380f.dao.AttachmentRepository;
import edu.ouhk.comps380f.dao.ReplyRepository;
import edu.ouhk.comps380f.dao.TopicRepository;
import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Reply;
import edu.ouhk.comps380f.model.Topic;
import edu.ouhk.comps380f.view.DownloadingView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author KanYip
 */
//@RequestMapping("{catId}/topic/view/{topicId}/attachment")
public class AttachmentController {
    
    @Autowired
    TopicRepository topicRepo;
    @Autowired
    ReplyRepository replyRepo;
    @Autowired
    AttachmentRepository attachmentRepo;
    

    /*
    //Downloading a file attachment in a particular topic ID......
     @RequestMapping(value = "{attachment:.+}",method = RequestMethod.GET)
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
       //View a topic of a particular topic ID...maybe list all the reply of that topic ID also?
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("topicId") int topicId) {
        Topic topic = topicRepo.findOne(topicId);

        if (topic == null) {
            return new ModelAndView(new RedirectView("/", true));
        }
        
        //Like a request dispatcher
        ModelAndView modelAndView = new ModelAndView("view", "replyForm", new RForm());
        modelAndView.addObject("attachments", attachmentRepo.findAttachment(topicId));

        //modelAndView.addObject("reply", replylist)??;
        return modelAndView;
    }
    
/*
    @RequestMapping(value = "/{topicId}/{replies.id}/attachment/{attachment:.+}", method = RequestMethod.GET)
    public View replyDownload(@PathVariable("topicId") long topicId, @PathVariable("replies.id") long replyId, @PathVariable("attachment") String name) {
        Reply reply = this.topicDatabase.get(topicId).getReply(replyId);
        if (reply != null) {
            Attachment attachment = reply.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(), attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("{catId}/topic/list", true);
    }
 */
}
