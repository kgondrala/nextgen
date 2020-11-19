package com.nextgen.payments.exception;

/**
 * Generic exception extends {@link RuntimeException} and 
 * handles any NextGen payment exception.
 * 
 * @author kgondrala
 *
 */
public class PaymentException extends RuntimeException {
	
	private static final long serialVersionUID = 1697660579450339386L;

	public PaymentException(String message) {
        super(message);
    }
	
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
