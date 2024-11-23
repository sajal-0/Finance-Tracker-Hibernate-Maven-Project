package com.tka.service;

import java.util.ArrayList;
import java.util.List;

import com.tka.dao.RegisterUserDao;
import com.tka.user.User;

public class ServiceTracker {
	RegisterUserDao userDao = null;
	
	 public List<User> regUser(User user) {
		 userDao = new RegisterUserDao();
		 
		 if (userDao.isUserExists(user.getUsername())) {
		        System.out.println("User exists");
		        return new ArrayList<>(); 
		    }
		 
		 List<User> regUser = userDao.regUser(user);
		 return regUser;
	 }
	 
	 public boolean loginUser(String username, String password) {
		 userDao = new RegisterUserDao();
		 boolean isAuthenticate = userDao.loginUser(username, password);
		 
		 if(isAuthenticate) {
			 return isAuthenticate;
		 }
		 System.out.println("Invalid username or password.");
		 return !(isAuthenticate);
	 }
	 
	 public List<User> updateUser(User user) {
		 userDao = new RegisterUserDao();
		 List<User> updateUser = userDao.updateUser(user);
		 return updateUser;
	 }
	 public List<User> deleteUser(int userID) {
		 userDao = new RegisterUserDao();
		 List<User> deleteUser = userDao.deleteUser(userID);
		 return deleteUser;
	 }
	 
}
