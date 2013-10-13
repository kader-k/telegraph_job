package com.tmg.ws.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.tmg.controller.UserManager;
import com.tmg.controller.impl.UserManagerImpl;
import com.tmg.domain.User;

/**
 * This is the API the "web" and "mobile" teams will call from the "register" and "my details" pages on the website and the internal tools will use for house keeping.
 * @author Kader
 *
 */
@Path("user")
public class UserResource {
	
	private UserManager userManager;
	
	public UserResource() {
		this.setUserManager(new UserManagerImpl());
	}
	
	/**
	 * <b>PUT:</b> http://localhost:8080/tmg/api/user/register <br/>
	 * <b>Accept:</b> application/json <br/>
	 * <b>Content-Type:</b> application/json <br/>
	 * To register a Subscriber, the adminUsername and adminPassword are not required however, all user object properties are mandatory including the home and billing address. <br/>
	 * <b>Body:</b> {"user":{"type":"Subscriber","firstName":"Normal","lastName":"Subscriber","title":"Mr","dateOfBirth":"2008-02-01","email":"newsubscriber@tmg.com","password":"password","home":{"houseNumber":"101", "address1":"Home", "city":"London"},"billing":{"houseNumber":"101", "address1":"Home", "city":"London"}}} <br/>
	 * 
	 * To register an User, the adminUsername and adminPassword are required but the home and billing addresses are optional. The admin has to be Super User type for registering User type <br/>
	 * <b>Body:</b> {"user":{"type":"User","firstName":"Normal","lastName":"User","title":"Mr","dateOfBirth":"2008-02-01","email":"newuser@tmg.com","password":"password"},"adminUsername":"superuser@tmg.com", "adminPassword":"super"} <br/>
	 * 
	 * @param AdminUser
	 * @return response code of 201 (CREATED) | 406 (NOT_ACCEPTABLE) | 401 (UNAUTHORIZED)
	 */
	@PUT
	@Path("/register")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(AdminUser user) {
		
		try {
			boolean created = this.userManager.createAccount(user.getUser(), user.getAdminUsername(), user.getAdminPassword());
			return created ? Response.status(Status.CREATED).build() : Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	/**
	 * <b>POST:</b> http://localhost:8080/tmg/api/user/mydetails <br/>
	 * <b>Content-Type:</b> application/json <br/>
	 * 
	 * To get the subscriber details, pass in the username and password <br/>
	 * <b>Body:</b> username=newuser@tmg.com&password=password <br/>
	 * 
	 * To get the user or subscriber details, pass in the subscriber username, adminUsername and adminPassword <br/>
	 * <b>Body:</b> username=newuser@tmg.com&adminUsername=superuser@tmg.compassword=super& <br/>
	 * 
	 * Admin in this case can be User or Super User type <br/>
	 * @param username
	 * @param password
	 * @param adminUsername
	 * @return json
	 */
	@POST
	@Path("/mydetails")
	@Produces({MediaType.APPLICATION_JSON})
	public Response retrieve(@FormParam("username") String username, 
			@FormParam("password") String password,
			@FormParam("adminUsername") String adminUsername) {
		
		try {
			User user = this.userManager.retrieveUserDetails(username, password, adminUsername);
			return Response.ok(user).build();
			
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	
	/**
	 * <b>PUT:</b> http://localhost:8080/tmg/api/user/update <br/>
	 * <b>Accept:</b> application/json <br/>
	 * <b>Content-Type:</b> application/json <br/>
	 * To update a Subscriber, the adminUsername and adminPassword are required and all user object properties are mandatory including the home and billing address. <br/>
	 * User or Super User can only update Subscriber <br/>
	 * <b>Body:</b> {"user":{"type":"Subscriber","firstName":"Normal","lastName":"Subscriber","title":"Mr","dateOfBirth":"2008-02-01","email":"newsubscriber@tmg.com","password":"password","home":{"houseNumber":"101", "address1":"Home", "city":"London"},"billing":{"houseNumber":"101", "address1":"Home", "city":"London"}}, "adminUsername":"superuser@tmg.com", "adminPassword":"super"} <br/>
	 * <br/>
	 * To update an User, the adminUsername and adminPassword are required but the home and billing addresses are optional. The admin has to be Super User type for updating User type <br/>
	 * <b>Body:</b> {"user":{"type":"User","firstName":"Normal","lastName":"User","title":"Mr","dateOfBirth":"2008-02-01","email":"newuser@tmg.com","password":"password"},"adminUsername":"superuser@tmg.com", "adminPassword":"super"} <br/>

	 * @param AdminUser
	 * @return response code of 201 (CREATED) | 406 (NOT_ACCEPTABLE) | 401 (UNAUTHORIZED)
	 */
	@PUT
	@Path("/update")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response update(AdminUser user) {
		
		try {
			return this.userManager.updateUser(user.getUser(), user.getAdminUsername(), user.getAdminPassword()) ? 
				Response.status(Status.OK).build() : Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	/**
	 * <b>DELETE:</b> http://localhost:8080/tmg/api/user/delete <br/>
	 * Only Super User type can delete an User or Subscriber type <br/>
	 * <b>Body:</b> userToDelete=user1@tmg.com&adminUsername=superuser&adminPassword=super <br/>
	 * @param userToDelete
	 * @param adminUsername
	 * @param adminPassword
	 * @return 200 (OK) | 304 (NOT_MODIFIED) | 401 (UNAUTHORIZED)
	 */
	@DELETE
	@Path("/delete")
	public Response delete(@FormParam("userToDelete") String userToDelete, 
			@FormParam("adminUsername") String adminUsername,
			@FormParam("adminPassword") String adminPassword) {

		try {
			return this.userManager.deleteUser(userToDelete, adminUsername, adminPassword) ?
				Response.status(Status.OK).build() : Response.status(Status.NOT_MODIFIED).build();
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
