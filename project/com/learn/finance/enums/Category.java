package com.learn.finance.enums;

public enum Category {
    FOOD,
    HOUSING,
    TRANSPORT,
    ENTERTAINMENT,
    HEALTH,
    EDUCATION,
    SALARY,
    INVESTMENT,
    OTHER;

    public boolean isExpenseCategory() {
        return switch (this) {
            case FOOD, HOUSING, TRANSPORT, ENTERTAINMENT, HEALTH, EDUCATION -> true;
            default -> false;
        };
    }

    public boolean isIncomeCategory() {
        return switch (this) {
            case SALARY, INVESTMENT -> true;
            default -> false;
        };
    }
}
