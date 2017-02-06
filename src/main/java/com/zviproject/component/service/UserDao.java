package com.zviproject.component.service;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.Status;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IUser;

/**
 * 
 * @author zviproject
 *
 */
@Repository
public class UserDao extends HibernateUtil implements IUser {

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
		ReturnedId returnedId;
		if (checkRegisterUser(user.getLogin())!=false) {
			returnedId = new ReturnedId(0, Status.ERROR);
			return returnedId;
		}

		try (Session session = getSessionFactory().openSession()) {
			session.save(user);
			returnedId = new ReturnedId(user.getId(), Status.SUCCESSFUL);
			return returnedId;
		}

	}

	/**
	 * Checking presence name user in a DB<br>
	 * if user not in a DB then this name can be registered
	 * 
	 * @param name
	 * @return exist
	 */
	private boolean checkRegisterUser(String login) {
		boolean bol=false;
		try{
			
		Session session = getSessionFactory().openSession();
		
		Criteria criteriaUser = session.createCriteria(User.class.getName()).add(Restrictions.eq("login", login))
				.setProjection(Projections.property("id"));
		int checkId = (int)criteriaUser.uniqueResult() ;
		return bol=true;
		}catch (NullPointerException e) {
			return bol;
		}
		
	}

	/**
	 * Update token for user by id
	 */
	@Override
	@Transactional
	public ReturnedId updateToken(String access_token, int id_user) {

		try (Session session = getSessionFactory().openSession()) {
			User userUpdate = (User) session.get(User.class, id_user);
			userUpdate.setAccessToken(access_token);

			session.beginTransaction();
			session.update(userUpdate);
			session.getTransaction().commit();

			ReturnedId returnedId = new ReturnedId(id_user, Status.SUCCESSFUL);

			return returnedId;
		}
	}

	@Override
	public Set<Room> getUserRooms(Integer id) {
		try(Session session = getSessionFactory().openSession()){						
			session.beginTransaction();
			Set<Room> rooms = session.get(User.class, id).getRooms();
			Hibernate.initialize(new Room());
			Hibernate.initialize(rooms);
			return rooms;
		}	
	}
}
