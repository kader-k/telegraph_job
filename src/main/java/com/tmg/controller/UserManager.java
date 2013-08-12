package com.tmg.controller;

import com.tmg.domain.User;

public interface UserManager {
	public boolean createAccount(User user, String username, String password) throws Exception;
	public User retrieveUserDetails(String username, String password, String adminUsername) throws Exception;
	public boolean deleteUser(String userToDelete, String adminUsername, String adminPassword) throws Exception;
	public boolean updateUser(User user, String adminUsername, String adminPassword) throws Exception;
}
