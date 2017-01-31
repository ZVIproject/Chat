package com.zviproject.controller;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.User;
import com.zviproject.service.ChatService;

@Configuration
@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	ChatService chatService;

	/**
	 * Request for return history of message
	 * 
	 * @param name1
	 * @param name2
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
	 * Method for sending messages between users
	 * 
	 * @param senderId
	 * @param receiverId
	 * @return int
	 */
	@RequestMapping(value = "/{senderId}/{receiverId}", method = RequestMethod.POST)
	public ReturnedId sendMessage(@PathVariable("senderId") int senderId, @PathVariable("receiverId") int receiverId,
			@RequestBody String login) {
		return chatService.sendMessage(senderId, receiverId, login);
	}

	/**
	 * Register new user in chat<br>
	 * Return information for user.
	 * 
	 * @param user
	 * @return String
	 */
	@RequestMapping(value = "/register", consumes = "application/json", method = RequestMethod.POST)
	public ReturnedId registerUser(@RequestBody User user) {

		return chatService.registerUser(user);

	}

	/**
	 * Get information about correspondence between users in pages
	 * 
	 * @param page
	 * @return Collection<MessageToDisplay>
	 */
	@RequestMapping(value = "/{senderId}/{receiverId}/information", method = RequestMethod.GET)
	public Collection<MessageToDisplay> getInformation(@PathVariable("senderId") int senderId,
			@PathVariable("receiverId") int receiverId) {

		return chatService.getInformation(senderId, receiverId);
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
		return chatService.getFullInformation(senderId, receiverId, dc);
	}

	/**
	 * Update token for user
	 * 
	 * @param token
	 */
	@RequestMapping(value = "/update/{idUser}", method = RequestMethod.PUT)
	public ReturnedId updateToken(@RequestHeader(value = "access_token") String access_token,
			@PathVariable("idUser") int id) {
		return chatService.updateToken(access_token, id);
	}

}
