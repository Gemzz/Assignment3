package org.o7planning.sbdatajpa.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.o7planning.sbdatajpa.entity.Account;
 
public interface AccountRepositoryCustom {
     
    public int updateBalance(BigInteger accountNumber, BigDecimal amount);//Question d (need to check status first)
    
    public int updateAccountStatus(BigInteger accountNumber, char status);//Question e
    
    public int saveAccount(Account act);//Question a
    
 
}