package com.nextgen.payments.exception;

/**
 * This extends {@link PaymentException} and is thrown by NextGen 
 * when an invalid {@link Account} is provided for processing.
 * 
 * @author kgondrala
 *
 */
public class InvalidAccountException extends PaymentException {

	private static final long serialVersionUID = 5452200788081371714L;

	public InvalidAccountException(String message) {
        super(message);
    }
	
    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
    }
	
}