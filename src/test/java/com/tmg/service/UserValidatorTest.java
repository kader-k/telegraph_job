package com.tmg.service;

import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import com.tmg.domain.Address;
import com.tmg.domain.User;
import com.tmg.service.impl.UserValidatorImpl;

public class UserValidatorTest {

	@Test
	public void isMandatoryFiledsPresent() {
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
		
		UserValidator userValidator = new UserValidatorImpl();
		boolean expected = true;
		boolean actual = userValidator.isMandatoryFiledsPresent(validUser);
		
		Assert.assertEquals(expected, actual);
	}

}
