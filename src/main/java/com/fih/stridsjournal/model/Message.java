package com.fih.stridsjournal.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * This is a comment to enable push
 * @author Zelus
 *
 */

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String sentBy;
	@Column(name = "message", length = 4096)
	private String message;
	private String sentTo;
	private boolean requiresAction = false;
	
	private Date savedAt = new Date();
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
