package com.tmg.bdd.steps;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.tmg.bdd.support.UserManagerSupport;
import com.tmg.domain.Address;
import com.tmg.domain.User;
import com.tmg.ws.resource.AdminUser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
public class UserManagerStepdefs {

	private UserManagerSupport userManagerSupport = new UserManagerSupport();
	private Object responseCode;
	private String username;
	private String password;
	private String adminUsername;
	private User userDataset;
	
	@Given("^that the \"([^\"]*)\" user does not exist$")
	public void that_the_user_does_not_exist(String username) throws Throwable {
		// The api has no feature to check this. Ignored for simplicity
	}

	@When("^data set is:$")
	public void data_set_is(List<Map<String, String>> data) throws Throwable {
		Map<String, String> userData = data.get(0);
		this.userDataset = new User();
		this.userDataset.setType(userData.get("type"));
		this.userDataset.setFirstName(userData.get("firstName"));
		this.userDataset.setLastName(userData.get("lastName"));
		this.userDataset.setTitle(userData.get("title"));
		this.userDataset.setDateOfBirth(Date.valueOf(userData.get("dateOfBirth")));
		this.userDataset.setEmail(userData.get("email"));
		this.userDataset.setPassword(userData.get("password"));
		
		Address home = new Address();
		home.setHouseNumber(userData.get("home_houseNum"));
		home.setPostcode(userData.get("home_postcode"));
		
		Address billing = new Address();
		home.setHouseNumber(userData.get("billing_houseNum"));
		home.setPostcode(userData.get("billing_postcode"));
		
		this.userDataset.setHome(home);
		this.userDataset.setBilling(billing);
	}
	
	@When("^I call api register \"([^\"]*)\"$")
	public void I_call_api_register(String url) throws Throwable {
		AdminUser adminUser = new AdminUser();
		adminUser.setUser(this.userDataset);
		adminUser.setAdminUsername(this.username);
		adminUser.setAdminPassword(this.password);
		this.responseCode = this.userManagerSupport.register(url, adminUser);
	}
		
	@Then("^I should get response status (\\d+)$")
	public void I_should_get_response_status(int expected) throws Throwable {
		Assert.assertEquals(expected, this.responseCode);
	}
	
	@Given("^\"([^\"]*)\" exists with password \"([^\"]*)\"$")
	public void exists_with_password(String user, String userpassword) throws Throwable {
	    // The api has no feature to check this. Ignored for simplicity
	}

	@When("^username is \"([^\"]*)\"$")
	public void username_is(String username) throws Throwable {
	    this.username = username;
	}

	@When("^password is \"([^\"]*)\"$")
	public void password_is(String password) throws Throwable {
	    this.password = password;
	}
	
	@Given("^that the \"([^\"]*)\" user exist$")
	public void that_the_user_exist(String arg1) throws Throwable {
		// The api has no feature to check this. Ignored for simplicity
	}

	@When("^adminUsername is \"([^\"]*)\"$")
	public void adminUsername_is(String adminUsername) throws Throwable {
		if(adminUsername != null && adminUsername.length() <= 0) {
			this.adminUsername = null;
		} else {
			this.adminUsername = adminUsername;
		}
	}

	@When("^I call api mydetails \"([^\"]*)\"$")
	public void I_call_api_mydetails(String url) throws Throwable {
		this.responseCode = this.userManagerSupport.retrieve(url, this.username, this.password, this.adminUsername);
	}
	
	
	@When("^I call api delete \"([^\"]*)\"$")
	public void I_call_api_delete(String url) throws Throwable {
		this.responseCode = this.userManagerSupport.delete(url, this.username, this.password, this.adminUsername);
	}
	
	@When("^I call api update \"([^\"]*)\"$")
	public void I_call_api_update(String url) throws Throwable {
		AdminUser adminUser = new AdminUser();
		adminUser.setUser(this.userDataset);
		adminUser.setAdminUsername(this.adminUsername);
		adminUser.setAdminPassword(this.password);
		this.responseCode = this.userManagerSupport.update(url, adminUser);
	}
}
