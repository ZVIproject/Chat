package com.zviproject.component.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity

/**
 * Creating mapping for query about message information
 * 
 * @author zviproject
 *
 */
@SqlResultSetMapping(name = "MessageToDisplay", entities = {
		@EntityResult(entityClass = MessageToDisplay.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "time", column = "time"),
				@FieldResult(name = "senderTok", column = "senderTok"),
				@FieldResult(name = "text", column = "text") }) })

public class MessageToDisplay {

	@Id
	private int id;

	private String text;
	private String senderTok;
	private Date time;

	public String getTextMessage() {
		return text;
	}

	public String getToken() {
		return senderTok;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
