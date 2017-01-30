package com.zviproject.service;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IChat;

@Service
public class ChatService {
	@Autowired
	IChat iChat;

	/**
	 * Method for sending message between users
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param login
	 * @return int
	 */
	public ReturnedId sendMessage(int senderId, int receiverId, String login) {

		return iChat.sendMessage(senderId, receiverId, login);
	}

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param dc
	 * @param senderId
	 * @param receiverId
	 * @return Collection<Message>
	 */
	public Collection<MessageToDisplay> getInformation(int senderId, int receiverId) {
		return iChat.getInformation(senderId, receiverId);

	}

	/**
	 * Register new user in chat<br>
	 * Return information for user and <b>ID</b>.
	 * 
	 * @param user
	 * @return String
	 */
	public ReturnedId registerUser(User user) {
		return iChat.registerUser(user);
	}

	/**
	 * Get all information about correspondence between users
	 * 
	 * @param dc
	 * @param senderId
	 * @param receiverId
	 * @return Collection<Message>
	 * @throws Exception
	 */
	public Collection<Message> getFullInformation(int senderId, int receiverId, DetachedCriteria dc) {
		return iChat.getFullInformation(senderId, receiverId, dc);
	}

	/**
	 * Update token for user
	 * 
	 * @param token
	 */
	public void updateToken(String token, int id) {
		iChat.updateToken(token, id);
	}

}
