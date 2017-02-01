package com.zviproject.component.interfacee;

import java.util.List;

import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.User;

/**
 *Interface for Room entity  
 */

public interface IRoom {
		
		/**
		 * Register new room in DB
		 * 
		 * @param room
		 * @return int
		 */
		public ReturnedId registerRoom(Room room);
		
		/**
		 * Return all users in the room by room id
		 * 
		 * @param id
		 * @return List<User>
		 */
		public List<User> getUsersInRoom(Integer id);

}