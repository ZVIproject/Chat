package com.zviproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IRoom;

/**
 *Service for room interface  
 */

@Service
public class RoomService {
	
	@Autowired
	private IRoom iRoom;
	
	/**
	 * Register new room in room<br>
	 * Return information for room and <b>ID</b>.
	 * 
	 * @param room
	 * @return String
	 */
	public ReturnedId registerRoom(Room room) {
		return iRoom.registerRoom(room);
	}
	
	/**
	 * Return all users in the room by room id
	 * 
	 * @param id
	 * @return List<User>
	 */
	public List<User> getUsersInRoom(Integer id){
		return iRoom.getUsersInRoom(id);		
	}

}