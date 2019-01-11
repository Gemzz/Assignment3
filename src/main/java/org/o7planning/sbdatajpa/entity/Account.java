package org.o7planning.sbdatajpa.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
//import java.util.Date;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
 
@Entity
@Table(name = "Account")
public class Account {
 
    //@Id
    //private Long id;
//--------------------------------
    @Column(name = "Full_Name", length = 128, nullable = false)
    @Size(min=1,max=128, message="Please enter a name.")
    @Pattern(regexp = "[a-zA-Z]+", message="Hi there! The name should only contain alphabets A-Z/a-z")
    private String name;
    
    @Id
    @Column(name = "Account_Number", length = 15, nullable = false)
    @Digits(integer=15, fraction=0, message="Please enter a 15 digit account number.")
	private BigInteger accountNumber; //length=15
    
    
    @Column(name = "Account_Type1", length = 128, nullable = false)
    @Pattern(regexp="(Deposit)|(PersonalLoan)|(HomeLoan)|(StudentLoan)")
	private String accountType1;//Deposit, Personal Loan, Home Loan, Student Loan
    
    //@Temporal(TemporalType.DATE)
    @Column(name = "Account_Open_Date", nullable = false)
    @Past(message="Please make sure that the Account Open Date is from the present/past.")//make sure that the account was opened in the past
	private LocalDate accountOpenDate;//yyyy-MM-dd
	
	 @Column(name = "Status", length = 1, nullable = false)
	 //@Pattern(regexp="A|C|F")//@Valid doesn't work for characters?
	 @NotNull(message="Please make sure that the status is 'A','C' or 'F'.")
	private char status;//A,C,F - Active, Closed, Freeze
	 
	 @Column(name = "Account_Type2", length = 128, nullable = false)
	 @Pattern(regexp="(Main)|(Authorized)")
	private String accountType2;//Main, Authorized
	 
	 @Column(name = "Balance", length = 18, nullable = false)
	 @Min(value=0, message="Hi there! Balance must be greater than or equal to 0")//works for BigDecimal
	private BigDecimal balance; //Number(18,2)
	
	 //Getters & Setters

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigInteger getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getAccountType1() {
		return accountType1;
	}


	public void setAccountType1(String accountType1) {
		this.accountType1 = accountType1;
	}


	public LocalDate getAccountOpenDate() {
		return accountOpenDate;
	}


	public void setAccountOpenDate(LocalDate accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}


	public char getStatus() {
		return status;
	}


	public void setStatus(char status) {
		this.status = status;
	}


	public String getAccountType2() {
		return accountType2;
	}


	public void setAccountType2(String accountType2) {
		this.accountType2 = accountType2;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	@Override
    public String toString() {
        return this.getAccountNumber() + ", " + this.getName();
    }
 
}