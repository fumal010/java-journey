package com.learn.finance.model;

import com.learn.finance.enums.*;
import com.learn.finance.exception.InsufficientFundsException;
import com.learn.finance.exception.InvalidAmountException;
import com.learn.finance.utils.FormatCheck;

import java.math.BigDecimal;
import java.util.Optional;

public class Account {
    private static int sequence = 0;

    private final String id;

    private String name;

    private final AccountType type;

    private BigDecimal balance;

    private BigDecimal initialBalance;

    private final BigDecimal creditLimit;



    // public Account(String name, AccountType type, BigDecimal balance) {
    //     this(name, type, balance, creditLimit, null);
    // }

    public Account(String name, AccountType type, BigDecimal balance, BigDecimal creditLimit) {
        this.id = "ACC-" + sequence;

        if (FormatCheck.isNullOrBlank(name)) {
            throw new IllegalArgumentException("Name can't be null or blank");
        }

        if (type == AccountType.CREDIT && !FormatCheck.isPositive(balance)) {
            throw new IllegalArgumentException("Initial balance must be non-negative for CREDIT accounts");
        }

        if (type == AccountType.CREDIT && !FormatCheck.isPositive(creditLimit)) {
            throw new IllegalArgumentException("Credit limit must be positive for CREDIT accounts");
        }

        if (type != AccountType.CREDIT && !FormatCheck.isPositive(balance)) {
//            throw new InvalidAmountException("Initial balance must be non-negative for non-CREDIT accounts");
        }

        if (type != AccountType.CREDIT && !FormatCheck.isNull(creditLimit)) {
            throw new IllegalArgumentException("Credit limit must be null for non-CREDIT accounts");
        }

        this.name = name;
        this.type = type;
        this.balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO);
        this.initialBalance = this.balance;
        this.creditLimit = creditLimit;
    }

    public String getId() {
        return id;
    }

    // check unseded getters
    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Optional<BigDecimal> getCreditLimit() {
        return Optional.ofNullable(creditLimit);
    }

    private boolean isCredit() {
        return type == AccountType.CREDIT;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than 0");
        }
        balance = balance.add(amount);
    }


    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than 0");
        }

        if (!isCredit() && balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        if (isCredit() && balance.subtract(amount).compareTo(creditLimit.negate()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        balance = balance.subtract(amount);
    }


    private BigDecimal getAvailableBalance() {
        if (isCredit()) {
            return creditLimit.add(balance);
        } else {
            return balance;
        }
    }

    //only possible for CREDIT accounts
    private boolean isOverdrawn() {
        return balance.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account{id='").append(id)
                .append("', name='").append(name)
                .append("', type=").append(type)
                .append(", balance=").append(FormatCheck.formatMoney(balance));
        if (isCredit()) {
            sb.append(", creditLimit=").append(FormatCheck.formatMoney(creditLimit))
                    .append(", available=").append(FormatCheck.formatMoney(getAvailableBalance()));
        }
        sb.append("}");
        return sb.toString();
    }
}
