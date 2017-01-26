package com.zviproject.service;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IChat;

@Service
public class ChatService {
	@Autowired
	IChat iChat;

	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param name2
	 * @return Collection<Message>
	 */
	public Collection<Message> sendMessage(int sender, int receiver, DetachedCriteria dc, String text) {

		return iChat.sendMessage(sender, receiver, dc, text);
	}

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	public Collection<Message> getInformation(int sender, int receiver, DetachedCriteria dc) {
		return iChat.getInformation(sender, receiver, dc);

	}

	/**
	 * Register new user in chat<br>
	 * Return information for user and <b>ID</b>.
	 * @param user
	 * @return String
	 */
	public int registerUser(User user) {
		return iChat.registerUser(user);
	}

}
