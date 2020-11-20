package com.nextgen.payments.account.dto;

import com.nextgen.payments.AccountActionType;
import com.nextgen.payments.CardType;

/**
 * A Data Transfer Object for Account actions submitted as part of batch file.
 * 
 * @author kgondrala
 *
 */
public class AccountActionDTO {

	private AccountActionType type;
	private CardType cardType;
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
	public CardType getCardType() {
		return cardType;
	}
	
	public static class Builder {
		private String type;
		private String userName;
		private String cardNumber;
		private long amount;
		private String cardType;
		
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
		
		public Builder cardType(String cardType) {
			this.cardType = cardType;
			return this;
		}
		
		public AccountActionDTO build() {
			AccountActionDTO r = new AccountActionDTO();
			r.type = AccountActionType.valueOf(this.type);
			r.userName = this.userName;
			r.cardNumber = this.cardNumber;
			r.amount = this.amount;
			r.cardType = CardType.valueOf(this.cardType);
			return r;
		}
	}

	@Override
	public String toString() {
		return "AccountActionDTO [type=" + type + ", cardType=" + cardType + ", userName=" + userName + ", cardNumber="
				+ cardNumber + ", amount=" + amount + "]";
	}
	


}
