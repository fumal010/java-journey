package com.learn.finance.service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.learn.finance.model.*;
import com.learn.finance.enums.*;
import com.learn.finance.exception.AccountNotFoundException;
import com.learn.finance.exception.FinanceException;

public class FinanceTracker {

    private final Map<String, Account> accounts = new HashMap<>();

    private final List<Transaction> transactions = new ArrayList<>();

    private final Map<String, Budget> budgetsByMonth = new HashMap<>();
 


    // O(n) for duplicate name check, O(1) put
    public Account addAccount(String name, AccountType type, BigDecimal initialBalance, BigDecimal creditLimit) {
        if (isAccountNameExist(name)) {
            // throw new AccountDuplicateException("Account with name " + name + " already exists");
        }
        Account newAccount = new Account(name, type, initialBalance, creditLimit);
        addAccountToMap(newAccount);
        return newAccount;
    }

    private void addAccountToMap(Account account) {
        accounts.put(account.getId(), account);
    }

    // O(n)
    private boolean isAccountNameExist(String name) {
        for (Account account : accounts.values()) {
            if (account.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // O(1)
    private boolean isAccountIdExist(String id) {
        return accounts.containsKey(id);
    }

    public Optional<Account> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public Optional<Account> findByName(String name) {
        for (Account account : accounts.values()) {
            if (account.getName().equalsIgnoreCase(name)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    private Account requireById(String id) {
        return findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found: " + id));
    }


    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

   public BigDecimal getTotalBalance() {
       BigDecimal totalBalance = BigDecimal.ZERO;

       for (Account account : accounts.values()) {
           totalBalance.add(account.getBalance());
       }
       return totalBalance;
   }

//O(1) 
   private Transaction createTransaction( String description, BigDecimal amount, TransactionType type, Category category, LocalDate date, String accountId)  {
       return createTransaction(description, amount, type, category, date, accountId, null);
   }

    private Transaction createTransaction( String description, BigDecimal amount, TransactionType type, Category category, LocalDate date, String accountId, String toAccountId)  {
        Transaction transaction = new Transaction(description, amount, type, category, date, accountId, toAccountId);
        transactions.add(transaction);
        return transaction;
    }

   public Transaction recordIncome( String description, BigDecimal amount, TransactionType type, Category category, LocalDate date, String accountId) {

        if (!category.isIncomeCategory()) {
            throw new IllegalArgumentException("Category is not an income category");
        }

        if (type.isDebit()) {
            throw new IllegalArgumentException("Type is not an income type");
        }

       Transaction transaction = createTransaction(description, amount, type, category, date, accountId);

       Account transactionAccount = requireById(accountId);
       transactionAccount.deposit(amount);
       transaction.setStatus(TransactionStatus.SUCCEEDED);

       return transaction;
   }


   public Transaction recordExpense(String description, BigDecimal amount, TransactionType type, Category category, LocalDate date, String accountId ) {
        if (!category.isExpenseCategory()) {
            throw new IllegalArgumentException("Category is not an expense category");
        }

        if (!type.isDebit()) {
            throw new IllegalArgumentException("Type is not an expense type");
        }

        Transaction transaction = createTransaction(description, amount, type, category, date, accountId);

        Account transactionAccount = requireById(accountId);
        transactionAccount.withdraw(amount);
        transaction.setStatus(TransactionStatus.SUCCEEDED);

        return transaction;
   }

   public Transaction transfer(String fromAccountId, String toAccountId, BigDecimal amount, String description, LocalDate date) {

       Account transactionFromAccount = requireById(fromAccountId);
       Account transactionToAccount = requireById(toAccountId);

       Transaction transaction = new Transaction(description, amount, TransactionType.TRANSFER, Category.OTHER, date, fromAccountId, toAccountId);
       transactions.add(transaction);

       try {
           transactionFromAccount.withdraw(amount);
       } catch (FinanceException e) {
           transaction.setStatus(TransactionStatus.FAILED);
           return transaction;
       }

       try {
           transactionToAccount.deposit(amount);
       } catch (FinanceException e) {
           transactionFromAccount.deposit(amount);
           transaction.setStatus(TransactionStatus.FAILED);
           return transaction;
       }

       transaction.setStatus(TransactionStatus.SUCCEEDED);
       return transaction;
   }
   

   // a getter? with protection for reference from external mutation?
   public List<Transaction> getTransactions() {
       return new ArrayList<>(transactions);
   }

   public List<Transaction> getTransactionsByAccount(String accountId) {
       List<Transaction> result = new ArrayList<>();
       for (Transaction transaction : transactions) {
           if (transaction.getAccountId().equals(accountId)) {
               result.add(transaction);
           }
       }
       return result;
   }

   public List<Transaction> getTransactionsByCategory(Category category) {
       List<Transaction> result = new ArrayList<>();
       for (Transaction transaction : transactions) {
           if (transaction.getCategory() == category) {
               result.add(transaction);
           }
       }
       return result;
   }

   public List<Transaction> getTransactionsByDateRange(LocalDate from, LocalDate to) {
       List<Transaction> result = new ArrayList<>();
       for (Transaction transaction : transactions) {
           if (transaction.getDate().isAfter(from) && transaction.getDate().isBefore(to)) {
               result.add(transaction);
           }
       }
       return result;
   }

   public List<Transaction> getTransactionsByType(TransactionType type) {
       List<Transaction> result = new ArrayList<>();
       for (Transaction transaction : transactions) {
           if (transaction.getType() == type) {
               result.add(transaction);
           }
       }
       return result;
   }

   public boolean hasBudget(int year, int month) {
       return budgetsByMonth.containsKey(monthKey(year, month));
   }

   // O(n) — single pass through all transactions
   public MonthlySummary getMonthlySummary(int year, int month) {
       BigDecimal totalIncome = BigDecimal.ZERO;
       BigDecimal totalExpenses = BigDecimal.ZERO;
       Map<Category, BigDecimal> incomeByCategory = new EnumMap<>(Category.class);
       Map<Category, BigDecimal> expensesByCategory = new EnumMap<>(Category.class);
       Transaction largestIncome = null;
       Transaction largestExpense = null;

       for (Transaction transaction : transactions) {
           if (!isInMonth(transaction.getDate(), year, month)) {
               continue;
           }

           if (transaction.getType() == TransactionType.INCOME) {
               totalIncome = totalIncome.add(transaction.getAmount());
               incomeByCategory.merge(transaction.getCategory(), transaction.getAmount(), BigDecimal::add);

               if (largestIncome == null
                       || transaction.getAmount().compareTo(largestIncome.getAmount()) > 0) {
                   largestIncome = transaction;
               }
           } else if (transaction.getType() == TransactionType.EXPENSE) {
               totalExpenses = totalExpenses.add(transaction.getAmount());
               expensesByCategory.merge(transaction.getCategory(), transaction.getAmount(), BigDecimal::add);

               if (largestExpense == null
                       || transaction.getAmount().compareTo(largestExpense.getAmount()) > 0) {
                   largestExpense = transaction;
               }
           }
       }

       BigDecimal netSavings = totalIncome.subtract(totalExpenses);
       return new MonthlySummary(
               totalIncome,
               totalExpenses,
               netSavings,
               expensesByCategory,
               incomeByCategory,
               largestExpense,
               largestIncome);
   }

   // creates or updates the budget entry for that month
   public void setBudget(int year, int month, Category category, BigDecimal amount) {
       String key = monthKey(year, month);
       Budget budget = budgetsByMonth.get(key);
       if (budget == null) {
           budget = new Budget(year, month);
           budgetsByMonth.put(key, budget);
       }
       budget.setBudget(category, amount);
   }

   public Budget getBudgetReport(int year, int month) {
       String key = monthKey(year, month);
       Budget stored = budgetsByMonth.get(key);

       Budget report = new Budget(year, month);
       if (stored != null) {
           for (Category category : Category.values()) {
               stored.getEntry(category)
                       .ifPresent(entry -> report.setBudget(category, entry.getPlannedAmount()));
           }
       }

       for (Transaction transaction : transactions) {
           if (!isInMonth(transaction.getDate(), year, month)) {
               continue;
           }
           if (transaction.getType() != TransactionType.EXPENSE) {
               continue;
           }
           if (report.getEntry(transaction.getCategory()).isPresent()) {
               report.recordExpense(transaction.getCategory(), transaction.getAmount());
           }
       }

       return report;
   }

   private static String monthKey(int year, int month) {
       return String.format("%04d-%02d", year, month);
   }

   private static boolean isInMonth(LocalDate date, int year, int month) {
       return date.getYear() == year && date.getMonthValue() == month;
   }

}
