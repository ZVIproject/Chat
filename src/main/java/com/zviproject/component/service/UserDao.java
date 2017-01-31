package com.zviproject.component.service;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.Util.HibernateUtil;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Status;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IUser;

@Repository
public class UserDao implements IUser {

	@Autowired
	private HibernateUtil hibernateUtil;

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
		if (!checkRegisterUser(user.getLogin())) {
			returnedId = new ReturnedId(user.getidUser(), Status.ERROR);
			return returnedId;
		}

		Session session = hibernateUtil.getSessionFactory().openSession();

		try {

			Date createTime = new Date();
			user.setdateTime(createTime);
			session.save(user);

			returnedId = new ReturnedId(user.getidUser(), Status.SUCCESSFUL);
			return returnedId;

		} finally {
			session.close();
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
		Session session = hibernateUtil.getSessionFactory().openSession();
		Criteria criteriaUser = session.createCriteria(User.class.getName()).add(Restrictions.eq("login", login))
				.setProjection(Projections.property("id"));
		return criteriaUser.uniqueResult() == null;
	}

	/**
	 * Update token for user by id
	 */
	@Override
	@Transactional
	public ReturnedId updateToken(String access_token, int id_user) {
		Session session = hibernateUtil.getSessionFactory().openSession();

		try {
			User userUpdate = (User) session.get(User.class, id_user);
			userUpdate.setaccessToken(access_token);

			session.beginTransaction();
			session.update(userUpdate);

			Date timeUpdate = new Date();
			userUpdate.settimeStamp(timeUpdate);

			session.getTransaction().commit();

			ReturnedId returnedId = new ReturnedId(id_user, Status.SUCCESSFUL);

			return returnedId;

		} finally {
			session.close();
		}

	}

}
