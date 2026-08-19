package com.learn.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.learn.finance.enums.*;
import com.learn.finance.utils.FormatCheck;

public class Transaction {

    private static int sequence = 0;

    private final String id;

    private String description;

    private BigDecimal amount;

    private TransactionType type;

    private Category category;

    private LocalDate date;

    private String accountId;

    private String toAccountId;

    private TransactionStatus status;

    {
        sequence++;
    }


    public Transaction(String description, BigDecimal amount, TransactionType type, Category category, LocalDate date, String accountId, String toAccountId) {

        this.id = "TXN-" + sequence;

        if (FormatCheck.isNullOrBlank(description)) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (!FormatCheck.isPositive(amount)) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (FormatCheck.isNull(type)) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (FormatCheck.isNull(category)) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (FormatCheck.isNull(date)) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (FormatCheck.isNullOrBlank(accountId)) {
            throw new IllegalArgumentException("Account ID cannot be null or blank");
        }
        if (isTransfer() && FormatCheck.isNullOrBlank(toAccountId)) {
            throw new IllegalArgumentException("To Account ID is required for transfer type");
        }

        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.accountId = accountId;
        this.toAccountId = toAccountId;
        this.status = TransactionStatus.PENDING;
    }



    private boolean isTransfer() {
        return type == TransactionType.TRANSFER;
    }

    // delete unused getters
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAccountId() {
        return accountId;
    }

    public Optional<String> getToAccountId() {
        return Optional.ofNullable(toAccountId);
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        if (FormatCheck.isNull(status)) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }



    @Override
    public String toString() {
        return "Transaction{id='" + id
                + "', date=" + date
                + ", type=" + type
                + ", category=" + category
                + ", amount=" + FormatCheck.formatMoney(amount)
                + ", status=" + status
                + ", desc='" + description + "'}";
    }
}
