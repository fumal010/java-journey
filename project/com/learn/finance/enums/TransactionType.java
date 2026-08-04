package com.learn.finance.enums;

public enum TransactionType {
    INCOME() {
        @Override
        public boolean isDebit() {
            return false;
        }
    },
    EXPENSE() {
        @Override
        public boolean isDebit() {
            return true;
        }
    },
    TRANSFER() {
        @Override
        public boolean isDebit() {
            return true;
        }
    };


   public abstract boolean isDebit();
}
