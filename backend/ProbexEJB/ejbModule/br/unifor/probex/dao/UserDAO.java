package br.unifor.probex.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.unifor.probex.dto.UserSimpleDTO;
import br.unifor.probex.entity.User;

@Stateless
public class UserDAO {

	@PersistenceContext
	private EntityManager manager;

	public String insert(User user) {
		try {
			manager.persist(user);
			return user.getUsername() + " inserted";
		} catch (PersistenceException e) {
			return "could not insert data " + e;
		}
	}

	public List<UserSimpleDTO> list() {
		List<User> users = manager.createQuery("SELECT a FROM User a LEFT JOIN FETCH a.permissions", User.class)
				.getResultList();
		List<UserSimpleDTO> userDTOs = new ArrayList<UserSimpleDTO>();
		for (User u : users) {
			UserSimpleDTO dto = new UserSimpleDTO(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(),
					u.getPermissions());
			userDTOs.add(dto);
		}
		return userDTOs;
	}

	public User findByUsernameAndPassword(String username, String password) {
		Query query = manager.createQuery(
				"SELECT a FROM User a LEFT JOIN FETCH a.permissions LEFT JOIN FETCH a.posts LEFT JOIN FETCH a.comments WHERE a.username = :username AND a.password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		User user;
		try {
			user = (User) query.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}

	}

	public User findById(Long id) {
		User user = (User) manager.createQuery(
				"SELECT a FROM User a LEFT JOIN FETCH a.permissions LEFT JOIN FETCH a.posts LEFT JOIN FETCH a.comments WHERE a.id = :id",
				User.class).setParameter("id", id).getSingleResult();
		return user;
	}
	
	public User findByUsername(String username) {
		User user = (User) manager.createQuery(
				"SELECT a FROM User a LEFT JOIN FETCH a.permissions LEFT JOIN FETCH a.posts LEFT JOIN FETCH a.comments WHERE a.username = :username",
				User.class).setParameter("username", username).getSingleResult();
		return user;
	}

	public String remove(Long id) {

		try {
			User user = manager.find(User.class, id);
			manager.remove(user);
			return user.getUsername() + " removed";
		} catch (PersistenceException e) {
			return "could not remove data " + e;
		}
	}

	public String update(User user) {

		try {
			User detached = manager.find(User.class, user.getId());

			if (detached == null)
				return "no user found with id " + user.getId();

			User managed = manager.merge(detached);

			managed.setUsername(user.getUsername());
			managed.setEmail(user.getEmail());
			managed.setPassword(user.getPassword());

			return user.getUsername() + " updated";
		} catch (PersistenceException e) {
			return "could not update data " + e;
		}
	}
}