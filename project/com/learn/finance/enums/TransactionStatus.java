package com.learn.finance.enums;

public enum TransactionStatus {
    PENDING,
    SUCCEEDED,
    FAILED;

    public boolean isSucceeded() {
        return this == SUCCEEDED;
    }

    public boolean isFailed() {
        return this == FAILED;
    }
}
