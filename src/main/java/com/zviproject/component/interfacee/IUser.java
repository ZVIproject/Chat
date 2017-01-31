package com.zviproject.component.interfacee;

import com.zviproject.component.entity.ReturnedId;
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

}
