package com.tmg.service;

import com.tmg.domain.User;

public interface UserValidator {
	public boolean isMandatoryFiledsPresent(User user);

}
