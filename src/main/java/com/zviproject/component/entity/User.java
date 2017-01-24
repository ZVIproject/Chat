package com.zviproject.component.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String access_token;
	private String name;

	@OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Message> messageForSender;

	@OneToMany(mappedBy = "receiver", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Message> messageForReceiver;

	public Set<Message> getMessageForSender() {
		return messageForSender;
	}

	public void setMessageForSender(Set<Message> messageForSender) {
		this.messageForSender = messageForSender;
	}

	public Set<Message> getMessageForReceiver() {
		return messageForReceiver;
	}

	public void setMessageForReceiver(Set<Message> messageForReceiver) {
		this.messageForReceiver = messageForReceiver;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
