package org.o7planning.sbdatajpa.repository;
 
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
 
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.o7planning.sbdatajpa.controller.MainController;
import org.o7planning.sbdatajpa.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
@Repository
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryCustomImpl.class);

    @Autowired
    EntityManager entityManager;
 
    //Question d
	@Override
	@Transactional
	public int updateBalance(BigInteger accountNumber, BigDecimal amount) {
		// TODO Auto-generated method stub
		LOGGER.info("//--- Updating Balance");
		Account e = entityManager.find(Account.class, accountNumber);
        if (e == null) {
        	LOGGER.info("//--- Updating balance has failed.");
            return 0;
        }
        System.out.println("INside AccountRepositoryCustomImpl's updateBalance");
        if(e.getStatus()==('A'))
        {
        	e.setBalance(amount);
            entityManager.flush();
            LOGGER.info("//--- Updating balance was a success.");
            return 1;
        }
        else if(e.getStatus()=='F')//can't change balance in F
        {
        	LOGGER.info("//--- Can not update bank balance since it is frozen.");
        	return 0;
        }
        LOGGER.info("//--- Updating balance has failed.");
        return 0;
        
  	}

	//Question e
	@Override
	@Transactional
	public int updateAccountStatus(BigInteger accountNumber, char status) {
		// TODO Auto-generated method stub
		Account e = entityManager.find(Account.class, accountNumber);
        if (e == null) {
        	LOGGER.info("//--- Updating account status has failed since account DNE.");
            return 0;
        }
        System.out.println("Inside AccountRepositoryCustomImpl's updateAccountStatus");
        
        if(status=='A'||status=='C'||status=='F')
        {
        	e.setStatus(status);
        	System.out.println("e's status is: "+e.getStatus());
            entityManager.flush();
            LOGGER.info("//--- Updating account status was a success.");
            return 1;
        }
        LOGGER.info("//--- Updating account status has failed since not valid status value.");
        return 0;//invalid value
        
	}
    
	@Override
	@Transactional
	public int saveAccount(Account act) {
		// TODO Auto-generated method stub
		Account e = entityManager.find(Account.class, act.getAccountNumber());
        if (e != null) {
        	LOGGER.info("//--- Adding account has failed since it already exists.");
            return 0;// invalid since the account already exists
        }
        System.out.println("Inside AccountRepositoryCustomImpl's saveAccount");
        
        if((act.getStatus()=='A'||act.getStatus()=='C'||act.getStatus()=='F'))
        {
        	int result=act.getBalance().compareTo(BigDecimal.ZERO);//checking if the balance is greater than zero
        	if(result!=-1)
        	{
	        	e=act;// this is working since e's status is printing correctly
	        	System.out.println("e's status is: "+e.getStatus());
	        	try 
	        	{
	        		//entityManager.flush();
	        		System.out.println("Inside the try-catch block");
	        		entityManager.persist(e);
	        		System.out.println("Exiting the try-catch");
	        	}
	        	catch(Exception ex)
	        	{
	        		System.out.println(ex.getMessage());
	        	}
	        	LOGGER.info("//--- Adding account was a success.");
	            return 1;
        	}
        }
        LOGGER.info("//--- Adding account has failed.");
        return 0;//invalid value
        
	}
}