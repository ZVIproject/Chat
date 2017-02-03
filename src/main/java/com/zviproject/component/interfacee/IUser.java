package com.zviproject.component.interfacee;

import java.util.List;
import java.util.Set;

import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.User;

public interface IUser {
	/**
	 * Register new user in DB
	 * 
	 * @param user
	 * @return int
	 */
	public ReturnedId registerUser(User user);

	/**
	 * Update token for user
	 * 
	 * @param token
	 */
	public ReturnedId updateToken(String token, int id);
	/**
	 * Show rooms of user
	 * 
	 * @param id
	 * @return Set<Room>
	 */
	public Set<Room> getUserRooms(Integer userId);

}
