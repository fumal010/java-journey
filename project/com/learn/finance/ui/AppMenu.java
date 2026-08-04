package com.learn.finance.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.learn.finance.enums.AccountType;
import com.learn.finance.enums.Category;
import com.learn.finance.enums.TransactionType;
import com.learn.finance.exception.AccountNotFoundException;
import com.learn.finance.exception.DuplicateAccountException;
import com.learn.finance.exception.FinanceException;
import com.learn.finance.exception.InsufficientFundsException;
import com.learn.finance.exception.InvalidAmountException;
import com.learn.finance.model.Account;
import com.learn.finance.model.Budget;
import com.learn.finance.model.BudgetEntry;
import com.learn.finance.model.Transaction;
import com.learn.finance.service.FinanceTracker;
import com.learn.finance.service.MonthlySummary;
import com.learn.finance.utils.FormatCheck;

public class AppMenu {

    private final FinanceTracker tracker;
    private final Scanner scanner;

    public AppMenu(FinanceTracker tracker) {
        this.tracker = tracker;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> addAccount();
                    case 2 -> viewAllAccounts();
                    case 3 -> recordIncome();
                    case 4 -> recordExpense();
                    case 5 -> transfer();
                    case 6 -> viewTransactions();
                    case 7 -> monthlySummary();
                    case 8 -> setMonthlyBudget();
                    case 9 -> budgetReport();
                    case 10 -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice — please enter 1–10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input — please enter a number");
            } catch (AccountNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (DuplicateAccountException e) {
                System.out.println(e.getMessage());
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            } catch (FinanceException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage() != null ? e.getMessage() : "An error occurred.");
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("===== Personal Finance Tracker =====");
        System.out.println("1. Add Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Record Income");
        System.out.println("4. Record Expense");
        System.out.println("5. Transfer Between Accounts");
        System.out.println("6. View Transactions");
        System.out.println("7. Monthly Summary");
        System.out.println("8. Set Monthly Budget");
        System.out.println("9. Budget Report");
        System.out.println("10. Exit");
    }

    // ----- Option 1 -----
    private void addAccount() {
        System.out.print("Account name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Account type:");
        System.out.println("  1=CHECKING");
        System.out.println("  2=SAVINGS");
        System.out.println("  3=CREDIT");
        System.out.print("Enter type: ");
        int typeChoice = Integer.parseInt(scanner.nextLine().trim());
        AccountType type = switch (typeChoice) {
            case 1 -> AccountType.CHECKING;
            case 2 -> AccountType.SAVINGS;
            case 3 -> AccountType.CREDIT;
            default -> throw new IllegalArgumentException("Invalid account type — enter 1, 2, or 3.");
        };

        System.out.print("Initial balance: ");
        BigDecimal balance = new BigDecimal(scanner.nextLine().trim());

        BigDecimal creditLimit = null;
        if (type == AccountType.CREDIT) {
            System.out.print("Credit limit: ");
            creditLimit = new BigDecimal(scanner.nextLine().trim());
        }

        Account account = tracker.addAccount(name, type, balance, creditLimit);
        System.out.println("Account created: " + account);
    }

    // ----- Option 2 -----
    private void viewAllAccounts() {
        List<Account> accounts = tracker.getAllAccounts();
        // Manual insertion sort by name alphabetically — O(n²)
        for (int i = 1; i < accounts.size(); i++) {
            Account key = accounts.get(i);
            int j = i - 1;
            while (j >= 0 && accounts.get(j).getName().compareToIgnoreCase(key.getName()) > 0) {
                accounts.set(j + 1, accounts.get(j));
                j--;
            }
            accounts.set(j + 1, key);
        }

        if (accounts.isEmpty()) {
            System.out.println("No accounts yet.");
        } else {
            for (Account account : accounts) {
                System.out.println(account);
            }
        }
        System.out.println("Total balance: " + FormatCheck.formatMoney(tracker.getTotalBalance()));
    }

    // ----- Option 3 -----
    private void recordIncome() {
        System.out.print("Account ID: ");
        String accountId = scanner.nextLine().trim();

        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

        Category category = promptCategory(true);

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        LocalDate date = promptDate();

        Transaction transaction = tracker.recordIncome(
                description, amount, TransactionType.INCOME, category, date, accountId);
        System.out.println("Income recorded: " + transaction);
    }

    // ----- Option 4 -----
    private void recordExpense() {
        System.out.print("Account ID: ");
        String accountId = scanner.nextLine().trim();

        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

        Category category = promptCategory(false);

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        LocalDate date = promptDate();

        Transaction transaction = tracker.recordExpense(
                description, amount, TransactionType.EXPENSE, category, date, accountId);
        System.out.println("Expense recorded: " + transaction);
    }

