package com.zviproject.component.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="rooms")
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_room")
	private Integer idRoom;
	
	private String roomName;
	
	@ManyToMany
	@JoinTable(name="users_in_rooms",
    joinColumns = @JoinColumn(name="id_room", referencedColumnName="id"),
    inverseJoinColumns = @JoinColumn(name="id_user", referencedColumnName="id"))
	private Set<User> usersInRoom;
	
	public Room(){
		
	}

	public Room(Integer idRoom, String roomName, Set<User> usersInRoom) {
		super();
		this.idRoom = idRoom;
		this.roomName = roomName;
		this.usersInRoom = usersInRoom;
	}

	public Integer getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Set<User> getUsersInRoom() {
		return usersInRoom;
	}

	public void setUsersInRoom(Set<User> usersInRoom) {
		this.usersInRoom = usersInRoom;
	}
}
