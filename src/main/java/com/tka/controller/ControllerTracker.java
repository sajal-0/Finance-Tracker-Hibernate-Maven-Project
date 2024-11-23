package com.tka.controller;

import java.util.List;

import com.tka.service.ServiceTracker;
import com.tka.user.User;

public class ControllerTracker {
	ServiceTracker service = null;
	
	public List<User> regUser(User user) {
		service = new ServiceTracker();
		List<User> regUser = service.regUser(user); 
		return regUser;
	 }
	 public boolean loginUser(String username, String password) {
		 service = new ServiceTracker();
		 boolean loginUser = service.loginUser(username, password);
		 return loginUser;
	 }
	 
	
}
