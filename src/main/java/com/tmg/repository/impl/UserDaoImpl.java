package com.tmg.repository.impl;

import java.util.Hashtable;
import java.util.Map;

import com.tmg.domain.User;
import com.tmg.repository.UserDao;

/** expected this class to be replaced with proper database.
 * 
 * @author Kader
 *
 */
public class UserDaoImpl implements UserDao {

	private static volatile Map<String, User> users;

	private void initSuperUser() {
		User su = new User();
		su.setEmail("superuser@tmg.com");
		su.setPassword("super");
		su.setType(User.TYPE_SUPER_USER);
		su.setFirstName("Super");
		su.setLastName("User");
		users.put(su.getEmail(), su);
		
		User u = new User();
		u.setEmail("user@tmg.com");
		u.setPassword("user");
		u.setType(User.TYPE_USER);
		u.setFirstName("User");
		u.setLastName("Normal");
		users.put(u.getEmail(), u);
		
		User sub = new User();
		sub.setEmail("subscriber@tmg.com");
		sub.setPassword("subscriber");
		sub.setType(User.TYPE_SUBSCRIBER);
		sub.setFirstName("Subscriber");
		sub.setLastName("Normal");
		users.put(sub.getEmail(), sub);
	}
	
	public UserDaoImpl() {
		// singleton
		if (users == null) {
			synchronized (UserDaoImpl.class) {
				if (users == null) {
					users = new Hashtable<String, User>();
					
					// super user can't be created via api therefore it is created here
					this.initSuperUser();					
				}
			}
		}
	}

	@Override
	public boolean createUser(User user) {
		// not allowed to create 2 entries
		if(users.containsKey(user.getEmail())) {
			return false;
		}
		
		users.put(user.getEmail(), user);
		
		return true;
	}

	@Override
	public User retrieveUser(String username) {
		return users.get(username);
	}

	@Override
	public boolean updateUser(User user) {
		
		// user does not exit therefore can't update
		if(users.get(user.getEmail()) == null) {
			return false;
		}
		
		users.put(user.getEmail(), user);
		
		return true;
	}

	@Override
	public boolean deleteUser(String username) {
		if(!users.containsKey(username)) {
			return true;
		}
		
		users.remove(username);
		return true;
	}	
}
