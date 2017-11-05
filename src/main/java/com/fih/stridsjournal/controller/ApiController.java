package com.fih.stridsjournal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fih.stridsjournal.dao.MessageRepository;
import com.fih.stridsjournal.model.Message;


@RestController
@RequestMapping("/api")
public class ApiController {
	
	
	@Autowired
	MessageRepository service;
	
	@PostMapping(value = "/message")
	public Message saveMessage(@RequestBody Message saveMessage){
		return service.save(saveMessage);
	}
	
	@GetMapping(value = "/message")
	public Collection<Message> getMessage(@RequestParam(value="after", required=false) String after, @RequestParam(value="to", required=false) String to) throws ParseException{
		Date af = new Date();
		if(after != null){
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");
			 af = formatter.parse(after);
		}
		if(after == null && to == null){
			return (List<Message>) service.findAll();
		}
		if(after != null && to == null){
			
			return (Set<Message> ) service.findBySavedAtAfter(af);
		}
		if(after == null && to != null){
			return (Set<Message>) service.findBySentTo(to);
		}
		if(after != null && to != null){
			
			return (Set<Message>) service.findBySentToAndSavedAtAfter(to, af);
		}
		return null;
	}
	

}
