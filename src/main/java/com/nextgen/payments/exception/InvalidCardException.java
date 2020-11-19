package com.nextgen.payments.exception;

public class InvalidCardException extends PaymentException {

	private static final long serialVersionUID = 5452200788081371714L;

	public InvalidCardException(String message) {
        super(message);
    }
	
    public InvalidCardException(String message, Throwable cause) {
        super(message, cause);
    }

}
