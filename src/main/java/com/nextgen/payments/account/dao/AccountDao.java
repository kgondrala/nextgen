package com.nextgen.payments.account.dao;

/**
 * an interface to perform CRUD operations on {@link Account}
 * 
 * @author kgondrala
 *
 */
public interface AccountDao {

	void create(Account account);
	
	Account get(String accountId);
	
	void update(Account account);

}
