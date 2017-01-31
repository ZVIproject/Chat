package com.zviproject.component.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_message")
	private Integer idMessage;

	private int id_sender;

	private int id_receiver;

	private Date send_time;

	private String body;

	private Date date_time;

	private Date time_stamp;

	public Integer getidMessage() {
		return idMessage;
	}

	public void setidMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

	public int getid_sender() {
		return id_sender;
	}

	public void setid_sender(int id_sender) {
		this.id_sender = id_sender;
	}

	public int getid_receiver() {
		return id_receiver;
	}

	public void setid_receiver(int id_receiver) {
		this.id_receiver = id_receiver;
	}

	public Date getsend_time() {
		return send_time;
	}

	public void setsend_time(Date send_time) {
		this.send_time = send_time;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getdate_time() {
		return date_time;
	}

	public void setdate_time(Date date_time) {
		this.date_time = date_time;
	}

	public Date gettime_stamp() {
		return time_stamp;
	}

	public void settime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
