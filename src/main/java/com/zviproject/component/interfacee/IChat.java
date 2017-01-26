package com.zviproject.component.interfacee;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.User;

public interface IChat {
	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param name2
	 * @return Collection<Message>
	 */
	public Collection<Message> sendMessage(int sender, int receiver, DetachedCriteria dc, String text);

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	public Collection<Message> getInformation(int sender, int receiver, DetachedCriteria dc);

	/**
	 * Register new user in DB
	 * 
	 * @param user
	 * @return int
	 */
	public int registerUser(User user);

}
