package com.zviproject.component.interfacee;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.User;

public interface IChat {
	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param reciver
	 * @return int
	 */
	public ReturnedId sendMessage(int sender, int reciver, String text);

	/**
	 * Get all information about correspondence between users
	 * 
	 * @param dc
	 *            query for gettng
	 * @param sender
	 * @param reciver
	 * @return Collection<Message>
	 */
	public Collection<Message> getFullInformation(int sender, int reciver, DetachedCriteria dc);

	/**
	 * Get information about correspondence between users
	 * 
	 * @param sender
	 * @param reciver
	 * @return Collection<Message>
	 */
	public Collection<MessageToDisplay> getInformation(int sender, int reciver);

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
	public void updateToken(String token, int id);
}
