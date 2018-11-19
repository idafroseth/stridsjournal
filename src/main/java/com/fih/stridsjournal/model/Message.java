package com.fih.stridsjournal.model;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Message {
	@Id
	private UUID id;
	
	private String sentBy;
	@Column(name = "message", length = 4096)
	private String message;
	private String sentTo;
	private boolean requiresAction = false;

	
	private Date savedAt = new Date();
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSentBy() {
		return sentBy;
	}

	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSentTo() {
		return sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	public Date getSavedAt() {
		return savedAt;
	}

	public void setSavedAt(Date savedAt) {
		this.savedAt = savedAt;
	}

	public boolean isRequiresAction() {
		return requiresAction;
	}

	public void setRequiresAction(boolean requiresAction) {
		this.requiresAction = requiresAction;
	}
	
}
