package com.zviproject.component.service;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.Util.HibernateUtil;
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
	@Transactional(rollbackFor = Exception.class)
	public Collection<Message> sendMessage(String name1, String name2, DetachedCriteria dc) {
		Session session = HibernateUtil.getSessionFactory().openSession();
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
