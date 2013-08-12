package com.tmg.controller.impl;

import com.tmg.controller.UserManager;
import com.tmg.domain.User;
import com.tmg.repository.UserDao;
import com.tmg.repository.impl.UserDaoImpl;
import com.tmg.service.UserValidator;
import com.tmg.service.impl.UserValidatorImpl;

public class UserManagerImpl implements UserManager {
	private UserDao userDao;
	private UserValidator userValidator;

	public UserManagerImpl() {
		this.userDao = new UserDaoImpl();
		this.userValidator = new UserValidatorImpl();
	}

	
	
	/** 
	 * any one create a subscriber account.
	 */
	private boolean createAccount(User user) {
		if(!this.userValidator.isMandatoryFiledsPresent(user)) {
			return false;
		}
		
		return this.userDao.createUser(user);
	}
	
	
	/**
	 * anyone can create "Subscriber" and only "Super User" can create "User"
	 * No one can create "Super User" via this api
	 */
	@Override
	public boolean createAccount(User user, String adminUsername, String adminPassword) throws Exception {
		if(user.getType().equals(User.TYPE_SUBSCRIBER)) {
			return this.createAccount(user);
		}
		
		// only super user can create "User"
		if(user.getType().equals(User.TYPE_USER)) {
			User adminUser = this.retrieveUserDetails(adminUsername, adminPassword);
			if(adminUser == null) {
				throw new Exception("Authorisation failed");
			}
			
			if(!adminUser.getType().equals(User.TYPE_SUPER_USER)) {
				throw new Exception("Authorisation failed");
			}
			
			return this.createAccount(user);
		}
		
		throw new Exception("Authorisation failed");
	}
	
	
	private User retrieveUserDetails(String username, String password) throws Exception {
		User user = this.userDao.retrieveUser(username);
		if(user == null) {
			return null;
		}
		
		if(!user.getPassword().equals(password)) {
			throw new Exception("Authorisation failed");
		}
		
		return user;
	}


	@Override
	public User retrieveUserDetails(String username, String password, String adminUsername) throws Exception {
		if(adminUsername != null) {
			User adminUser = this.retrieveUserDetails(adminUsername, password); // this is expected to throw exception
			
			if(adminUser.getType().equals(User.TYPE_USER) || adminUser.getType().equals(User.TYPE_SUPER_USER)) {
				return this.userDao.retrieveUser(username);
			}
			
			throw new Exception("Authorisation failed");
		}
		
		return this.retrieveUserDetails(username, password);
	}



	@Override
	public boolean deleteUser(String userToDelete, String adminUsername, String adminPassword) throws Exception {
		User deleteMe = this.userDao.retrieveUser(userToDelete); 
		if(deleteMe == null) {
			return true;
		}

		// can't delete super user
		if(!deleteMe.getType().equals(User.TYPE_SUBSCRIBER) && !deleteMe.getType().equals(User.TYPE_USER)) {
			throw new Exception("Authorisation failed");
		}
		
		if(isSuperValidSuperUser(adminUsername, adminPassword)) {
			return this.userDao.deleteUser(userToDelete);
		}
		
		return false;
	}



	private boolean isSuperValidSuperUser(String adminUsername, String adminPassword)
			throws Exception {
		
		String userType = this.retrieveAdminType(adminUsername, adminPassword);
		
		return userType == null ? false : userType.equals(User.TYPE_SUPER_USER);
	}



	@Override
	public boolean updateUser(User user, String adminUsername, String adminPassword)
			throws Exception {
		if(user == null) {
			return false;
		}
		
		if(!this.userValidator.isMandatoryFiledsPresent(user)) {
			return false;
		}
		
		// can't update super user
		if(!user.getType().equals(User.TYPE_SUBSCRIBER) && !user.getType().equals(User.TYPE_USER)) {
			return false;
		}
		
		String adminType = this.retrieveAdminType(adminUsername, adminPassword);
		
		if(adminType == null) {
			return false;
		}
		
		if(adminType.equals(User.TYPE_SUPER_USER)) {
			return this.userDao.updateUser(user);
		}

		if(adminType.equals(User.TYPE_USER) && user.getType().equals(User.TYPE_SUBSCRIBER)) { 
			return this.userDao.updateUser(user);
		}
		
		return false;
	}

	private String retrieveAdminType(String username, String password) throws Exception {
		User user = this.retrieveUserDetails(username, password);
		
		return user == null ? null : user.getType();
	}
}
