public enum OrderStatus {

    PLACED("Order Placed") {
        @Override
        public boolean isTerminal() {
            return false;
        }
    },
    CONFIRMED("Order Confirmed") {
        @Override
        public boolean isTerminal() {
            return false;
        }
    },
    SHIPPED("Order Shipped") {
        @Override
        public boolean isTerminal() {
            return false;
        }
    },
    DELIVERED("Order Delivered") {
        @Override
        public boolean isTerminal() {
            return true;
        }
    },
    CANCELLED("Order Cancelled") {
        @Override
        public boolean isTerminal() {
            return true;
        }
    };

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract boolean isTerminal();

    public boolean canTransitionTo(OrderStatus next) {
        if (next == null) {
            return false;
        }
        return switch (this) {
            case PLACED -> next == CONFIRMED || next == CANCELLED;
            case CONFIRMED -> next == SHIPPED || next == CANCELLED;
            case SHIPPED -> next == DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
