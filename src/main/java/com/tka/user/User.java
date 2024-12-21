package com.tka.user;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;
	
	
	@NotNull
	@Size(min = 4, max = 20)
	private String username;
	private String password;
	@Email
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<TransactionSummary> transactions = new ArrayList<>();

	public User() {
		super();
	}
	


	public User(@NotNull @Size(min = 4, max = 20) String username, String password, @Email String email,
			List<TransactionSummary> transactions) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.transactions = transactions;
	}



	public User(@NotNull @Size(min = 4, max = 20) String username, String password, @Email String email) {
		super();
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
