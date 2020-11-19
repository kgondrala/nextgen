package com.nextgen.payments.account.cache;

import com.nextgen.payments.account.dao.Account;

/**
 * {@link Account} cache. Acts like DB.
 * 
 * @author kgondrala
 *
 */
public interface AccountCache {

	void add(Account account);
	
	Account get(String userName);
	
	void update(Account account);

}
