package com.leniolabs.challenge.controller;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3390503923455136411L;

	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotFoundException(String message) {
		super(message);
	}

	public AccountNotFoundException(Throwable cause) {
		super(cause);
	}

}
