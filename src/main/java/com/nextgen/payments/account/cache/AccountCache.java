package com.nextgen.payments.account.cache;

import com.nextgen.payments.account.dao.Account;

public interface AccountCache {

	void add(Account account);
	
	Account get(String userName);

}
