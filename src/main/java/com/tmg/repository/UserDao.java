package com.tmg.repository;

import com.tmg.domain.User;

public interface UserDao {
	public boolean createUser(User user);
	/** usename is email address
	 * 
	 * @param username
	 * @return
	 */
	public User retrieveUser(String username);
	
	/**
	 * user must exist for update to take place
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);
	
	/** returns true if user does not exist
	 * 
	 * @param username
	 * @return
	 */
	public boolean deleteUser(String username);
	
}
