package com.tka.user;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TransactionSummary {
	
@Id
private int traID;
private double amount;
private String type;
private String category;

//@Temporal(TemporalType.DATE)
private Date date;
private String description;

@ManyToOne
@JoinColumn( nullable = false)
private User user;

public TransactionSummary() {
	super();
}


public User getUser() {
	return user;
}


public void setUser(User user) {
	this.user = user;
}


public TransactionSummary(double amount, String type, String category, Date date, String description, User user) {
	super();
	this.amount = amount;
	this.type = type;
	this.category = category;
	this.date = date;
	this.description = description;
	this.user = user;
}


public TransactionSummary(int traID, double amount, String type, String category, Date date, String description,
		User user) {
	super();
	this.traID = traID;
	this.amount = amount;
	this.type = type;
	this.category = category;
	this.date = date;
	this.description = description;
	this.user = user;
}


public TransactionSummary(int traID, int amount, String type, String category, Date date, String description) {
	super();
	this.traID = traID;
	this.amount = amount;
	this.type = type;
	this.category = category;
	this.date = date;
	this.description = description;
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

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}


@Override
public String toString() {
	return "TransactionSummary [traID=" + traID + ", amount=" + amount + ", type=" + type + ", category=" + category
			+ ", date=" + date + ", description=" + description + ", user=" + user + "]";
}






}
