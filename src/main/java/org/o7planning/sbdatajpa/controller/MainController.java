package org.o7planning.sbdatajpa.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.o7planning.sbdatajpa.SpringBootDataJpaApplication;
import org.o7planning.sbdatajpa.entity.Account;
import org.o7planning.sbdatajpa.repository.AccountRepository;
import org.o7planning.sbdatajpa.repository.AccountRepositoryCustomImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;






 
@Controller
public class MainController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

 
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountRepositoryCustomImpl accountRepositoryCustomImpl;
 
    //IGNORE THIS HOME METHOD
    @ResponseBody
    @RequestMapping("/")
    public String home() {
        String html = "";
        html += "<ul>";
        html += " <li><a href='/addAccount'>Add Account</a></li>";
        html += " <li><a href='/getAccountGivenAccountNumber'>Fetch Account Given Account Number</a></li>";
        html += " <li><a href='/getAccountGivenAccountType'>Fetch Accounts Given Account Type</a></li>";
        html += " <li><a href='/updateBalanceAmount'>Update Balance Amount</a></li>";
        html += " <li><a href='/updateAccountStatus'>Update Account Status</a></li>";
        html += "</ul>";
        return html;
    }
    
    
    //ADD ACCOUNT : RETURN JSON ACCOUNT
    @RequestMapping(value = "/addAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateStatus(@Valid @RequestBody Account account) {
    	LOGGER.info("---- Main Controller: Add Account");
    	int result = accountRepositoryCustomImpl.saveAccount(account);
		System.out.println("The result in addAccount of MainController is "+result);
		if (result == 0) {
			LOGGER.info("--- Adding the account has failed.");
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
			//return "Adding the account has failed";
		}
		LOGGER.info("--- Adding the account was successful.");
		
		System.out.println("After calling the other method in controller");
		ResponseEntity<Account> act=getUser(account.getAccountNumber());
		System.out.println("After calling the other method in controller AGAIn");
		return act;
		//return new ResponseEntity<Account>(account,HttpStatus.OK);
		//return "Account has been successfully added to the database";
	}
    
    //GET ACCOUNT GIVEN ACCOUNT TYPE : RETURN JSON ACCOUNT LIST
    @RequestMapping(value = "/getAccountGivenAccountType/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> getAccountWithType(@PathVariable("type") String type) {
    	LOGGER.info("--- Main Controller: Get Account Given Account Type");
    	List<Account> act = accountRepository.findByAccountType1(type);
		if (act == null) {
			LOGGER.info("--- Getting the account by type has failed.");
			return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("--- Getting the account by type was a success.");
		return new ResponseEntity<List<Account>>(act, HttpStatus.OK);
	}
    
    //GET ACCOUNT GIVEN ACCOUNT NUMBER: RETURN JSON ACCOUNT
    @RequestMapping(value = "/getAccountGivenAccountNumber/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> getUser(@PathVariable("number") BigInteger number) {
    	LOGGER.info("--- Main Controller's Get Account Given Account Number");
    	Account act = accountRepository.findByAccountNumber(number);
		if (act == null) {
			LOGGER.info("--- Getting account given account number has failed.");
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("--- Getting account given account number was a success.");
		return new ResponseEntity<Account>(act, HttpStatus.OK);
	}
    
    //UPDATE BALANCE AMOUNT : RETURN JSON ACCOUNT
    @RequestMapping(value = "/updateBalanceAmount/{number}/{amount}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateStatus(@PathVariable("number") BigInteger accountNumber,@PathVariable("amount") BigDecimal amount) {
    	LOGGER.info("--- Main Controller: Update Balance Amount");
    	int result = accountRepositoryCustomImpl.updateBalance(accountNumber, amount);
		System.out.println("The status is "+amount);
		System.out.println("The result in updateBalanceAmount of MainController is "+result);
		if (result == 0) {
			LOGGER.info("--- Updating balance amount has failed.");
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("--- Updating balance amount was a success.");
		ResponseEntity<Account> act=getUser(accountNumber);
		System.out.println("After calling the other method in controller");
		return act;
	}
    
    //UPDATE ACCOUNT STATUS - RETURN JSON ACCOUNT
    @RequestMapping(value = "/updateAccountStatus/{number}/{status}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateStatus(@PathVariable("number") BigInteger accountNumber,@PathVariable("status") char status) {
    	LOGGER.info("--- Main Controller: Update Account Status");
    	
    	//Control flow was going into the commented code for some reason, even though the status is 'A' and it is a character.
    	//Doing the status check in the repo instead.
		/*
		 * if(status!='A'||status!='C'||status!='F')//having to do manual check
		 * since @Pattern does not take char {
		 * LOGGER.info("--- Checking if status has a valid value. It doesn't. {}, {}"
		 * ,status, Character.getType(status)); return new
		 * ResponseEntity<Account>(HttpStatus.BAD_REQUEST); }
		 */
    	int result = accountRepositoryCustomImpl.updateAccountStatus(accountNumber, status);
		System.out.println("The status is "+status);
		System.out.println("The result in updateAccountStatus of MainController is "+result);
		if (result == 0) {
			LOGGER.info("--- Updating account status has failed.");
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("--- Updating account status was a success.");
		ResponseEntity<Account> act=getUser(accountNumber);
		System.out.println("After calling the other method in controller");
		return act;
	}
}