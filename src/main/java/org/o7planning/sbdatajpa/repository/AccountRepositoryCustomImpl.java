package org.o7planning.sbdatajpa.repository;
 
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
 
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
 
import org.o7planning.sbdatajpa.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
@Repository
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
 
    @Autowired
    EntityManager entityManager;
 
    //Question d
	@Override
	@Transactional
	public int updateBalance(BigInteger accountNumber, BigDecimal amount) {
		// TODO Auto-generated method stub
		Account e = entityManager.find(Account.class, accountNumber);
        if (e == null) {
            return 0;
        }
        System.out.println("INside AccountRepositoryCustomImpl's updateBalance");
        if(e.getStatus()==('A'))
        {
        	e.setBalance(amount);
            entityManager.flush();
            return 1;
        }
        return 0;
        
  	}

	//Question e
	@Override
	@Transactional
	public int updateAccountStatus(BigInteger accountNumber, char status) {
		// TODO Auto-generated method stub
		Account e = entityManager.find(Account.class, accountNumber);
        if (e == null) {
            return 0;
        }
        System.out.println("Inside AccountRepositoryCustomImpl's updateAccountStatus");
        
        if(status=='A'||status=='C'||status=='F')
        {
        	e.setStatus(status);
        	System.out.println("e's status is: "+e.getStatus());
            entityManager.flush();
            return 1;
        }
        return 0;//invalid value
        
	}
    
	@Override
	@Transactional
	public int saveAccount(Account act) {
		// TODO Auto-generated method stub
		Account e = entityManager.find(Account.class, act.getAccountNumber());
        if (e != null) {
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
	        		entityManager.persist(e);
	        	}
	        	catch(Exception ex)
	        	{
	        		System.out.println(ex.getMessage());
	        	}
	            return 1;
        	}
        }
        return 0;//invalid value
        
	}
}