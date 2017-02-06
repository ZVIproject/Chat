package com.zviproject.service;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.interfacee.IMessage;

@Service
public class MessageService {
	@Autowired
	IMessage iMessage;

	/**
	 * Method for sending message between users
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param login
	 * @return int
	 */
	public ReturnedId sendMessage(int senderId, int receiverId, String body) {

		return iMessage.saveMessage(senderId, receiverId, body);
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
		return iMessage.getInformation(senderId, receiverId);

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
		return iMessage.getFullInformation(senderId, receiverId, dc);
	}

	/**
	 * Update text of message by id in DB
	 * 
	 * @param idSender
	 * @param idMessage
	 * @param textMessage
	 * 
	 * @return ReturnedId
	 */
	public ReturnedId updateTextOfMessageById(int senderId, int messageId, String textMessage) {
		return iMessage.updateTextOfMessageById(senderId, messageId, textMessage);
	}

}
