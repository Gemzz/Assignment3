package org.o7planning.sbdatajpa.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.o7planning.sbdatajpa.entity.Account;
import org.o7planning.sbdatajpa.repository.AccountRepository;
import org.o7planning.sbdatajpa.repository.AccountRepositoryCustomImpl;
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
 
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountRepositoryCustomImpl accountRepositoryCustomImpl;
 
 
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

    @RequestMapping(value = "/addAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateStatus(@RequestBody Account account) {
		int result = accountRepositoryCustomImpl.saveAccount(account);
		System.out.println("The result in addAccount of MainController is "+result);
		if (result == 0) {
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
			//return "Adding the account has failed";
		}
		System.out.println("After calling the other method in controller");
		ResponseEntity<Account> act=getUser(account.getAccountNumber());
		System.out.println("After calling the other method in controller AGAIn");
		return act;
		//return new ResponseEntity<Account>(account,HttpStatus.OK);
		//return "Account has been successfully added to the database";
	}
    
    @RequestMapping(value = "/getAccountGivenAccountType/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> getAccountWithType(@PathVariable("type") String type) {
		List<Account> act = accountRepository.findByAccountType1(type);
		if (act == null) {
			return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Account>>(act, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/getAccountGivenAccountNumber/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> getUser(@PathVariable("number") BigInteger number) {
		Account act = accountRepository.findByAccountNumber(number);
		if (act == null) {
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Account>(act, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/updateBalanceAmount/{number}/{amount}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateStatus(@PathVariable("number") BigInteger accountNumber,@PathVariable("amount") BigDecimal amount) {
		int result = accountRepositoryCustomImpl.updateBalance(accountNumber, amount);
		System.out.println("The status is "+amount);
		System.out.println("The result in updateBalanceAmount of MainController is "+result);
		if (result == 0) {
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<Account> act=getUser(accountNumber);
		System.out.println("After calling the other method in controller");
		return act;
	}
    
    @RequestMapping(value = "/updateAccountStatus/{number}/{status}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateStatus(@PathVariable("number") BigInteger accountNumber,@PathVariable("status") char status) {
		int result = accountRepositoryCustomImpl.updateAccountStatus(accountNumber, status);
		System.out.println("The status is "+status);
		System.out.println("The result in updateAccountStatus of MainController is "+result);
		if (result == 0) {
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<Account> act=getUser(accountNumber);
		System.out.println("After calling the other method in controller");
		return act;
	}
}