package com.nextgen.payments.exception;

/**
 * This extends {@link PaymentException} and is thrown by NextGen 
 * when the given {@link Account} has insufficient funds to process the request.
 * 
 * @author kgondrala
 *
 */
public class InsufficientFundsException extends PaymentException {

	private static final long serialVersionUID = -1365337009348776465L;

	public InsufficientFundsException(String message) {
        super(message);
    }
	
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
