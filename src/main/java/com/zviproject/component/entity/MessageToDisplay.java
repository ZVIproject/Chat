package com.zviproject.component.entity;

import java.sql.Date;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

@Entity

/**
 * Creating mapping for query about message information
 * 
 * @author zviproject
 *
 */
@SqlResultSetMapping(name = "MessageToDisplay", entities = {
		@EntityResult(entityClass = MessageToDisplay.class, fields = { @FieldResult(name = "id", column = "id"),
				@FieldResult(name = "time", column = "time"), @FieldResult(name = "senderTok", column = "senderTok"),
				@FieldResult(name = "text", column = "text") }) })

public class MessageToDisplay {

	@Id
	private int id;

	private String text;
	private String senderTok;
	private Date time;

	/**
	 * Working with FaceBook API
	 */
	@Inject
	public void FacebookController(Facebook facebook) {

	}

	public String getTextMessage() {
		return text;
	}

	public String getToken() {
		Facebook facebook;
		facebook = new FacebookTemplate(senderTok, "me");
		User me = facebook.userOperations().getUserProfile();
		String name = me.getName();

		return name;
	}

	public void setToken(String token) {
		this.senderTok = token;
	}

	public void setTextMessage(String textMessage) {
		this.text = textMessage;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
