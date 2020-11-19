package com.nextgen.payments.account.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextgen.payments.account.cache.AccountCache;
import com.nextgen.payments.exception.InsufficientFundsException;
import com.nextgen.payments.exception.InvalidAccountException;

/**
 * Implementation of {@link AccountDao}. 
 * Supports CRUD operations on {link Account}.
 * 
 * @author kgondrala
 * 
 */
@Repository
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private AccountCache cache;
	
	/**
	 * adds a new {@link Account} to system.
	 * 
	 * @param account user account
	 * @return void
	 */
	@Override
	public void add(Account account) {
		cache.add(account);
	}
	
	/**
	 * charges the amount on user's {@link Account}
	 * 
	 * @param account user account
	 * @return long account balance
	 * @throws @{link PaymentException} if provided amount is invalid or does not have sufficient balance.
	 */
	@Override
	public long charge(Account account) {
		
		Account cacheAccount = cache.get(account.getUserName());
		if(cacheAccount == null) {
			String msg = String.format("Account - %s is invalid", account.getUserName());
			throw new InvalidAccountException(msg);
		}
		
		long charge = account.getAmount();
		long balance = cacheAccount.getAmount();
		
		if(charge > balance) {
			throw new InsufficientFundsException("Unable to 'Charge' due to insufficent balance");
		}
		 balance -= charge;
		cacheAccount.setAmount(balance);
		return balance;
	}
	
	/**
	 * credits the amount to user's {@link Account}
	 * 
	 * @param account user account
	 * @return long account balance
	 * @throws @{link PaymentException} if provided amount is invalid.
	 */
	@Override
	public long credit(Account account) {
		
		Account cacheAccount = cache.get(account.getUserName());
		if(cacheAccount == null) {
			String msg = String.format("Account - %s is invalid", account.getUserName());
			throw new InvalidAccountException(msg);
		}
		
		long totalAmount = cacheAccount.getAmount() + account.getAmount();
		cacheAccount.setAmount(totalAmount);
		return totalAmount;
	}

}
