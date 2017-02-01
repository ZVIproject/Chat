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
import com.zviproject.component.entity.Status;
import com.zviproject.component.interfacee.IMessage;

/**
 * 
 * @author zviproject
 *
 */
@Repository
public class MessageDao implements IMessage {

	/**
	 * Get short information about message
	 */
	private static final String SQL_INFORMATION_ABOUT_USERS = "SELECT m.body, m.send_time, m.id_message, s.access_token AS senderTok"
			+ " FROM messages AS m"
			+ " LEFT JOIN users AS r ON r.id_user=m.id_receiver LEFT JOIN users AS s ON s.id_user = m.id_sender"
			+ " WHERE (m.id_sender = :senderId AND m.id_receiver=:receiverId) OR (m.id_sender=:receiverId AND m.id_receiver=:senderId)";

	private static final String SQL_COUNT_USERS = "SELECT COUNT(id_user) FROM users WHERE users.id_user in (:senderId, :receiverId) LIMIT 2";

	@Autowired
	private HibernateUtil hibernateUtil;

	/**
	 * Printing information in stack trace
	 */
	private static Logger log = Logger.getLogger(MessageDao.class.getName());

	/**
	 * Method for sending message between users
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param login
	 * @return int
	 */
	@Override
	@Transactional
	public ReturnedId saveMessage(int senderId, int receiverId, String body) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		ReturnedId returnedId;

		try {

			if (!checkUser(senderId, receiverId)) {
				returnedId = new ReturnedId(0, Status.ERROR);
				return returnedId;
			}

			Message messageForSave = prepareMessageToSave(senderId, receiverId, body);

			session.save(messageForSave);

			returnedId = new ReturnedId(messageForSave.getIdMessage(), Status.SUCCESSFUL);

			log.info(String.format("Message have id ****** %d ******", messageForSave.getIdMessage()));
		} finally {
			session.close();
		}

		return returnedId;
	}

	private Message prepareMessageToSave(int senderId, int receiverId, String body) {
		Date sendTimeMessage = new Date();

		Message message = new Message();

		message.setBody(body);
		message.setIdSender(receiverId);
		message.setIdSender(senderId);
		message.setSendTime(sendTimeMessage);

		return message;
	}

	/**
	 * Get information about correspondence of user in room
	 * 
	 * @param id sender(user)
	 * @param id receiver(room)
	 * @return Collection<MessageToDisplay>
	 */
	@Override
	@Transactional
	public Collection<MessageToDisplay> getInformation(int senderId, int receiverId) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		try {
			if (!checkUser(senderId, receiverId)) {
				return null;
			}

			SQLQuery query = session.createSQLQuery(SQL_INFORMATION_ABOUT_USERS);
			query.setParameter("senderId", senderId);
			query.setParameter("receiverId", receiverId);

			query.addEntity(MessageToDisplay.class);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			Collection<MessageToDisplay> messageToDisplays = (List<MessageToDisplay>) query.list();
			return messageToDisplays;
		} finally {
			session.close();
		}

	}

	/**
	 * Get information about correspondence of user in room
	 * 
	 * @param id sender(user)
	 * @param id receiver(room)
	 * @return Collection<MessageToDisplay>
	 */
	@Override
	@Transactional
	public Collection<Message> getFullInformation(int senderId, int receiverId, DetachedCriteria detachedCriteria) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		if (!checkUser(senderId, receiverId)) {
			return null;
		}

		Collection<Message> messages = (List<Message>) detachedCriteria.getExecutableCriteria(session).list();
		return messages;
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
	@Override
	public ReturnedId updateTextOfMessageById(int idSender, int idMessage, String textMessage) {

		ReturnedId returnedId;

		if (!checkOneUser(idSender, idMessage)) {

			returnedId = new ReturnedId(idMessage, Status.ERROR);

			return returnedId;
		}

		returnedId = new ReturnedId(idMessage, Status.ERROR);

		Session session = hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Message updateTextOfMessage = (Message) session.get(Message.class, idMessage);
		updateTextOfMessage.setBody(textMessage);

		Date timeUpdate = new Date();
		updateTextOfMessage.setTimeStamp(timeUpdate);

		session.update(updateTextOfMessage);
		session.getTransaction().commit();

		returnedId.setStatus(Status.SUCCESSFUL);

		return returnedId;
	}

	/**
	 * Check element on presence in DB
	 * 
	 * @param idElement
	 */
	private boolean checkOneUser(int idUser, int idMessage) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		boolean exist = false;

		Criteria search = session.createCriteria(Message.class.getName()).add(Restrictions.eq("idMessage", idMessage))
				.setProjection(Projections.property("idSender"));

		int checkUserid = (int) search.uniqueResult();

		if (checkUserid == idUser) {
			exist = true;
		}

		return exist;
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
		try {
			SQLQuery query = session.createSQLQuery(SQL_COUNT_USERS);
			query.setParameter("senderId", senderId);
			query.setParameter("receiverId", receiverId);

			Integer count = ((BigInteger) query.uniqueResult()).intValue();
			if (count == 2) {
				exist = true;
			}

			return exist;

		} finally {
			session.close();
		}

	}

}
