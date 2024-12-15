package com.tka.controller;

import java.util.List;

import com.tka.service.ServiceUserRegistration;
import com.tka.user.User;

public class ControllerTracker {
	ServiceUserRegistration service = null;
	
	public List<User> regUser(User user) {
		service = new ServiceUserRegistration();
		List<User> regUser = service.regUser(user); 
		return regUser;
	 }
	 public boolean loginUser(String username, String password) {
		 service = new ServiceUserRegistration();
		 boolean loginUser = service.loginUser(username, password);
		 return loginUser;
	 }
	 
	
}
