package com.nextgen.payments.account.service;

import static com.nextgen.payments.account.service.AccountMapper.toModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextgen.payments.account.dao.Account;
import com.nextgen.payments.account.dao.AccountDao;
import com.nextgen.payments.account.dto.AccountActionDTO;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public void add(AccountActionDTO dto) {
		Account a = toModel(dto);
		accountDao.add(a);
	}
	
	@Override
	public long charge(AccountActionDTO dto) {
		Account a = toModel(dto);
		return accountDao.charge(a);
	}
	
	@Override
	public long credit(AccountActionDTO dto) {
		Account a = toModel(dto);
		return accountDao.credit(a);
	}
}
