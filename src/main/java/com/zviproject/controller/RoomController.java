package com.zviproject.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.User;
import com.zviproject.service.RoomService;

/**
 *Controller for Chat with rooms  
 */


@Configuration
@RestController
@RequestMapping("/roomChat")

public class RoomController {

		@Autowired
		RoomService roomService;

		/**
		 * Register new room in chat<br>
		 * Return information for room.
		 * 
		 * @param room
		 * @return String
		 */
		@RequestMapping(value = "/register", consumes = "application/json", method = RequestMethod.POST)
		public ReturnedId registerRoom(@RequestBody Room room) {

			return roomService.registerRoom(room);

		}
		
		/**
		 * Return all users in the room.
		 * 
		 * @param id
		 * @return List<User>
		 */

		@RequestMapping(value = "/{id}/usersInRoom", method = RequestMethod.GET)
		public List<User> getUsersInRoom(@PathVariable("id") Integer id) {
			return roomService.getUsersInRoom(id);

		}		
}

