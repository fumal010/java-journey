package com.learn.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import com.learn.finance.enums.Category;
import com.learn.finance.model.Transaction;

public class MonthlySummary {

    private final BigDecimal totalIncome;
    private final BigDecimal totalExpenses;
    private final BigDecimal netSavings;
    private final Map<Category, BigDecimal> expensesByCategory;
    private final Map<Category, BigDecimal> incomeByCategory;
    private final Transaction largestExpense;
    private final Transaction largestIncome;

    public MonthlySummary(
            BigDecimal totalIncome,
            BigDecimal totalExpenses,
            BigDecimal netSavings,
            Map<Category, BigDecimal> expensesByCategory,
            Map<Category, BigDecimal> incomeByCategory,
            Transaction largestExpense,
            Transaction largestIncome) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.netSavings = netSavings;
        this.expensesByCategory = expensesByCategory;
        this.incomeByCategory = incomeByCategory;
        this.largestExpense = largestExpense;
        this.largestIncome = largestIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getNetSavings() {
        return netSavings;
    }

    public Map<Category, BigDecimal> getExpensesByCategory() {
        return expensesByCategory;
    }

    public Map<Category, BigDecimal> getIncomeByCategory() {
        return incomeByCategory;
    }

    public Optional<Transaction> getLargestExpense() {
        return Optional.ofNullable(largestExpense);
    }

    public Optional<Transaction> getLargestIncome() {
        return Optional.ofNullable(largestIncome);
    }

    private static String formatMoney(BigDecimal amount) {
        return "$" + amount.setScale(2, RoundingMode.HALF_UP);
    }
}
