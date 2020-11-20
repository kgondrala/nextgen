package com.nextgen.payments.account.service;

import static com.nextgen.payments.account.service.AccountMapper.toModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextgen.payments.account.dao.Account;
import com.nextgen.payments.account.dao.AccountDao;
import com.nextgen.payments.account.dto.AccountActionDTO;
import com.nextgen.payments.exception.InsufficientFundsException;
import com.nextgen.payments.exception.InvalidAccountException;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public void add(AccountActionDTO dto) {
		Account a = toModel(dto);
		accountDao.create(a);
	}
	
	/**
	 * @throws @{link PaymentException} if provided amount is invalid or does not have sufficient balance.
	 */
	@Override
	public long charge(AccountActionDTO dto) {
		
		Account a = accountDao.get(dto.getUserName(), dto.getCardType().toString());
		if(a == null) {
			String msg = String.format("Account - %s is invalid", dto.getUserName());
			throw new InvalidAccountException(msg);
		}
		
		long totalAmount = a.getAmount() + dto.getAmount();
		a.setAmount(totalAmount);
		accountDao.update(a);
		return totalAmount;
	}
	
	/**
	 * 
	 * @throws @{link PaymentException} if provided amount is invalid.
	 */
	@Override
	public long credit(AccountActionDTO dto) {

		Account a = accountDao.get(dto.getUserName(), dto.getCardType().toString());
		
		if(a == null) {
			String msg = String.format("Account - %s is invalid", dto.getUserName());
			throw new InvalidAccountException(msg);
		}
		
		long charge = dto.getAmount();
		long balance = a.getAmount();
		
		if(charge > balance) {
			throw new InsufficientFundsException("Unable to 'Charge' due to insufficent balance");
		}
		 balance -= charge;
		a.setAmount(balance);
		accountDao.update(a);
		return balance;
	}
}
