package com.zviproject.component.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer idUser;

	private String login;

	@Column(name = "access_token")
	private String accessToken;

	
	@ManyToMany(mappedBy = "users_in_rooms")
	private Set<Room> userRooms;

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

	public Set<Room> getUserRooms() {
		return userRooms;
	}

	public void setUserRooms(Set<Room> userRooms) {
		this.userRooms = userRooms;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
