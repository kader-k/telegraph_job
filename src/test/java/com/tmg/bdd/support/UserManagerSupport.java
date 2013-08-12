package com.tmg.bdd.support;

import static com.jayway.restassured.RestAssured.expect;

import java.util.HashMap;
import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.tmg.ws.resource.AdminUser;

public class UserManagerSupport {
	
	public UserManagerSupport() {
		RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/tmg/api/user";
	}

	public int register(String path, AdminUser user) {
		Response res = expect().given().request().body(user).contentType("application/json").put(path, new HashMap<String, String>());
		return res.getStatusCode();
	}

	public int retrieve(String path, String username, String password, String adminUsername) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		if(adminUsername != null) {
			params.put("adminuser", adminUsername);
		}
		
		Response res = expect().given().formParameters(params).contentType("application/json").post(path);
		return res.getStatusCode();
	}

	public int delete(String path, String userToDelete, String password, String adminUsername) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userToDelete", userToDelete);
		params.put("adminPassword", password);
		params.put("adminUser", adminUsername);
		
		Response res = expect().given().formParameters(params).contentType("application/json").delete(path);
		return res.getStatusCode();
	}

	public int update(String path, AdminUser user) {	
		Response res = expect().given().request().body(user).contentType("application/json").put(path, new HashMap<String, String>());
		return res.getStatusCode();
	}

	
}