    // ----- Option 5 -----
    private void transfer() {
        System.out.print("From account ID: ");
        String fromId = scanner.nextLine().trim();

        System.out.print("To account ID: ");
        String toId = scanner.nextLine().trim();

        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        LocalDate date = promptDate();

        Account from = tracker.findById(fromId);
        Account to = tracker.findById(toId);
        tracker.transfer(fromId, toId, amount, description, date);
        System.out.println("Transfer recorded: " + FormatCheck.formatMoney(amount)
                + " from " + from.getName() + " to " + to.getName());
    }

    // ----- Option 6 -----
    private void viewTransactions() {
        System.out.println("Filter:");
        System.out.println("  1=All");
        System.out.println("  2=By Account");
        System.out.println("  3=By Category");
        System.out.println("  4=By Date Range");
        System.out.println("  5=By Type");
        System.out.print("Enter filter: ");
        int filter = Integer.parseInt(scanner.nextLine().trim());

        List<Transaction> list;
        switch (filter) {
            case 1 -> list = tracker.getTransactions();
            case 2 -> {
                System.out.print("Account ID: ");
                list = tracker.getTransactionsByAccount(scanner.nextLine().trim());
            }
            case 3 -> {
                Category category = promptAnyCategory();
                list = tracker.getTransactionsByCategory(category);
            }
            case 4 -> {
                System.out.print("From date (YYYY-MM-DD): ");
                LocalDate from = LocalDate.parse(scanner.nextLine().trim());
                System.out.print("To date (YYYY-MM-DD): ");
                LocalDate to = LocalDate.parse(scanner.nextLine().trim());
                list = tracker.getTransactionsByDateRange(from, to);
            }
            case 5 -> {
                System.out.println("  1=INCOME  2=EXPENSE  3=TRANSFER");
                System.out.print("Enter type: ");
                int typeChoice = Integer.parseInt(scanner.nextLine().trim());
                TransactionType type = switch (typeChoice) {
                    case 1 -> TransactionType.INCOME;
                    case 2 -> TransactionType.EXPENSE;
                    case 3 -> TransactionType.TRANSFER;
                    default -> throw new IllegalArgumentException("Invalid type — enter 1, 2, or 3.");
                };
                list = tracker.getTransactionsByType(type);
            }
            default -> throw new IllegalArgumentException("Invalid filter — enter 1–5.");
        }

        System.out.println("Sort by?");
        System.out.println("  1=Date");
        System.out.println("  2=Amount");
        System.out.println("  3=Category");
        System.out.print("Enter sort: ");
        int sort = Integer.parseInt(scanner.nextLine().trim());
        sortTransactions(list, sort);
        printTransactionTable(list);
    }

    private void sortTransactions(List<Transaction> list, int sort) {
        // Manual insertion sort — O(n²)
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compareTransactions(list.get(j), key, sort) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private int compareTransactions(Transaction a, Transaction b, int sort) {
        return switch (sort) {
            case 1 -> a.getDate().compareTo(b.getDate());
            case 2 -> a.getAmount().compareTo(b.getAmount());
            case 3 -> a.getCategory().name().compareTo(b.getCategory().name());
            default -> throw new IllegalArgumentException("Invalid sort — enter 1, 2, or 3.");
        };
    }

    private void printTransactionTable(List<Transaction> list) {
        System.out.println("| ID | Date | Type | Category | Amount | Description |");
        System.out.println("|----|------|------|----------|--------|-------------|");
        if (list.isEmpty()) {
            System.out.println("(no transactions)");
            return;
        }
        for (Transaction t : list) {
            System.out.printf("| %s | %s | %s | %s | %s | %s |%n",
                    t.getId(),
                    t.getDate(),
                    t.getType(),
                    t.getCategory(),
                    FormatCheck.formatMoney(t.getAmount()),
                    t.getDescription());
        }
    }

    // ----- Option 7 -----
    private void monthlySummary() {
        LocalDate today = LocalDate.now();

        System.out.print("Year [" + today.getYear() + "]: ");
        String yearInput = scanner.nextLine().trim();
        int year = yearInput.isEmpty() ? today.getYear() : Integer.parseInt(yearInput);

        System.out.print("Month (1–12) [" + today.getMonthValue() + "]: ");
        String monthInput = scanner.nextLine().trim();
        int month = monthInput.isEmpty() ? today.getMonthValue() : Integer.parseInt(monthInput);
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }

        MonthlySummary summary = tracker.getMonthlySummary(year, month);
        System.out.println(summary);
    }

