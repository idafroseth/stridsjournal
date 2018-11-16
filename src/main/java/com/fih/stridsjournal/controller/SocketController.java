package com.fih.stridsjournal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fih.stridsjournal.dao.MessageRepository;
import com.fih.stridsjournal.model.Message;

@Controller
public class SocketController {

	@Autowired
	MessageRepository service;
	
    @MessageMapping("/save")
    @SendTo("/topic/messages")
    public Message greeting(Message message, Principal principal) throws Exception {
    		System.out.println("Trying to save message "+ message.getMessage() + " by "+ principal);
	    	if(principal == null){
				throw new NotAllowedException();
		}
    		message.setSentBy(principal.getName());    
    		return service.save(message);
    }
   
}
