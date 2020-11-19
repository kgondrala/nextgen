package com.nextgen.payments.account.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nextgen.payments.account.dao.Account;

/**
 * An implementation of {@link AccountCache}.
 * This holds all the {@link Account}s in memory and acts like a in-memory DB.
 * 
 * @author kgondrala
 *
 */
@Component
public class AccountCacheImpl implements AccountCache {

	private Map<String, Account> map = new HashMap<>();
	
	@Override
	public void add(Account account) {
		map.put(account.getUserName(), account);
	}
	
	@Override
	public Account get(String userName) {
		return map.get(userName);
	}
	
	@Override
	public void update(Account account) {
		map.put(account.getUserName(), account);
	}
	
}
