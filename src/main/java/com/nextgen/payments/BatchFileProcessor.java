package com.nextgen.payments;

import static com.nextgen.payments.BatchProcessConstants.RECORD_FIELD_DELIMITER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nextgen.payments.account.dto.AccountActionDTO;
import com.nextgen.payments.account.service.AccountService;
import com.nextgen.payments.account.validator.AccountValidator;
import com.nextgen.payments.exception.PaymentException;

/**
 * processes the NextGen Payments Batch file.
 * 
 * @author kgondrala
 */
@Component
public class BatchFileProcessor {

	private static final Logger logger = Logger.getLogger(BatchFileProcessor.class);
	
	@Autowired
	private AccountService accountService;
	
	public void process(String fileName) {

		Map<String, List<AccountActionDTO>> map = readBatchFile(fileName);
		if(map == null) {
			return;
		}
		
		Map<String, Map<String, String>>  batchResponse = process(map);
		
		printBatchResponse(batchResponse);
	}
	
	/**
	 * reads data from the input file and returns AccountAction objects to further process.
	 * 
	 * @param fileName batch file name
	 * @return map Map<String, List<AccountActionDTO>>
	 */
	private Map<String, List<AccountActionDTO>> readBatchFile(String fileName) {
		
		Map<String, List<AccountActionDTO>> map = new HashMap<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			logger.info("\n\n\n");
			while ((line = br.readLine()) != null) {
				
				if(line == null || line.isBlank()) {
					continue;
				}
				logger.info(line);
				AccountActionDTO dto = toRecord(line);
				
				if(!map.containsKey(dto.getUserName())) {
					map.put(dto.getUserName(), new ArrayList<>());
				}
				map.get(dto.getUserName()).add(dto);
			}

		} catch (IOException e) {
			logger.error("Unable to read the file " + fileName, e);
			return null;
		}
		
		return map;
	}
	
	/**
	 * this method processes all the Account action requests and returns the batch response 
	 * @param list List of Account Action requests
	 * @return map Map of AccountIds and its Account Action response
	 */
	private Map<String, Map<String, String>> process(Map<String, List<AccountActionDTO>> map) {

		Map<String, Map<String, String>> batchResponse = new HashMap<>();
		
		for(String userName: map.keySet()) {
			
			List<AccountActionDTO> list = map.get(userName);
			for(AccountActionDTO dto : list) {
				String response = null;
				
				switch(dto.getType()) {
					case Add: response = add(dto); break;
					case Charge: response = charge(dto); break;
					case Credit: response = credit(dto); break;
				}
				Map<String, String> m = batchResponse.get(userName);
				if(m == null) {
					m = new HashMap<>();
					batchResponse.put(userName, m);
				}
				m.put(dto.getCardType().toString(), response);
			}
		}
		
		return batchResponse;
	}
	
	/**
	 * Adds an account with the give information
	 * 
	 * @param dto {@link AccountActionDTO}
	 * @return string status
	 */
	private String add(AccountActionDTO dto) {
		
		String status = "success";
		try {
			
			AccountValidator.validateCardNumber(dto.getCardNumber());
			accountService.add(dto);
			
		} catch(PaymentException e) {
			// logger.error("add -->" + e.getMessage());
			status = "error";
		}
		return status;
	}
	
	/**
	 * charges on the account for the requested amount
	 * 
	 * @param dto {@link AccountActionDTO}
	 * @return string status
	 */
	private String charge(AccountActionDTO dto) {
		String status = null;
		try {
			long balance = accountService.charge(dto);
			status = "$" + balance;
		} catch(PaymentException e) {
			// logger.error("charge -->" + e.getMessage());
			status = "error";
		}
		return status;
	}
	
	/**
	 * credits to the account for the requested amount
	 * 
	 * @param dto {@link AccountActionDTO}
	 * @return  string status 
	 */
	private String credit(AccountActionDTO dto) {
		String status = null;
		try {
			long balance = accountService.credit(dto);
			status = "$" + balance;
		} catch(PaymentException e) {
			// logger.error("credit -->" + e.getMessage());
			status = "error";
		}
		return status;
	}
	
	/**
	 * prints the batch response to the console
	 * 
	 * @param map Map of Account Ids and its Account Action responses
	 */
	private void printBatchResponse(Map<String, Map<String, String>> map) {
		
		logger.info("\n\n");
		
		if(map != null) {
			List<String> list = new ArrayList<>(map.keySet());
			Collections.sort(list);
			
			for (String userName : list) {
				String str = "";
				Map<String, String> accountMap = map.get(userName);
				for(String key: accountMap.keySet()) {
					str += String.format(" (%s) %s,", key, accountMap.get(key));
				}
				logger.info(String.format("%s: %s", userName, str));
			}
		}
	}
	
	/**
	 * converts the each batch file line to an {@link AccountActionDTO} object.
	 * 
	 * @param line record from the batch file
	 * @return AccountActionDTO
	 */
	private AccountActionDTO toRecord(String line) {
		String[] arr = line.split(RECORD_FIELD_DELIMITER);
		String type = arr[0];
		String userName = arr[1];
		String cardType = arr[2];
		String cardNumber = null;
		Long amount = null;

		if (arr.length == 4) {
			amount = Long.valueOf(arr[3].substring(1));
		} else if (arr.length == 5) {
			cardNumber = arr[3];
			amount = Long.valueOf(arr[4].substring(1));
		}

		AccountActionDTO record = new AccountActionDTO.Builder()
								.withType(type)
								.userName(userName)
								.cardNumber(cardNumber)
								.amount(amount)
								.cardType(cardType)
								.build();
		
		return record;
	}

}
