package com.leniolabs.challenge.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.calculator.factory.FeeCalculatorFactory;
import com.leniolabs.challenge.custom.AccountType;
import com.leniolabs.challenge.model.Account;
import com.leniolabs.challenge.service.AccounServiceIF;

@RestController
@RequestMapping("/lenio-challenge/account/v1")
public class AccountController {
	
	@Value("${bad.request.exception}")
	private String badRequestException;
	
	@Value("${not.found.exception}")
	private String notFoundException;	

	@Autowired
	private AccounServiceIF accountControllerService;

	@Autowired
	private FeeCalculatorFactory feeCalculatorFactory;

	@PostMapping(value = "/create")
	public ResponseEntity<String> createAccount(@RequestBody Account account) {
		String accountId = accountControllerService.createAccount(account);
		return ResponseEntity.ok(accountId);
	}

	@GetMapping(value = "/calculate-fee/{id}")
	public ResponseEntity<Double> calculateFee(@PathVariable String id) throws Exception {
		Double result = null;
		Optional<Account> account = accountControllerService.findById(id);
		FeeCalculatorIF feeCalculator = feeCalculatorFactory.createFeeCalculator(AccountType.class, 
				account.get().getAccountType());

		if (id.isEmpty() || feeCalculator == null) {
			throw new Exception(badRequestException);
		} else {
			result = feeCalculator.calculateFee();

			if (result == null) {
				throw new AccountNotFoundException(notFoundException);
			}
		}

		return new ResponseEntity<Double>(result, HttpStatus.OK);
	}

}
