package com.example.CropMonitoringAPI.DAO.Implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.CropMonitoringAPI.DAO.UserDAO;
import com.example.CropMonitoringAPI.models.User;

public class UserDAOImplementation{

	private static UserDAOImplementation instance;
	protected EntityManager entityManager;

	public static UserDAOImplementation getInstance() {
		if (instance == null) {
			instance = new UserDAOImplementation();
		}

		return instance;
	}

	private UserDAOImplementation() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public User getById(final long id) {
		return entityManager.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return entityManager.createQuery("FROM " + User.class.getName()).getResultList();
	}

	public User persist(User user) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			return null;
		}
	}
	
//	public List<User> persist(List<User> user) {
//		try {
//			entityManager.getTransaction().begin();
//			entityManager.
//			entityManager.getTransaction().commit();
//			return user;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			entityManager.getTransaction().rollback();
//			return null;
//		}
//	}

	public User merge(User user) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(user);
			entityManager.getTransaction().commit();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			return null;
		}
	}

	public void remove(User user) {
		try {
			entityManager.getTransaction().begin();
			user = entityManager.find(User.class, user.getId());
			entityManager.remove(user);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			User user = getById(id);
			remove(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
