package com.user.service;

import java.util.Map;

import com.user.bindings.LoginForm;
import com.user.bindings.UnlockAccForm;
import com.user.bindings.UserRegForm;

public interface UserManagementService {

//Login Functionality Method
	public String Login(LoginForm logform);

//	Registration Functionalities Methods
	public String emailCheck(String emailId);

	public Map<Integer, String> loadCountries();

	public Map<Integer, String> loadStates(Integer countryId);

	public Map<Integer, String> loadCities(Integer stateId);

	public String registerUser(UserRegForm userform);

// Unlock Account Functionality Methods
	public String unlockAccount(UnlockAccForm unlockAccform);

//	Forgot Password Functionality Method
	public String forgotPwd(String email);
}
