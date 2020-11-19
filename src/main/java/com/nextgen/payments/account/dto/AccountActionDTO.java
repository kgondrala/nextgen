package com.nextgen.payments.account.dto;

import com.nextgen.payments.AccountActionType;

/**
 * A Data Transfer Object for Account actions submitted as part of batch file.
 * 
 * @author kgondrala
 *
 */
public class AccountActionDTO {

	private AccountActionType type;
	private String userName;
	private String cardNumber;
	private long amount;
	
	public AccountActionType getType() {
		return type;
	}
	public String getUserName() {
		return userName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public long getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return type + " " + userName + " " + (cardNumber == null ? "" : cardNumber) + " $" + amount;
	}
	
	public static class Builder {
		private String type;
		private String userName;
		private String cardNumber;
		private long amount;
		
		public Builder withType(String type) {
			this.type = type;
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder cardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
			return this;
		}

		public Builder amount(long amount) {
			this.amount = amount;
			return this;
		}
		
		public AccountActionDTO build() {
			AccountActionDTO r = new AccountActionDTO();
			r.type = AccountActionType.valueOf(this.type);
			r.userName = this.userName;
			r.cardNumber = this.cardNumber;
			r.amount = this.amount;
			return r;
		}
	}

}
