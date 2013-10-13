package com.tmg.repository;

import org.junit.Assert;
import org.junit.Test;

import com.tmg.domain.Address;
import com.tmg.domain.User;
import com.tmg.repository.impl.UserDaoImpl;

public class UserDoaTest {

	@Test
	public void createUser() {
		UserDao userDao = new UserDaoImpl();
		User user = new User();
		user.setFirstName("firstname");
		user.setLastName("lname");
		user.setEmail("createUser@tmg.com");
		Address home = new Address();
		home.setHouseNumber("1");
		home.setPostcode("SW1");

		boolean actual = userDao.createUser(user);
		Assert.assertEquals(true, actual);

		actual = userDao.createUser(user);
		Assert.assertEquals(false, actual);
	}

	@Test
	public void retrieveUser() {
		UserDao userDao = new UserDaoImpl();
		User expected = new User();
		expected.setFirstName("firstname");
		expected.setLastName("lname");
		expected.setEmail("retrieveUser@tmg.com");
		Address home = new Address();
		home.setHouseNumber("1");
		home.setPostcode("SW1");

		userDao.createUser(expected);
		User actual = userDao.retrieveUser("retrieveUser@tmg.com");
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void updateUser() {
		UserDao userDao = new UserDaoImpl();
		User newuser = new User();
		newuser.setFirstName("update");
		newuser.setLastName("me");
		newuser.setEmail("updateUser@tmg.com");
		Address home = new Address();
		home.setHouseNumber("1");
		home.setPostcode("UP1");

		userDao.createUser(newuser);

		User updateUser = new User();
		updateUser.setFirstName("done");
		updateUser.setLastName("updating");
		updateUser.setEmail("updateUser@tmg.com");
		Address homeAddress = new Address();
		homeAddress.setHouseNumber("1");
		homeAddress.setPostcode("UP1");

		boolean expected = true;
		boolean actual = userDao.updateUser(updateUser);

		Assert.assertEquals(expected, actual);
	}

	
	@Test
	public void deleteUser() {
		UserDao userDao = new UserDaoImpl();
		boolean actual = userDao.deleteUser("deleteUser@tmg.com");
		
		boolean expected = true;
		Assert.assertEquals(expected, actual);
		
		User deleteUser = new User();
		deleteUser.setFirstName("delete");
		deleteUser.setLastName("me");
		deleteUser.setEmail("deleteUser@tmg.com");
		Address homeAddress = new Address();
		homeAddress.setHouseNumber("1");
		homeAddress.setPostcode("UP1");
		
		userDao.createUser(deleteUser);
		
		actual = userDao.deleteUser("deleteMe@tmg.com");		
		expected = true;
		Assert.assertEquals(expected, actual);
	}
}
