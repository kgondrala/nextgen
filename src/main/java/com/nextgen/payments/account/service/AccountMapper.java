package com.nextgen.payments.account.service;

import com.nextgen.payments.account.dao.Account;
import com.nextgen.payments.account.dto.AccountActionDTO;

public class AccountMapper {

	public static Account toModel(AccountActionDTO dto) {
		Account a = new Account();
		a.setUserName(dto.getUserName());
		a.setCardNumber(dto.getCardNumber());
		a.setAmount(dto.getAmount());
		a.setCardType(dto.getCardType());
		return a;
	}
}
