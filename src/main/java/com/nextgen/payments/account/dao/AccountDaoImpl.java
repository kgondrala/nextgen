package com.nextgen.payments.account.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextgen.payments.account.cache.AccountCache;

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
	public void create(Account account) {
		cache.add(account);
	}
	
	/**
	 * update the {@link Account} to Cache.
	 * 
	 * @param account user account
	 * @return void
	 */
	@Override
	public void update(Account account) {
		cache.update(account);
	}
	
	/**
	 * gets {@link Account} given AccountId
	 * 
	 * @param accountId User Account Id
	 * @return account user account
	 */
	@Override
	public Account get(String userName, String cardType) {
		return cache.get(userName, cardType);
	}

}
