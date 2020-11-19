package com.nextgen.payments.account.dao;

/**
 * an interface to perform CRUD operations on {@link Account}
 * 
 * @author kgondrala
 *
 */
public interface AccountDao {

	void add(Account account);
	
	long charge(Account account);
	
	long credit(Account account);

}
