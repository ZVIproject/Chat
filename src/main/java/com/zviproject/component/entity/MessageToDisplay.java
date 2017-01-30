package com.zviproject.component.entity;

import java.sql.Date;

import javax.inject.Inject;
import javax.persistence.Column;
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
		@EntityResult(entityClass = MessageToDisplay.class, fields = { @FieldResult(name = "id", column = "id_message"),
				@FieldResult(name = "time", column = "send_time"),
				@FieldResult(name = "senderTok", column = "senderTok"),
				@FieldResult(name = "textMessage", column = "body") }) })

public class MessageToDisplay {

	@Id
	@Column(name = "id_message")
	private int id;

	@Column(name = "body")
	private String textMessage;

	private String senderTok;

	@Column(name = "send_time")
	private Date time;

	/**
	 * Working with FaceBook API
	 */
	@Inject
	public void FacebookController(Facebook facebook) {

	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setToken(String senderTok) {
		this.senderTok = senderTok;
	}

	public String getToken() {
		Facebook facebook;
		facebook = new FacebookTemplate(senderTok, "me");
		User me = facebook.userOperations().getUserProfile();
		String name = me.getName();

		return name;
	}

}
