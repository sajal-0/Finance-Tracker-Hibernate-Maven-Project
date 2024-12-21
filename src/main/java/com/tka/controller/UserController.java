package com.tka.controller;

import java.util.List;

import com.tka.service.ServiceUserRegistration;
import com.tka.user.User;

public class UserController {
	ServiceUserRegistration service = null;
	
	public List<User> regUser(User user) {
		service = new ServiceUserRegistration();
		List<User> regUser = service.regUser(user); 
		return regUser;
	 }
	public User getUserByUsername(String username) {
        return service.findByUsername(username);
    }
	 public boolean loginUser(String username, String password) {
		 service = new ServiceUserRegistration();
		 boolean loginUser = service.loginUser(username, password);
		 return loginUser;
	 }
	 
	 
	
}
