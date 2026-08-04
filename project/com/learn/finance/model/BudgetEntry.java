package com.learn.finance.model;

import java.math.BigDecimal;

import com.learn.finance.enums.Category;
import com.learn.finance.utils.FormatCheck;

public class BudgetEntry {

    private final Category category;

    private BigDecimal plannedAmount; // — the monthly budget for this category

    private BigDecimal actualAmount; // — accumulated from recorded expenses

    public BudgetEntry(Category category, BigDecimal plannedAmount) {
        if (FormatCheck.isNull(category)) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (!FormatCheck.isPositive(plannedAmount)) {
            throw new IllegalArgumentException("Planned amount must be positive");
        }

        this.category = category;
        this.plannedAmount = plannedAmount;
        this.actualAmount = BigDecimal.ZERO;
    }

    public void addActual(BigDecimal amount) {
        if (!FormatCheck.isPositive(amount)) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.actualAmount = this.actualAmount.add(amount);
    }

    public BigDecimal getVariance() {
        return plannedAmount.subtract(actualAmount);
    }

    public boolean isOverBudget() {
        return actualAmount.compareTo(plannedAmount) > 0;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPlannedAmount() {
        return plannedAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

//    @Override toString(): "BudgetEntry{category=FOOD, planned=$500.00, actual=$620.00, variance=-$120.00, OVER BUDGET}"
}
