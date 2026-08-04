import java.util.EnumMap;
import java.util.EnumSet;

public class Day17Demo {

    // int constants: void setStatusInt(int s) { ... } accepts any int (e.g. 99) — no type safety.
    static void setStatusInt(int statusCode) {
        System.out.println("int status set to: " + statusCode);
    }

    // enum: only valid OrderStatus constants compile — void setStatus(OrderStatus s)
    static void setStatus(OrderStatus status) {
        System.out.println("OrderStatus set to: " + status + " (" + status.getLabel() + ")");
    }

    public static void main(String[] args) {
        System.out.println("=== Type safety: int vs enum ===");
        setStatusInt(99);
        setStatus(OrderStatus.PLACED);
        // setStatus(99); // compile error: incompatible types

        System.out.println("\n=== PaymentMethod.values() ===");
        for (PaymentMethod method : PaymentMethod.values()) {
            System.out.println(method.name()
                    + " ordinal=" + method.ordinal()
                    + " fee on 100.0=" + method.computeFee(100.0));
        }

        System.out.println("\n=== valueOf ===");
        PaymentMethod credit = PaymentMethod.valueOf("CREDIT_CARD");
        // PaymentMethod.CREDIT_CARD.getDisplayName();
        System.out.println(credit);
        System.out.println("valueOf CREDIT_CARD: " + credit.getDisplayName());
        try {
            PaymentMethod.valueOf("INVALID");
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("valueOf INVALID: " + illegalArgumentException.getMessage());
        }

        System.out.println("\n=== OrderStatus state machine ===");
        OrderStatus current = OrderStatus.PLACED;
        System.out.println("Start: " + current.getLabel());

        current = transition(current, OrderStatus.CONFIRMED);
        current = transition(current, OrderStatus.SHIPPED);
        current = transition(current, OrderStatus.DELIVERED);

        System.out.println("isTerminal(DELIVERED): " + current.isTerminal());

        transition(current, OrderStatus.CANCELLED);

        System.out.println("\n=== EnumSet ===");
        EnumSet<OrderStatus> terminal = EnumSet.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED);
        System.out.println("terminal: " + terminal);
        EnumSet<OrderStatus> nonTerminal = EnumSet.complementOf(terminal);
        System.out.println("non-terminal (complement): " + nonTerminal);

        System.out.println("\n=== EnumMap ===");
        // EnumMap uses an array indexed by ordinal — no hashing, no boxing for keys, O(1) get/put.
        EnumMap<PaymentMethod, Integer> usageCount = new EnumMap<>(PaymentMethod.class);
        usageCount.put(PaymentMethod.CREDIT_CARD, 120);
        usageCount.put(PaymentMethod.DEBIT_CARD, 85);
        usageCount.put(PaymentMethod.BANK_TRANSFER, 40);
        for (PaymentMethod method : usageCount.keySet()) {
            System.out.println(method.name() + " -> " + usageCount.get(method));
        }
    }

    private static OrderStatus transition(OrderStatus from, OrderStatus to) {
        if (from.canTransitionTo(to)) {
            System.out.println(from.name() + " -> " + to.name() + " (" + to.getLabel() + ")");
            return to;
        }
        System.out.println("invalid transition: " + from.name() + " -> " + to.name());
        return from;
    }
}
