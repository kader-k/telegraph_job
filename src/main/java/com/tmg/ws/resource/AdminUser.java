package com.tmg.ws.resource;

import com.tmg.domain.User;

/**
 * Wrapper class for holding user and admin user details
 * @author Kader
 *
 */
public class AdminUser {
	private User user;
	private String adminUsername;
	private String adminPassword;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
}
