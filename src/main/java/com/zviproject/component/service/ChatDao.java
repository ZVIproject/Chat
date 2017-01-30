package com.zviproject.component.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.Util.HibernateUtil;
import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IChat;

@Repository
public class ChatDao implements IChat {

	@Autowired
	private HibernateUtil hibernateUtil;

	/**
	 * Printing information in stack trace
	 */
	private static Logger log = Logger.getLogger(ChatDao.class.getName());

	/**
	 * Checking presence name user in a DB<br>
	 * if user not in a DB then this name can be registered
	 * 
	 * @param name
	 * @return exist
	 */
	public boolean registerUser(String name) {
		boolean exist = false;
		Session session = hibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteriaUser = session.createCriteria(User.class.getName()).add(Restrictions.eq("name", name))
					.setProjection(Projections.property("id"));
			int idUser = (int) criteriaUser.uniqueResult();
			log.info(String.format("******* The user with this name * %s * in a DB and can not be registered *******",
					name));
		} catch (NullPointerException e) {
			exist = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exist;
	}

	/**
	 * Checking presence users in DB
	 * 
	 * @param receiver
	 * @return status
	 */
	public boolean checkUser(int sender, int receiver) {
		boolean exist = false;
		Session session = hibernateUtil.getSessionFactory().openSession();
		try {

			final String SQL = "SELECT count(id) from Users where Users.id like (:sender) or  Users.id like(:receiver)";

			SQLQuery query = session.createSQLQuery(SQL);
			query.setParameter("sender", sender);
			query.setParameter("receiver", receiver);

			Integer count = ((BigInteger) query.uniqueResult()).intValue();
			if (count == 2) {
				exist = true;
			}
			session.close();
		} catch (Exception e) {
			log.info("******* Problem with checking user *******");
		}

		return exist;
	}

	/**
	 * Method for sending message between users
	 * 
	 * @param sender
	 * @param receiver
	 * @param text
	 * @return int
	 */
	@Override
	@Transactional
	public int sendMessage(int sender, int receiver, String text) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		if (checkUser(sender, receiver)) {
			Date timeMessage = new Date();

			Message message = new Message();

			message.setReceiver(receiver);
			message.setSender(sender);
			message.setText(text);
			message.setTime(timeMessage);
			session.save(message);

			int idMessage = message.getId();

			log.info(String.format("Message have id ****** %d ******", idMessage));

			if (session.isOpen()) {
				session.close();
			}

			return idMessage;
		} else {
			return 0;
		}

	}

	/**
	 * Get information about correspondence between users in pages
	 * 
	 * @param page
	 * @return Collection<MessageToDisplay>
	 */
	@Override
	@Transactional
	public Collection<MessageToDisplay> getInformation(int sender, int receiver) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Collection<MessageToDisplay> messageToDisplays = null;
		if (checkUser(sender, receiver)) {
			final String SQL = "SELECT m.text, m.time, m.id, s.access_token AS senderTok" + " FROM Messages AS m"
					+ " LEFT JOIN Users AS r ON r.id=m.receiver LEFT JOIN Users AS s ON s.id = m.sender"
					+ " WHERE (m.sender = :sender AND m.receiver= :receiver) OR (m.sender=:receiver AND m.receiver= :sender)";

			SQLQuery query = session.createSQLQuery(SQL);
			query.setParameter("sender", sender);
			query.setParameter("receiver", receiver);

			query.addEntity(MessageToDisplay.class);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			messageToDisplays = (List<MessageToDisplay>) query.list();

		}

		return messageToDisplays;

	}

	/**
	 * Register new user in chat<br>
	 * Return information for user.
	 * 
	 * @param user
	 * @return String
	 */
	@Override
	@Transactional
	public int registerUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		boolean accessReg = registerUser(user.getName());
		if (accessReg) {
			session.save(user);

			int id = user.getId();

			if (session.isOpen()) {
				session.close();
			}
			log.info(String.format("New user hes id ****** %d ******", id));
			return id;
		} else
			return 0;

	}

	/**
	 * Get all information about correspondence between users
	 * 
	 * @param dc
	 * @param sender
	 * @param receiver
	 * @return Collection<Message>
	 */
	@Override
	@Transactional
	public Collection<Message> getFullInformation(int sender, int receiver, DetachedCriteria dc) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		Collection<Message> messages = null;

		if (checkUser(sender, receiver)) {
			messages = (List<Message>) dc.getExecutableCriteria(session).list();
		}
		return messages;
	}

}
