package com.zviproject.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.zviproject.component.entity.Message;

@Service
public class ChatService {

	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param name2
	 * @return Collection<Message>
	 */
	public Collection<Message> sendMessage(String name1, String name2) {
		return null;
	}

	/**
	 * Get information about correspondence between users in pages Every page
	 * have 10 message
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	public Collection<Message> getInformation(String name1, String name2, int page) {
		return null;

	}

}
