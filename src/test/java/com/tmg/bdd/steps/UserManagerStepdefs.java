package com.tmg.bdd.steps;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.tmg.bdd.support.UserManagerSupport;
import com.tmg.domain.Address;
import com.tmg.domain.User;
import com.tmg.repository.UserDao;
import com.tmg.repository.impl.UserDaoImpl;
import com.tmg.ws.resource.AdminUser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
public class UserManagerStepdefs {

	private UserManagerSupport userManagerSupport = new UserManagerSupport();
	private Object responseCode;
	
	@Given("^that the \"([^\"]*)\" user does not exist$")
	public void that_the_user_does_not_exist(String username) throws Throwable {
		// remove the username if exists
		
	}

	@When("^I call api \"([^\"]*)\" with:$")
	public void I_call_api_with(String url, List<Map<String, String>> data) throws Throwable {
		Map<String, String> userData = data.get(0);
		User user = new User();
		user.setType(userData.get("type"));
		user.setFirstName(userData.get("firstName"));
		user.setLastName(userData.get("lastName"));
		user.setTitle(userData.get("title"));
		user.setDateOfBirth(Date.valueOf(userData.get("dateOfBirth")));
		user.setEmail(userData.get("email"));
		user.setPassword(userData.get("password"));
		
		Address home = new Address();
		home.setHouseNumber(userData.get("home_houseNum"));
		home.setPostcode(userData.get("home_postcode"));
		
		Address billing = new Address();
		home.setHouseNumber(userData.get("billing_houseNum"));
		home.setPostcode(userData.get("billing_postcode"));
		
		user.setHome(home);
		user.setBilling(billing);
		
		AdminUser adminUser = new AdminUser();
		adminUser.setUser(user);
		
		this.responseCode = this.userManagerSupport.register(url, adminUser);
	}
	
	@Then("^I should get created response status (\\d+)$")
	public void I_should_get_created_status(int expected) throws Throwable {
		Assert.assertEquals(expected, this.responseCode);
	}
}
