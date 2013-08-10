package com.tmg.service.impl;

import java.sql.Date;

import com.tmg.domain.Address;
import com.tmg.domain.User;
import com.tmg.service.UserValidator;

public class UserValidatorImpl implements UserValidator {

	/** 
	 * the date range is not validated
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isDateOfBirthValid(Date dateOfBirth) {
		return dateOfBirth == null ? false : true;
	}
	

	/**
	 * if string is null return true
	 * if string is 0 length return true
	 * @param string
	 * @return
	 */
	private boolean isStringNullOrEmpty(String string) {
		if(string == null || string.length() <= 0) {
			return true;
		}
		
		return false;
	}
	
	private boolean isUSerTypeValid(String type) {
		if(type ==  null) {
			return false;
		}
		
		if(type.equals(User.TYPE_SUBSCRIBER) || type.equals(User.TYPE_USER) || type.equals(User.TYPE_SUPER_USER)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isMandatoryFiledsPresent(User user) {
		if (user == null) {
			return false;
		}

		if( !this.isUSerTypeValid(user.getType())) {
			return false;
		}
		
		if (this.isStringNullOrEmpty(user.getFirstName())) {
			return false;
		}

		if (this.isStringNullOrEmpty(user.getLastName())) {
			return false;
		}

		if(this.isStringNullOrEmpty(user.getTitle())) {
			return false;
		}
		
		if(!isDateOfBirthValid(user.getDateOfBirth())) {
			return false;
		}
		
		if(!isEmailAddressValid(user.getEmail())) {
			return false;
		}
		
		if(this.isStringNullOrEmpty(user.getPassword())) {
			return false;
		}
		
		if(user.getType().equals(User.TYPE_SUBSCRIBER)) {
			if (!isAddressValid(user.getHome())) {
				return false;
			}

			if (!isAddressValid(user.getBilling())) {
				return false;
			}
		}
		
		return true;
	}


	/**
	 * minimum validation is done i.e. not null address is valid
	 * @param address
	 * @return
	 */
	private boolean isAddressValid(Address address) {
		if(address ==  null) {
			return false;
		}

		return true;
	}


	private boolean isEmailAddressValid(String email) {
		if(email == null) {
			return false;
		}
		
		// taken from http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				
		return email.matches(emailPattern);
	}
}
