package com.zviproject.service;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.component.entity.Message;
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
	public Collection<Message> sendMessage(String name1, String name2, DetachedCriteria dc, String text) {

		return iChat.sendMessage(name1, name2, dc, text);
	}

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	public Collection<Message> getInformation(String name1, String name2, int page) {
		return iChat.getInformation(name1, name2, page);

	}

}
