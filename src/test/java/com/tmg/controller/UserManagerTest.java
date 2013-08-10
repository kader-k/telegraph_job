package com.tmg.controller;

import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import com.tmg.domain.Address;
import com.tmg.domain.User;
import com.tmg.repository.UserDao;
import com.tmg.repository.impl.UserDaoImpl;

public class UserManagerTest {

	private User createValidUser() {
		User validUser = new User();
		validUser.setFirstName("valid");
		validUser.setLastName("user");
		validUser.setTitle("Mr");
		validUser.setDateOfBirth(new Date(0));
		validUser.setEmail("validUser@tmg.com");
		validUser.setPassword("password");
		Address home = new Address();
		home.setHouseNumber("1");
		home.setPostcode("UP1");
		validUser.setHome(home);
		validUser.setBilling(home);
		validUser.setType("Subscriber");
		return validUser;			
	}
	
	@Test
	public void deleteAccount() throws Exception {
		String userToDelete = "deleteMe@tmg.com";
		String adminUser = "superUser@tmg.com";
		String adminPassword = "password";
		User superUser = this.createValidUser();
		superUser.setType(User.TYPE_SUPER_USER);
		superUser.setEmail(adminUser);
		superUser.setPassword(adminPassword);
		
		User deleteMeUser = this.createValidUser();
		deleteMeUser.setEmail(userToDelete);
		deleteMeUser.setType(User.TYPE_USER);
		
		UserDao userDao = new UserDaoImpl();
		userDao.createUser(superUser);
		userDao.createUser(deleteMeUser);

		UserManager userManager = new UserManagerImpl();
		boolean actual = userManager.deleteUser(userToDelete, adminUser, adminPassword);
		boolean expected = true;
		Assert.assertEquals(expected, actual);
	}
	
	
	@Test
	public void updateAccount() throws Exception {
		User updateMe = this.createValidUser();
		updateMe.setFirstName("update");
		
		String adminUser = "adminUser@tmg.com";
		String adminPassword = "password";
		User admin = this.createValidUser();
		admin.setEmail(adminUser);
		admin.setPassword(adminPassword);
		admin.setType(User.TYPE_SUPER_USER);
		
		UserDao userDao = new UserDaoImpl();
		userDao.createUser(admin);
		
		UserManager userManager = new UserManagerImpl();
		boolean expected = true;
		boolean actual = userManager.updateUser(updateMe, adminUser, adminPassword);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createSubscriberAccount() throws Exception {
		UserManager userManager = new UserManagerImpl();
		boolean expected = true;
		boolean actual = userManager.createAccount(this.createValidUser(), null, null);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createUserAccount() throws Exception {
		String username = "adminuser@tmg.com";
		String password = "adminuser";
		User adminUser = this.createValidUser();
		adminUser.setType(User.TYPE_SUPER_USER);
		adminUser.setEmail(username);
		adminUser.setPassword(password);
		UserDao userDao = new UserDaoImpl();
		userDao.createUser(adminUser);
		
		
		UserManager userManager = new UserManagerImpl();
		User user = this.createValidUser();
		user.setType(User.TYPE_USER);
		user.setEmail("user@tmg.com");
		boolean expected = true;
		boolean actual = userManager.createAccount(user, username, password);
		
		Assert.assertEquals(expected, actual);
	}
	
	
	@Test
	public void retrieveUserDetailsAsAdminUser() throws Exception {
		String adminUsername = "adminuser@tmg.com";
		String adminPassword = "adminuser";
		User adminUser = this.createValidUser();
		adminUser.setType(User.TYPE_SUPER_USER);
		adminUser.setEmail(adminUsername);
		adminUser.setPassword(adminPassword);
		UserDao userDao = new UserDaoImpl();
		userDao.createUser(adminUser);
		
		
		String username = "userx@tmg.com";
		String password = "password";
		User expected = this.createValidUser();
		expected.setEmail(username);
		
		
		UserManager userManager = new UserManagerImpl();
		userManager.createAccount(expected, username, password);
		User actual = userManager.retrieveUserDetails(username, adminPassword, adminUsername);
		
		Assert.assertEquals(expected, actual);
	}
	
	
	@Test(expected=Exception.class)
	public void retrieveUserDetailsAsBadAdminUser() throws Exception {
		String adminUsername = "badadminuser@tmg.com";
		String adminPassword = "errorpassword";
		User adminUser = this.createValidUser();
		adminUser.setType(User.TYPE_USER);
		adminUser.setEmail(adminUsername);
		UserDao userDao = new UserDaoImpl();
		userDao.createUser(adminUser);
		
		String username = "user@tmg.com";
		User expected = this.createValidUser();
		expected.setEmail(username);
		
		UserManager userManager = new UserManagerImpl();
		userManager.retrieveUserDetails(username, adminPassword, adminUsername);
	}
	
	
	@Test
	public void retrieveUserDetails() throws Exception {
		UserManager userManager = new UserManagerImpl();
		
		// add new user to db authorisation 
		User expected = this.createValidUser();
		expected.setEmail("expected@tmg.com"); // rename email to valid conflict with other tests
		userManager.createAccount(expected, null, null);
		
		String username = "expected@tmg.com";
		String password = "password";
		User actual = userManager.retrieveUserDetails(username, password, null);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected=Exception.class)
	public void retrieveUserDetailsAuthFailed() throws Exception {
		UserManager userManager = new UserManagerImpl();
		
		// add new user to db authorisation 
		User expected = this.createValidUser();
		expected.setEmail("authfailed@tmg.com"); // rename email to valid conflict with other tests
		userManager.createAccount(expected, null, null);
		
		String username = "authfailed@tmg.com";
		String password = "missmatch";
		userManager.retrieveUserDetails(username, password, null);
	}
	

}
