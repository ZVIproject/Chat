package com.zviproject.component.service;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.entity.Message;
import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.Status;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IMessage;

/**
 * 
 * @author zviproject
 *
 */
@Repository
@Qualifier
public class MessageDao extends HibernateUtil implements IMessage {

	/**
	 * Get short information about message
	 */
	private static final String SQL_INFORMATION_ABOUT_USERS = "SELECT m.body, m.send_time, m.id, s.access_token AS senderTok"
			+ " FROM messages AS m"
			+ " LEFT JOIN rooms AS r ON r.id=m.receiver_id LEFT JOIN users AS s ON s.id= m.sender_id"
			+ " WHERE (m.sender_id = :senderId AND m.receiver_id=:receiverId)";

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
		ReturnedId returnedId;

		try(Session session = getSessionFactory().openSession()) {

			if (!checkUser(senderId, receiverId)) {
				return new ReturnedId(0, Status.ERROR);
			}

			Message messageForSave = prepareMessageToSave(senderId, receiverId, body);
			session.save(messageForSave);

			returnedId = new ReturnedId(messageForSave.getIdMessage(), Status.SUCCESSFUL);

			log.info(String.format("Message have id ****** %d ******", messageForSave.getIdMessage()));
		}

		return returnedId;
	}

	private Message prepareMessageToSave(int senderId, int receiverId, String body) {
		Message message = new Message();

		message.setBody(body);
		message.setReceiverId(receiverId);
		message.setSenderId(senderId);


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

		try (Session session = getSessionFactory().openSession()) {
			if (!checkUser(senderId, receiverId)) {
				return null;
			}

			SQLQuery query = session.createSQLQuery(SQL_INFORMATION_ABOUT_USERS);
			query.setParameter("senderId", senderId);
			query.setParameter("receiverId", receiverId);

			query.addEntity(MessageToDisplay.class);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			return (List) query.list();
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
		Session session = getSessionFactory().openSession();
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
			return returnedId = new ReturnedId(idMessage, Status.ERROR);
		}

		returnedId = new ReturnedId(idMessage, Status.ERROR);

		Session session = getSessionFactory().openSession();
		session.beginTransaction();

		Message updateTextOfMessage = (Message) session.get(Message.class, idMessage);
		updateTextOfMessage.setBody(textMessage);

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
		try(Session session = getSessionFactory().openSession()){
		Criteria search = session.createCriteria(Message.class.getName()).add(Restrictions.eq("id", idMessage))
				.setProjection(Projections.property("sender_id"));
		return (int) search.uniqueResult()==idUser;

		}
	}

	/**
	 * Checking presence users in DB
	 * 
	 * @param receiverId
	 * @return status
	 */
	private boolean checkUser(int senderId, int receiverId) {
		try(Session session = getSessionFactory().openSession()) {
			Criteria userCriteria = session.createCriteria(User.class).add(Restrictions.eq("id", senderId));
			Criteria roomCriteria = session.createCriteria(Room.class).add(Restrictions.eq("id", receiverId));
			return ((userCriteria.uniqueResult()!=null)&&(roomCriteria.uniqueResult()!=null));
		}
	}
}
