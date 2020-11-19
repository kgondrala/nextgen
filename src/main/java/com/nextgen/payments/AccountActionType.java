package com.nextgen.payments;

/**
 * enum representing all the supported operations on the {@link Account}
 * 
 * @author kgondrala
 *
 */
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
