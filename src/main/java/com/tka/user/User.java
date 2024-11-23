package com.tka.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class User {
	@Id
	private int userID;
	private String username;
	private String password;
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<TransactionSummary> transactions;

	public User() {
		super();
	}
	

	public User(int userID, String username, String password, String email, List<TransactionSummary> transactions) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.transactions = transactions;
	}


	public User(int userID, String username, String password, String email) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TransactionSummary> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionSummary> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", password=" + password + ", email=" + email
				+ "]";
	}

}
