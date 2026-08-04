package com.learn.finance.enums;

public enum AccountType {
    CHECKING() {
        @Override
        public boolean allowsNegativeBalance() {
            return false;
        }
    },
    SAVINGS() {
        @Override
        public boolean allowsNegativeBalance() {
            return false;
        }
    },
    CREDIT() {
        @Override
        public boolean allowsNegativeBalance() {
            return true;
        }
    };

    public abstract boolean allowsNegativeBalance();
}
