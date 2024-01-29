package com.Medicinebookingsystem.exception;

public class InsufficientWalletBalanceException extends RuntimeException {

	
	//private String errorMessage;

	public InsufficientWalletBalanceException(String errorMessage) {
		super(errorMessage);
	
}
}