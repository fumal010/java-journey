# Finance Tracker — Notes

## UML Class Diagram

```mermaid
classDiagram
    direction TB

    %% ===== app =====
    class Main {
        +main(String[] args)$ void
    }

    %% ===== ui =====
    class AppMenu

    %% ===== service =====
    class FinanceTracker

    %% ===== model =====
    class Account {
        -sequence: int$
        -id: String
        -name: String
        -type: AccountType
        -balance: BigDecimal
        -initialBalance: BigDecimal
        -creditLimit: BigDecimal
        +Account(name, type, balance, creditLimit)
        +getId() String
        +getName() String
        +getType() AccountType
        +getBalance() BigDecimal
        +getCreditLimit() BigDecimal
        -isCredit() boolean
        -deposit(amount) void
        -withdraw(amount) void
        -getAvailableBalance() BigDecimal
        -isOverdrawn() boolean
    }

    class Transaction {
        -sequence: int$
        -id: String
        -description: String
        -amount: BigDecimal
        -type: TransactionType
        -category: Category
        -date: LocalDate
        -accountId: String
        -toAccountId: String
        +Transaction(...)
        +getId() String
        +getDescription() String
        +getAmount() BigDecimal
        +getType() TransactionType
        +getCategory() Category
        +getDate() LocalDate
        +getAccountId() String
        +getToAccountId() String
        -isTransfer() boolean
    }

    class Budget {
        -year: int
        -month: int
        -entries: Map~Category, BudgetEntry~
        +Budget(year, month)
        +setBudget(category, amount) void
        +recordExpense(category, amount) void
        +getEntry(category) BudgetEntry
        +getOverBudgetCategories() List~BudgetEntry~
        +getTotalPlanned() BigDecimal
        +getTotalActual() BigDecimal
        +getYear() int
        +getMonth() int
    }

    class BudgetEntry {
        -category: Category
        -plannedAmount: BigDecimal
        -actualAmount: BigDecimal
        +BudgetEntry(category, plannedAmount)
        +addActual(amount) void
        +getVariance() BigDecimal
        +isOverBudget() boolean
        +getCategory() Category
        +getPlannedAmount() BigDecimal
        +getActualAmount() BigDecimal
    }

    %% ===== enums =====
    class AccountType {
        <<enumeration>>
        CHECKING
        SAVINGS
        CREDIT
        +allowsNegativeBalance()* boolean
    }

    class TransactionType {
        <<enumeration>>
        INCOME
        EXPENSE
        TRANSFER
        +isDebit()* boolean
    }

    class Category {
        <<enumeration>>
        FOOD
        HOUSING
        TRANSPORT
        ENTERTAINMENT
        HEALTH
        EDUCATION
        SALARY
        INVESTMENT
        OTHER
        +isExpenseCategory() boolean
        +isIncomeCategory() boolean
    }

    %% ===== exceptions =====
    class AccountNotFoundException
    class DuplicateAccountException
    class InsufficientFundsException
    class InvalidAmountException
    class Exception

    %% ===== relationships =====
    Main --> AppMenu : uses
    Main --> FinanceTracker : uses
    AppMenu --> FinanceTracker : uses

    FinanceTracker "1" o-- "*" Account : manages
    FinanceTracker "1" o-- "*" Transaction : manages
    FinanceTracker "1" o-- "*" Budget : manages

    Account --> AccountType : type
    Transaction --> TransactionType : type
    Transaction --> Category : category
    Transaction ..> Account : accountId / toAccountId

    Budget "1" *-- "*" BudgetEntry : entries
    BudgetEntry --> Category : category

    AccountNotFoundException --|> Exception
    DuplicateAccountException --|> Exception
    InsufficientFundsException --|> Exception
    InvalidAmountException --|> Exception

    Account ..> InsufficientFundsException : throws
    Account ..> InvalidAmountException : throws
```

## Package Layout

```
com.learn.finance
├── app          → Main
├── ui           → AppMenu
├── service      → FinanceTracker
├── model        → Account, Transaction, Budget, BudgetEntry
├── enums        → AccountType, TransactionType, Category
└── exception    → AccountNotFoundException, DuplicateAccountException,
                   InsufficientFundsException, InvalidAmountException
```

## Relationship Legend

| Symbol | Meaning |
|--------|---------|
| `-->`  | association / dependency |
| `o--`  | aggregation (has-a, shared lifetime) |
| `*--`  | composition (has-a, owned lifetime) |
| `--\|>` | inheritance |
| `..>`  | dependency (uses / throws) |
