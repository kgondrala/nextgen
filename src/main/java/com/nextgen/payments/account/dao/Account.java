package com.nextgen.payments.account.dao;

/**
 * Entity that holds the user account information
 * 
 * @author kgondrala
 *
 */
public class Account {
	
	private String userName;
	private String cardNumber;
	private long amount;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
}
