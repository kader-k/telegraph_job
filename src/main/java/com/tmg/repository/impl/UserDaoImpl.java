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

	public UserDaoImpl() {
		// singleton
		if (users == null) {
			synchronized (UserDaoImpl.class) {
				if (users == null) {
					users = new Hashtable<String, User>();
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
