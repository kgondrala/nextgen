package com.nextgen.payments;

import static com.nextgen.payments.BatchProcessConstants.RECORD_FIELD_DELIMITER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nextgen.payments.account.dto.AccountActionDTO;
import com.nextgen.payments.account.service.AccountService;
import com.nextgen.payments.exception.PaymentException;

@Component
public class BatchFileProcessor {

	@Autowired
	private AccountService accountService;
	
	public void process(String fileName) {

		List<AccountActionDTO> list = readBatchFile(fileName);
		printBatchFile(list);
		
		List<String>  batchResponse = process(list);
		printBatchResponse(batchResponse);
	}
	
	
	private List<AccountActionDTO> readBatchFile(String fileName) {
		List<AccountActionDTO> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				list.add(toRecord(line));
			}

		} catch (IOException e) {
			e.printStackTrace(); // TODO
		}
		
		return list;

	}
	
	private List<String> process(List<AccountActionDTO> list) {

		List<String> batchResponse = new ArrayList<>();
		
		for(AccountActionDTO dto: list) {
			
			String response = null;
			
			switch(dto.getType()) {
				case Add: response = add(dto); break;
				case Charge: response = charge(dto); break;
				case Credit: response = credit(dto); break;
			}
			batchResponse.add(response);
		}
		
		return batchResponse;
	}
	
	private String add(AccountActionDTO dto) {
		
		String status = "success";
		try {
			accountService.add(dto);
			
		} catch(PaymentException e) {
			status = "error";
		}
		return String.format("%s %s %s", dto.getType().toString(), dto.getUserName(), status);
	}
	
	
	private String charge(AccountActionDTO dto) {
		String status = null;
		try {
			long balance = accountService.charge(dto);
			status = "$" + balance;
		} catch(PaymentException e) {
			status = "error";
		}
		return String.format("%s %s %s", dto.getType().toString(), dto.getUserName(), status);
	}
	
	private String credit(AccountActionDTO dto) {
		String status = null;
		try {
			long balance = accountService.credit(dto);
			status = "$" + balance;
		} catch(PaymentException e) {
			status = "error";
		}
		return String.format("%s %s %s", dto.getType().toString(), dto.getUserName(), status);
	}
	
	
	private void printBatchFile(List<AccountActionDTO> list) {
		System.out.println("\n\n-------------------- Batch File Start ----------");
		if(list != null) {
			for (AccountActionDTO record : list) {
				System.out.println(record);
			}
		}
		System.out.println("-------------------- Batch File End ----------");
	}
	
	private void printBatchResponse(List<String> list) {
		System.out.println("\n\n-------------------- Batch Response Start ----------");
		if(list != null) {
			for (String response : list) {
				System.out.println(response);
			}
		}
		System.out.println("-------------------- Batch Response End ----------");
	}
	

	private AccountActionDTO toRecord(String line) {
		String[] arr = line.split(RECORD_FIELD_DELIMITER);
		String type = arr[0];
		String userName = arr[1];
		String cardNumber = null;
		Long amount = null;

		if (arr.length == 3) {
			amount = Long.valueOf(arr[2].substring(1));
		} else if (arr.length == 4) {
			cardNumber = arr[2];
			amount = Long.valueOf(arr[3].substring(1));
		}

		AccountActionDTO record = new AccountActionDTO.Builder()
								.withType(type)
								.userName(userName)
								.cardNumber(cardNumber)
								.amount(amount)
								.build();
		return record;
	}

}
