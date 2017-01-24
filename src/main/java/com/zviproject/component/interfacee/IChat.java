package com.zviproject.component.interfacee;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.zviproject.component.entity.Message;

public interface IChat {
	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param name2
	 * @return Collection<Message>
	 */
	public Collection<Message> sendMessage(String name1, String name2, DetachedCriteria dc);

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	public Collection<Message> getInformation(String name1, String name2, int page);

}
