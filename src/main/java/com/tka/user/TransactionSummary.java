package com.tka.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transaction_summary")
public class TransactionSummary {
	
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "traId")
private int traID;

@Column(name = "amount")
private double amount;

@Column(name = "type")
private String type;

@Column(name = "category")
private String category;

@Column(name = "transaction_date")
private Date transactionDate;

@Column(name = "description")
private String description;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "userID", nullable = false)
private User user;

public TransactionSummary() {
	
}



public TransactionSummary(double amount, String type, String category, Date transactionDate, String description,
		User user) {
	super();
	this.amount = amount;
	this.type = type;
	this.category = category;
	this.transactionDate = transactionDate;
	this.description = description;
	this.user = user;
}

public TransactionSummary(int traID, double amount, String type, String category, Date transactionDate,
		String description, User user) {
	super();
	this.traID = traID;
	this.amount = amount;
	this.type = type;
	this.category = category;
	this.transactionDate = transactionDate;
	this.description = description;
	this.user = user;
}



public int getTraID() {
	return traID;
}

public void setTraID(int traID) {
	this.traID = traID;
}



public double getAmount() {
	return amount;
}

public void setAmount(double d) {
	this.amount = d;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public Date getTransactionDate() {
    return transactionDate;
}

public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
}
public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}
@Override
public String toString() {
    return "TransactionSummary{" +
           ", amount=" + amount +
           ", type='" + type + '\'' +
           ", category='" + category + '\'' +
           ", transactionDate=" + transactionDate +
           ", description='" + description + '\'' +
           '}'; // Avoid user.toString()
}




}