    // ----- Option 8 -----
    private void setMonthlyBudget() {
        LocalDate today = LocalDate.now();

        System.out.print("Year [" + today.getYear() + "]: ");
        String yearInput = scanner.nextLine().trim();
        int year = yearInput.isEmpty() ? today.getYear() : Integer.parseInt(yearInput);

        System.out.print("Month (1–12) [" + today.getMonthValue() + "]: ");
        String monthInput = scanner.nextLine().trim();
        int month = monthInput.isEmpty() ? today.getMonthValue() : Integer.parseInt(monthInput);
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }

        List<Category> expenseCategories = expenseCategories();
        while (true) {
            System.out.println("Select category (0 to finish):");
            for (int i = 0; i < expenseCategories.size(); i++) {
                System.out.println("  " + (i + 1) + "=" + expenseCategories.get(i));
            }
            System.out.print("Enter category: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > expenseCategories.size()) {
                System.out.println("Invalid category choice.");
                continue;
            }
            Category category = expenseCategories.get(choice - 1);
            System.out.print("Budget amount for " + category + ": ");
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            tracker.setBudget(year, month, category, amount);
            System.out.println("Budget set: " + category + " = " + FormatCheck.formatMoney(amount));
        }
    }

    // ----- Option 9 -----
    private void budgetReport() {
        LocalDate today = LocalDate.now();

        System.out.print("Year [" + today.getYear() + "]: ");
        String yearInput = scanner.nextLine().trim();
        int year = yearInput.isEmpty() ? today.getYear() : Integer.parseInt(yearInput);

        System.out.print("Month (1–12) [" + today.getMonthValue() + "]: ");
        String monthInput = scanner.nextLine().trim();
        int month = monthInput.isEmpty() ? today.getMonthValue() : Integer.parseInt(monthInput);
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }

        if (!tracker.hasBudget(year, month)) {
            String monthName = Month.of(month).name().charAt(0)
                    + Month.of(month).name().substring(1).toLowerCase();
            System.out.println("No budget set for " + monthName + " " + year);
            return;
        }

        Budget report = tracker.getBudgetReport(year, month);
        System.out.println("Budget Report — " + Month.of(month) + " " + year);
        System.out.println("| Category | Planned | Actual | Variance | Status |");
        System.out.println("|----------|---------|--------|----------|--------|");

        for (Category category : Category.values()) {
            BudgetEntry entry = report.getEntry(category);
            if (entry == null) {
                continue;
            }
            String status = entry.isOverBudget() ? "OVER" : "UNDER";
            System.out.printf("| %s | %s | %s | %s | %s |%n",
                    entry.getCategory(),
                    FormatCheck.formatMoney(entry.getPlannedAmount()),
                    FormatCheck.formatMoney(entry.getActualAmount()),
                    FormatCheck.formatMoney(entry.getVariance()),
                    status);
        }

        BigDecimal totalPlanned = report.getTotalPlanned();
        BigDecimal totalActual = report.getTotalActual();
        BigDecimal netVariance = totalPlanned.subtract(totalActual);
        System.out.println("Total planned:  " + FormatCheck.formatMoney(totalPlanned));
        System.out.println("Total actual:   " + FormatCheck.formatMoney(totalActual));
        System.out.println("Net variance:   " + FormatCheck.formatMoney(netVariance));
    }

    // ----- helpers -----
    private LocalDate promptDate() {
        System.out.print("Date (YYYY-MM-DD, Enter for today): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date — use YYYY-MM-DD.");
        }
    }

    private Category promptCategory(boolean incomeOnly) {
        List<Category> options = incomeOnly ? incomeCategories() : expenseCategories();
        System.out.println(incomeOnly ? "Income categories:" : "Expense categories:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + (i + 1) + "=" + options.get(i));
        }
        System.out.print("Enter category: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice < 1 || choice > options.size()) {
            throw new IllegalArgumentException("Invalid category choice.");
        }
        return options.get(choice - 1);
    }

    private Category promptAnyCategory() {
        Category[] all = Category.values();
        System.out.println("Categories:");
        for (int i = 0; i < all.length; i++) {
            System.out.println("  " + (i + 1) + "=" + all[i]);
        }
        System.out.print("Enter category: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice < 1 || choice > all.length) {
            throw new IllegalArgumentException("Invalid category choice.");
        }
        return all[choice - 1];
    }

    private List<Category> incomeCategories() {
        List<Category> list = new ArrayList<>();
        for (Category category : Category.values()) {
            if (category.isIncomeCategory()) {
                list.add(category);
            }
        }
        return list;
    }

    private List<Category> expenseCategories() {
        List<Category> list = new ArrayList<>();
        for (Category category : Category.values()) {
            if (category.isExpenseCategory()) {
                list.add(category);
            }
        }
        return list;
    }
}
