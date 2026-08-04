public enum PaymentMethod {

    CREDIT_CARD("123oo", 0.025),
    DEBIT_CARD("Debit Card", 0.015),
    BANK_TRANSFER("Bank Transfer", 0.005);

    private final String displayName;
    private final double feeRate;

    PaymentMethod(String displayName, double feeRate) {
        this.displayName = displayName;
        this.feeRate = feeRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public double computeFee(double amount) {
        return amount * feeRate;
    }
}
