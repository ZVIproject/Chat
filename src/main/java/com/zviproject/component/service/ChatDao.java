package com.zviproject.component.service;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
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
	public Collection<Message> sendMessage(int sender, int receiver, DetachedCriteria dc, String text) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		Date timeMessage = new Date();

		Message message = new Message();

		message.setReceiver(receiver);
		message.setSender(sender);
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
	 * Get information about correspondence between users in pages 
	 * @param page
	 * @return Collection<Message>
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Collection<Message> getInformation(int sender, int receiver, DetachedCriteria dc) {
		Session session=hibernateUtil.getSessionFactory().openSession();
		Collection<Message> messages = (Collection<Message>) dc.getExecutableCriteria(session).list();

		return messages;
	}

	/**
	 * Register new user in chat<br>
	 * Return information for user.
	 * @param user
	 * @return String
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int registerUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		session.save(user);
		Criteria userId=session.createCriteria(User.class.getName())
				.add(Restrictions.eq("name", user.getName()))
				.setProjection(Projections.property("id"));
		
		int id=(int) userId.uniqueResult();
		
		if (session.isOpen()) {
			session.close();
		}
		
		return id;

	}

}
