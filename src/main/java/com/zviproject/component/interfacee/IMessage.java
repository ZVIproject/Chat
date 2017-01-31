package com.zviproject.component.interfacee;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;

public interface IMessage {
	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param reciver
	 * @return int
	 */
	public ReturnedId saveMessage(int sender, int reciver, String text);

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
	 * Update text of message by id in DB
	 * 
	 * @param idSender
	 * @param idMessage
	 * @param textMessage
	 * 
	 * @return ReturnedId
	 */
	public ReturnedId updateTextOfMessageById(int idSender, int idMessage, String textMessage);

}
