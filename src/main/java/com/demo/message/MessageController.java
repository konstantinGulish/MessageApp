package com.demo.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class MessageController {
    @Autowired
    MessageRepository messages;

    @Autowired
    RecipientRepository recipients;

    @Autowired
    LikeRepository likes;

    @PostConstruct
    public void fillTables() {
    Recipient r = new Recipient();
    r.setRecipientName("John Smith");
    recipients.save(r);

    r = new Recipient();
    r.setRecipientName("Joe Black");
    recipients.save(r);

    r = new Recipient();
    r.setRecipientName("Jane Doe");
    recipients.save(r);
    }
    @RequestMapping("/")
    public String index(Model model)
    {

        model.addAttribute("messages",messages.findAll());
        model.addAttribute("recipients",recipients.findAll());
        return "index";
    }

    @GetMapping("/addmessage")
    public String messageForm( Model model){

        model.addAttribute("message",new Message());
        model.addAttribute("recipientlist", recipients.findAll());
        return "message";
    }

    @PostMapping("/process")
    public String messageForm(@Valid Message message, BindingResult result)
    {
        if (result.hasErrors()){
            return "message";
        }
        messages.save(message);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String addLike (@PathVariable("id") long id, Model model){
        Like l = new Like();
        l.setMsg(messages.findById(id).get());
        likes.save(l);
       return "redirect:/";
    }
}
