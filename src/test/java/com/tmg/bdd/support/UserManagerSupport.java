package com.tmg.bdd.support;

import static com.jayway.restassured.RestAssured.expect;

import java.util.HashMap;

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
	
}
