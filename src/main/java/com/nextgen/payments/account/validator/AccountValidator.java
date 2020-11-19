package com.nextgen.payments.account.validator;

import com.nextgen.payments.account.dao.Account;
import com.nextgen.payments.exception.InvalidCardException;

/**
 * Account Validator containing {@link Account} related validations.
 * 
 * @author kgondrala
 *
 */
public class AccountValidator {

	/**
	 * Luhn Algorithm program to validate credit card numbers.
	 * 
	 * @param cardNumber Credit Card Number
	 * @return boolean
	 */
	public static void validateCardNumber(String cardNumber) {
		
		if(cardNumber == null || cardNumber.isBlank()) {
			throw new InvalidCardException(cardNumber + " is invalid");
		}

		int[] ints = new int[cardNumber.length()];
		for (int i = 0; i < cardNumber.length(); i++) {
			ints[i] = Integer.parseInt(cardNumber.substring(i, i + 1));
		}
		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9) {
				j = j % 10 + 1;
			}
			ints[i] = j;
		}
		int sum = 0;
		for (int i = 0; i < ints.length; i++) {
			sum += ints[i];
		}
		if (sum % 10 != 0) {
			throw new InvalidCardException(cardNumber + " is invalid");
		}
	}
}
