package com.nextgen.payments.account.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nextgen.payments.account.dao.Account;

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
}
