package com.nextgen.payments.account.dao;

import com.nextgen.payments.CardType;

/**
 * Entity that holds the user account information
 * 
 * @author kgondrala
 *
 */
public class Account {
	
	private String userName;
	private CardType cardType;
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
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public CardType getCardType() {
		return cardType;
	}
}
