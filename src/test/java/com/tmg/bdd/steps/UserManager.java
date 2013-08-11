package com.tmg.bdd.steps;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.tmg.domain.Address;
import com.tmg.domain.User;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
public class UserManager {

	@Given("^that the \"([^\"]*)\" user does not exist$")
	public void that_the_user_does_not_exist(String username) throws Throwable {
		
		//fail("TODO:");
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
		
	}
	
	@Then("^I should get created status (\\d+)$")
	public void I_should_get_created_status(int arg1) throws Throwable {
		fail("TODO:");
	}
}
