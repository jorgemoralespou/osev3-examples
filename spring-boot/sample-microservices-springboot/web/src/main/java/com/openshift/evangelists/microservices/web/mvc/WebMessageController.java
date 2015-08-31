package com.openshift.evangelists.microservices.web.mvc;

import javax.validation.Valid;

import com.openshift.evangelists.microservices.web.api.Message;
import com.openshift.evangelists.microservices.web.api.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by jmorales on 8/28/15.
 */
@Controller
@RequestMapping("/")
public class WebMessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public WebMessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping
    public ModelAndView list() {
        System.out.println("WebMessageController.list");
        Iterable<Message> messages = this.messageRepository.findAll();
        return new ModelAndView("messages/list", "messages", messages);
    }

    @RequestMapping(method=RequestMethod.GET, value="{id}")
    public ModelAndView view(@PathVariable("id") Message message) {
        System.out.println("WebMessageController.viewByMessage:" + message);
        return new ModelAndView("messages/view", "message", message);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Message message) {
        System.out.println("WebMessageController.createForm:" + message);
        return "messages/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Message message, BindingResult result,
                               RedirectAttributes redirect) {
        System.out.println("WebMessageController.create:" + message);
        if (result.hasErrors()) {
            return new ModelAndView("messages/form", "formErrors", result.getAllErrors());
        }
        message = this.messageRepository.save(message);
        if (message != null){
            System.out.println("WebMessageController.Created. Saved and returned: " + message);
            redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
            System.out.println("WebMessageController.Redirecting to messageId :" + message.getId());
            return new ModelAndView("redirect:/{message.id}", "message.id", message.getId());
        }
        else{
            // TODO: Handle errors
            return new ModelAndView("redirect:/error");
        }

    }

    @RequestMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }



}
