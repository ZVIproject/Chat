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
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IChat;

/**
 * 
 * @author zviproject
 *
 */
@Repository
public class ChatDao implements IChat {

	/**
	 * Get short information about message
	 */
	private static final String SQL_INFORMATION_ABOUT_USERS = "SELECT m.body, m.send_time, m.id_message, s.access_token AS senderTok"
			+ " FROM messages AS m"
			+ " LEFT JOIN users AS r ON r.id_user=m.id_receiver LEFT JOIN users AS s ON s.id_user = m.id_sender"
			+ " WHERE (m.id_sender = :senderId AND m.id_receiver=:receiverId) OR (m.id_sender=:receiverId AND m.id_receiver=:senderId)";

	private static final String SQL_COUNT_USERS = "SELECT COUNT(id_user) FROM users WHERE users.id_user LIKE (:senderId) OR  users.id_user LIKE(:receiverId)";

	private ReturnedId returnedId = new ReturnedId();

	@Autowired
	private HibernateUtil hibernateUtil;

	/**
	 * Printing information in stack trace
	 */
	private static Logger log = Logger.getLogger(ChatDao.class.getName());

	/**
	 * Method for sending message between users
	 * 
	 * @param senderIdId
	 * @param receiverId
	 * @param login
	 * @return int
	 */
	@Override
	@Transactional
	public ReturnedId sendMessage(int senderId, int receiverId, String body) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		if (checkUser(senderId, receiverId)) {
			Date sendTimeMessage = new Date();

			Message message = new Message();

			message.setBody(body);
			message.setId_receiver(receiverId);
			message.setId_sender(senderId);
			message.setSend_time(sendTimeMessage);
			message.setDate_time(sendTimeMessage);
			session.save(message);

			returnedId.setId(message.getId_message());
			returnedId.setStatus("done");

			log.info(String.format("Message have id ****** %d ******", message.getId_message()));

			if (session.isOpen()) {
				session.close();
			}

			return returnedId;
		} else {
			returnedId.setStatus("error");
			;
			return returnedId;
		}

	}

	/**
	 * Get information about correspondence between users in pages
	 * 
	 * @param page
	 * @return Collection<MessageToDisplay>
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Collection<MessageToDisplay> getInformation(int senderId, int receiverId) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Collection<MessageToDisplay> messageToDisplays = null;
		if (checkUser(senderId, receiverId)) {

			SQLQuery query = session.createSQLQuery(SQL_INFORMATION_ABOUT_USERS);
			query.setParameter("senderId", senderId);
			query.setParameter("receiverId", receiverId);

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
	public ReturnedId registerUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		boolean accessToRegister = checkRegisterUser(user.getLogin());

		if (accessToRegister) {
			session.save(user);

			returnedId.setId(user.getId_user());
			returnedId.setStatus("done");

			if (session.isOpen()) {
				session.close();
			}
			log.info(String.format("New user hes id ****** %d ******", user.getId_user()));
			return returnedId;
		} else
			returnedId.setStatus("error");
		return returnedId;

	}

	/**
	 * Checking presence name user in a DB<br>
	 * if user not in a DB then this name can be registered
	 * 
	 * @param name
	 * @return exist
	 */
	private boolean checkRegisterUser(String login) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Criteria criteriaUser = session.createCriteria(User.class.getName()).add(Restrictions.eq("login", login))
				.setProjection(Projections.property("id"));
		return criteriaUser.uniqueResult() == null;
	}

	/**
	 * Get all information about correspondence between users
	 * 
	 * @param dc
	 *            - get information about message
	 * @param senderId
	 * @param receiverId
	 * @return Collection<Message>
	 */
	@Override
	@Transactional
	public Collection<Message> getFullInformation(int senderId, int receiverId, DetachedCriteria detachedCriteria) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		Collection<Message> messages = null;

		if (checkUser(senderId, receiverId)) {
			messages = (List<Message>) detachedCriteria.getExecutableCriteria(session).list();
		}
		return messages;
	}

	/**
	 * Update token for user by id
	 */
	@Override
	@Transactional
	public void updateToken(String access_token, int id_user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		User userUpdate = (User) session.get(User.class, id_user);
		userUpdate.setAccess_token(access_token);
		session.beginTransaction();
		session.update(userUpdate);
		session.getTransaction().commit();
		if (session.isOpen()) {
			session.close();
		}

	}

	/**
	 * Checking presence users in DB
	 * 
	 * @param receiverId
	 * @return status
	 */
	private boolean checkUser(int senderId, int receiverId) {
		boolean exist = false;
		Session session = hibernateUtil.getSessionFactory().openSession();

		SQLQuery query = session.createSQLQuery(SQL_COUNT_USERS);
		query.setParameter("senderId", senderId);
		query.setParameter("receiverId", receiverId);

		Integer count = ((BigInteger) query.uniqueResult()).intValue();
		if (count == 2) {
			exist = true;
		}
		session.close();

		return exist;
	}
}
