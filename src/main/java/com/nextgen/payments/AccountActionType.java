package com.nextgen.payments;

public enum AccountActionType {

	Add("Add"),
	Charge("Charge"),
	Credit("Credit");
	
	private String type;
	
	AccountActionType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
