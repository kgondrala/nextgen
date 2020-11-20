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

	private Map<String, Map<String, Account>> map = new HashMap<>();
	
	@Override
	public void add(Account account) {
		String userName = account.getUserName();
		String cardType = account.getCardType().toString();
		
		Map<String, Account> accountMap = map.get(userName);
		if(accountMap == null) {
			accountMap = new HashMap<>();
			map.put(userName, accountMap);
		}
		map.get(userName).put(cardType, account);
	}
	
	@Override
	public Account get(String userName, String cardType) {
		Map<String, Account> accountMap = map.get(userName);
		return accountMap.get(cardType);
	}
	
	@Override
	public void update(Account account) {
		Map<String, Account> accountMap = map.get(account.getUserName());
		accountMap.put(account.getCardType().toString(), account);
	}
	
}
