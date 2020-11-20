package com.nextgen.payments;

public enum CardType {

	Gold("Gold"),
	Platinum("Platinum"),
	Work("Work"),
	Home("Home");
	
	private String type;
	
	CardType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
