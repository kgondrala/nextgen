package com.nextgen.payments.account.service;

import com.nextgen.payments.account.dto.AccountActionDTO;

public interface AccountService {

	void add(AccountActionDTO request);
	
	long charge(AccountActionDTO request);
	
	long credit(AccountActionDTO request);
}
