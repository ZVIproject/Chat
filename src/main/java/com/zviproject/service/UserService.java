package com.zviproject.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IUser;

@Service
public class UserService {

	@Autowired
	IUser iUser;

	/**
	 * Register new user in chat<br>
	 * Return information for user and <b>ID</b>.
	 * 
	 * @param user
	 * @return String
	 */
	public ReturnedId registerUser(User user) {
		return iUser.registerUser(user);
	}

	/**
	 * Update token for user
	 * 
	 * @param token
	 */
	public ReturnedId updateToken(String token, int id) {
		return iUser.updateToken(token, id);
	}
	/**
	 * Return all users in the room by room id
	 * 
	 * @param id
	 * @return Set<Room>
	 */
	public Set<Room> getUsersInRoom(Integer id){
		return iUser.getUserRooms(id);	
	}
}
