package com.learn.bank;

public class BankAccount {


    private final String accountId;
    private double balance = 0;

    public BankAccount(String accountId) {
        this.accountId = accountId;
    }

    public void deposit(double amount) {
      if(amount <= 0 ) {
          throw new IllegalArgumentException("Deposit amount must be greater than 0");
      }

      balance += amount;
    }

    public void withdraw(double amount) {
    if(amount <=0) {
        throw new IllegalArgumentException("Withdraw amount must be greater than 0");
    }

    if(balance - amount <= 0) {
        throw new IllegalStateException("Insufficient balance: balance is " + balance);
    }
        balance -= amount;
    }


    public double getBalance() {
        return balance;
    }

    void auditReset() {
        balance = 0;
    }

    public String toString() {
        return "Account ID: " + accountId + ", Balance: " + balance;
    }
}