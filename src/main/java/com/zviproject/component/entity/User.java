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
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Integer idUser;

	private String login;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "date_time")
	private Date dateTime;

	@Column(name = "time_stamp")
	private Date timeStamp;

	public Integer getidUser() {
		return idUser;
	}

	public void setidUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getaccessToken() {
		return accessToken;
	}

	public void setaccessToken(String accessToken) {
		this.accessToken = accessToken;
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
