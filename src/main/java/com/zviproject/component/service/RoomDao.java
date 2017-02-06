package com.zviproject.component.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.component.entity.MessageToDisplay;
import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.Room;
import com.zviproject.component.entity.Status;
import com.zviproject.component.entity.User;
import com.zviproject.component.interfacee.IRoom;
/**
 * 
 * @author af150416
 *
 */
@Repository
@Qualifier
public class RoomDao extends HibernateUtil implements IRoom {

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
			return new ReturnedId(0, Status.ERROR);
		}


		try(Session session = getSessionFactory().openSession()) {
			session.save(room);
			new ReturnedId(room.getId(), Status.SUCCESSFUL);
			return new ReturnedId(room.getId(), Status.SUCCESSFUL);
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
		Session session = getSessionFactory().openSession();
		Criteria criteriaRoom = session.createCriteria(Room.class.getName())
				.add(Restrictions.eq("name", roomName))
				.setProjection(Projections.property("id"));
		return criteriaRoom.uniqueResult() == null;
	}
	
	/**
	 * Get users in the room
	 * 
	 * @param roomId
	 * @return exist
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Room getUsersInRoom(Integer id) {
		try(Session session = getSessionFactory().openSession()){						
			SQLQuery query = session.createSQLQuery("select r from rooms r left join fetch r.users where r.id=:roomId");
			query.setParameter("roomId", id);
			return (Room) query.uniqueResult();
			
		}		
	}
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public Set getAllTeacher(int id) {
//		try {
//			Session session = HibernateUtil.getSessionFactory().openSession();
//			session.beginTransaction();
//			Team team = (Team) session.get(Team.class, id);
//			session.getTransaction().commit();
//	return team.getTeachers();} 
//		catch (Exception e) {throw e;}}
}
