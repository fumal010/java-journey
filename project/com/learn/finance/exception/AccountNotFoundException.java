package com.learn.finance.exception;

public class AccountNotFoundException extends FinanceException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
