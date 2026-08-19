package com.learn.finance.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.learn.finance.enums.Category;

public class Budget {

    private final int year;
    private final int month;

    private final Map<Category, BudgetEntry> entries = new EnumMap<>(Category.class);

    public Budget(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public void setBudget(Category category, BigDecimal amount) {
        entries.put(category, new BudgetEntry(category, amount));
    }

    public void recordExpense(Category category, BigDecimal amount) {
        BudgetEntry entry = entries.get(category);
        if (entry == null) {
            entry = new BudgetEntry(category, amount);
            entries.put(category, entry);
        }
        entry.addActual(amount);
    }

    // empty when no budget was set for that category
    public Optional<BudgetEntry> getEntry(Category category) {
        return Optional.ofNullable(entries.get(category));
    }

    public List<BudgetEntry> getOverBudgetCategories() {
        List<BudgetEntry> overBudget = new ArrayList<>();
        for (BudgetEntry entry : entries.values()) {
            if (entry.isOverBudget()) {
                overBudget.add(entry);
            }
        }
        return overBudget;
    }

    public BigDecimal getTotalPlanned() {
        BigDecimal total = BigDecimal.ZERO;
        for (BudgetEntry entry : entries.values()) {
            total = total.add(entry.getPlannedAmount());
        }
        return total;
    }

    public BigDecimal getTotalActual() {
        BigDecimal total = BigDecimal.ZERO;
        for (BudgetEntry entry : entries.values()) {
            total = total.add(entry.getActualAmount());
        }
        return total;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

}
