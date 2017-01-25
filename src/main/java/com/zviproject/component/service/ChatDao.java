package com.zviproject.component.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.Util.HibernateUtil;
import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IChat;

@Repository
public class ChatDao implements IChat {

	@Autowired
	private HibernateUtil hibernateUtil;

	public DetachedCriteria searchUser(String userName) {
		return null;
	}

	/**
	 * Method for sending message between users
	 * 
	 * @param name1
	 * @param name2
	 * @param dc
	 * @param text
	 * @return Collection<Message>
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Collection<Message> sendMessage(String name1, String name2, DetachedCriteria dc, String text) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		Criteria sender = session.createCriteria(User.class).add(Restrictions.eq("name", name1));

		User userSender = (User) sender.uniqueResult();

		Criteria receiver = session.createCriteria(User.class).add(Restrictions.eq("name", name2));

		User userReceiver = (User) receiver.uniqueResult();

		Date timeMessage = new Date();

		Message message = new Message();

		message.setReceiver(userReceiver);
		message.setSender(userSender);
		message.setText(text);
		message.setTime(timeMessage);
		session.save(message);
		Collection<Message> messages = (Collection<Message>) dc.getExecutableCriteria(session).list();
		if (session.isOpen()) {
			session.close();
		}

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
	@Transactional(rollbackFor = Exception.class)
	public Collection<Message> getInformation(String name1, String name2, int page) {

		Collection<Message> messages = new ArrayList<>();

		Message message = new Message();

		return messages;
	}

}
