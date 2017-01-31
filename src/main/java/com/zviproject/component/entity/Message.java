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
	@Column(name = "")
	private Integer idMessage;

	@Column(name = "id_sender")
	private int idSender;

	@Column(name = "id_receiver")
	private int idReceiver;

	@Column(name = "send_time")
	private Date sendTime;

	private String body;

	@Column(name = "date_time")
	private Date dateTime;

	@Column(name = "time_stamp")
	private Date timeStamp;

	public Integer getidMessage() {
		return idMessage;
	}

	public void setidMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

	public int getidSender() {
		return idSender;
	}

	public void setidSender(int idSender) {
		this.idSender = idSender;
	}

	public int getidReceiver() {
		return idReceiver;
	}

	public void setidReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}

	public Date getsendTime() {
		return sendTime;
	}

	public void setsendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getdateTime() {
		return dateTime;
	}

	public void setdateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date gettimeStamp() {
		return timeStamp;
	}

	public void settimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
