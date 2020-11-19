package com.nextgen.payments;

import static com.nextgen.payments.BatchProcessConstants.RECORD_FIELD_DELIMITER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nextgen.payments.account.dto.AccountActionDTO;
import com.nextgen.payments.account.service.AccountService;
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

		List<AccountActionDTO> list = readBatchFile(fileName);
		if(list == null) {
			return;
		}
		
		printBatchFile(list);
		
		List<String>  batchResponse = process(list);
		printBatchResponse(batchResponse);
	}
	
	/**
	 * reads data from the input file and returns AccountAction objects to further process.
	 * 
	 * @param fileName batch file name
	 * @return list List<AccountActionDTO>
	 */
	private List<AccountActionDTO> readBatchFile(String fileName) {
		List<AccountActionDTO> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				list.add(toRecord(line));
			}

		} catch (IOException e) {
			logger.error("Unable to read the file " + fileName, e);
			return null;
		}
		
		return list;
	}
	
	/**
	 * this method processes all the Account action requests and returns the batch response 
	 * @param list List of Account Action requests
	 * @return list List of Account Action responses
	 */
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
	
	/**
	 * Adds an account with the give information
	 * 
	 * @param dto {@link AccountActionDTO}
	 * @return string status
	 */
	private String add(AccountActionDTO dto) {
		
		String status = "success";
		try {
			accountService.add(dto);
			
		} catch(PaymentException e) {
			status = "error";
		}
		return String.format("%s %s %s", dto.getType().toString(), dto.getUserName(), status);
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
			status = "error";
		}
		return String.format("%s %s %s", dto.getType().toString(), dto.getUserName(), status);
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
			status = "error";
		}
		return String.format("%s %s %s", dto.getType().toString(), dto.getUserName(), status);
	}
	
	/**
	 * prints the Batch File content to the console
	 * 
	 * @param list List of {@link AccountActionDTO}
	 */
	private void printBatchFile(List<AccountActionDTO> list) {
		logger.info("\n\n-------------------- Batch File Start ----------");
		if(list != null) {
			for (AccountActionDTO record : list) {
				logger.info(record);
			}
		}
		logger.info("-------------------- Batch File End ----------");
	}
	
	/**
	 * prints the batch response to the console
	 * 
	 * @param list List of Account Action responses
	 */
	private void printBatchResponse(List<String> list) {
		logger.info("\n\n-------------------- Batch Response Start ----------");
		if(list != null) {
			for (String response : list) {
				logger.info(response);
			}
		}
		logger.info("-------------------- Batch Response End ----------");
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
