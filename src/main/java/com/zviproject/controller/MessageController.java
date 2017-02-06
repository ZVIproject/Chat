package com.zviproject.controller;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.service.MessageService;

@Configuration
@RestController
@RequestMapping("/chat")
public class MessageController {

	@Autowired
	MessageService messageService;

	/**
	 * Request for return history of message
	 * 
	 * @param id sender
	 * @param id room
	 * @return DetachedCriteria
	 */
	public DetachedCriteria createDetachedCriteria(int senderId, int receiverId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class).addOrder(Order.asc("sendTime"))
				.add(Restrictions.or(
						Restrictions.and(Restrictions.eq("idSender", senderId),
								Restrictions.eq("idReceiver", receiverId)),
						Restrictions.and(Restrictions.eq("idSender", receiverId),
								Restrictions.eq("idReceiver", senderId))));
		return detachedCriteria;
	}

	/**
	 * Method for sending messages
	 * 
	 * @param senderId
	 * @param receiverId
	 * @return int
	 */
	@RequestMapping(value = "/{sender_id}/{receiver_id}", method = RequestMethod.POST)
	public ReturnedId sendMessage(@PathVariable("sender_id") int senderId, @PathVariable("receiver_id") int receiverId,
			@RequestBody String body) {
		return messageService.sendMessage(senderId, receiverId, body);
	}

	/**
	 * Get information about correspondence of user in room by pages
	 * 
	 * @param senderId
	 * @param receiverId
	 * @return Collection<MessageToDisplay>
	 */
	@RequestMapping(value = "/{senderId}/{receiverId}/information", method = RequestMethod.GET)
	public Collection<MessageToDisplay> getInformation(@PathVariable("senderId") int senderId,
			@PathVariable("receiverId") int receiverId) {

		return messageService.getInformation(senderId, receiverId);
	}

	/**
	 * Get all information about correspondence between users
	 * 
	 * @param dc
	 * @param senderId
	 * @param receiverId
	 * @return Collection<Message>
	 */
	@RequestMapping(value = "/{senderId}/{receiverId}/informationFull", method = RequestMethod.GET)
	public Collection<Message> getFullInformation(@PathVariable("senderId") int senderId,
			@PathVariable("receiverId") int receiverId) {
		DetachedCriteria dc = createDetachedCriteria(senderId, receiverId);
		return messageService.getFullInformation(senderId, receiverId, dc);
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
	@RequestMapping(value = "/update_message/{sender_id}/{id}", method = RequestMethod.PUT)
	public ReturnedId updateTextOfMessageById(@PathVariable("sender_id") int idSender,
			@PathVariable("id") int idMessage, @RequestBody String textMessage) {
		return messageService.updateTextOfMessageById(idSender, idMessage, textMessage);
	}

}
