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

@Path("user")
public class UserResource {
	
	private UserManager userManager;
	
	public UserResource() {
		this.setUserManager(new UserManagerImpl());
	}
	
	/**
	 * anyone can register
PUT http://localhost:8080/tmg/api/user/register
Host: localhost:8080
Accept: application/json
Content-Type: application/json

{"user":{"type":"Subscriber","firstName":"Subscriber","lastName":"One","title":"Mr","dateOfBirth":"2008-02-01","email":"usernew@tmg.com","password":"password"}}
	 * @param subscriber
	 * @return
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
	 * 
	 * @param username
	 * @param password
	 * @param adminUsername
	 * @return
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
	 * 
	 * @param user
	 * @return
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
	 * DELETE http://localhost:8080/tmg/api/user/delete
Host: localhost:8080
Content-Type: application/json

userToDelete=x&adminUsername=adminUsername&adminPassword=adminPassword
	 * @param userToDelete
	 * @param adminUsername
	 * @param adminPassword
	 * @return
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
