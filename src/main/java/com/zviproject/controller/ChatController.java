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
	public DetachedCriteria createDetachedCriteria(int name1, int name2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class)
				.addOrder(Order.asc("time"))
				.add(Restrictions.or(
						Restrictions.and(
								Restrictions.eq("sender", name1), 
								Restrictions.eq("receiver", name2)), 
						Restrictions.and(
								Restrictions.eq("sender", name2), 
								Restrictions.eq("receiver", name1))));
		return detachedCriteria;
	}
	

	/**
	 * Method for sending messages between users
	 * 
	 * @param sender
	 * @param receiver
	 * @return Collection<Message>
	 */
	@RequestMapping(value = "/{sender}/{receiver}", method = RequestMethod.POST)
	public Collection<Message> sendMessage(@PathVariable("sender") int sender, @PathVariable("receiver") int receiver, @RequestBody String text) {
		DetachedCriteria dc = createDetachedCriteria(sender, receiver);
		return chatService.sendMessage(sender, receiver, dc, text);
	}

	/**
	 * Register new user in chat<br>
	 * Return information for user.
	 * @param user
	 * @return String
	 */
	@RequestMapping(value = "/register", consumes = "application/json", method = RequestMethod.POST)
	public String registerUser(@RequestBody User user) {
		
		return "Your id in this chat is ****** "+chatService.registerUser(user)+
				" ******\n you can send message by using your id and after /(slash) id person that must receive this message";
		
	}

	/**
	 * Get information about correspondence between users in pages 
	 * 
	 * @param page
	 * @return Collection<Message>
	 */
	@RequestMapping(value = "/{sender}/{receiver}/information", method = RequestMethod.GET)
	public Collection<Message> getInformation(@PathVariable("sender") int sender, @PathVariable("receiver") int receiver) {
		DetachedCriteria dc=createDetachedCriteria(sender, receiver);
		return chatService.getInformation(sender, receiver, dc);
	}

}
