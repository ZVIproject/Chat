package com.zviproject.component.Util;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Working with session for access to db
 */
@Component
public class HibernateUtil {

	private SessionFactory sessionFactory;

	@PostConstruct
	private void buildSessionFactory() {
		System.out.println("=========================================================== buildSessionFactory was started ===========================================================");
		sessionFactory = new Configuration().configure().buildSessionFactory();

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void shutdown() {
		getSessionFactory().close();
	}
}
