package com.zviproject.component.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.Util.HibernateUtil;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.Status;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IRoom;

@Repository
public class RoomDao implements IRoom {
	@Autowired
	private HibernateUtil hibernateUtil;

	/**
	 * Register new room 
	 * Return information for user.
	 * 
	 * @param room
	 * @return String
	 */

	@Transactional
	public ReturnedId registerRoom(Room room) {
		ReturnedId returnedId;
		if (!checkRegisterRoom(room.getName())) {
			returnedId = new ReturnedId(0, Status.ERROR);
			return returnedId;
		}


		try(Session session = hibernateUtil.getSessionFactory().openSession()) {
			session.save(room);
			returnedId = new ReturnedId(room.getId(), Status.SUCCESSFUL);
			return returnedId;
		}
	}

	/**
	 * Checking presence name of the room in a DB<br>
	 * if room not in a DB then this room name can be registered
	 * 
	 * @param name
	 * @return exist
	 */
	private boolean checkRegisterRoom(String roomName) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Criteria criteriaRoom = session.createCriteria(Room.class.getName())
				.add(Restrictions.eq("name", roomName))
				.setProjection(Projections.property("id"));
		return criteriaRoom.uniqueResult() == null;
	}


	public List<User> getUsersInRoom(Integer roomId) {
		try(Session session = hibernateUtil.getSessionFactory().openSession()){						
			List<User> users = session.get(Room.class, roomId).getUsers();
			return users;
		}		
	}
}
