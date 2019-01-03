package org.o7planning.sbdatajpa.repository;
 
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.o7planning.sbdatajpa.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
// This is an Interface.
// No need Annotation here.
public interface AccountRepository extends CrudRepository<Account, BigInteger> { 
 
    Account findByAccountNumber(BigInteger accountNumber);//Question b
 
    List<Account> findByAccountType1(String accountType1);//Question c
 
 
   
}