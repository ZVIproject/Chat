package com.zviproject.component.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.zviproject.component.entity.Message;
import com.zviproject.component.interfacee.IChat;

@Repository
public class ChatDao implements IChat {

	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param name2
	 * @return Collection<Message>
	 */
	@Override
	public Collection<Message> sendMessage(String name1, String name2) {

		Collection<Message> messages = new ArrayList<>();
		Message message = new Message();

		return messages;
	}

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	@Override

	public Collection<Message> getInformation(String name1, String name2, int page) {

		Collection<Message> messages = new ArrayList<>();

		Message message = new Message();

		return messages;
	}

}
