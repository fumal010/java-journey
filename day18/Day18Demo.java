import java.util.HashMap;
import java.util.Map;

public class Day18Demo {

    public static void main(String[] args) {
        integerCacheDemo();
        nullUnboxingDemo();
        autoboxingPerformanceDemo();
        integerUtilityMethodsDemo();
        integerOverflowDemo();
    }

    private static void integerCacheDemo() {
        System.out.println("=== Integer cache & equals ===");

        Integer a127 = 127;
        Integer b127 = 127;
        // == compares references; -128..127 are cached, so both variables point at the same object → true
        System.out.println("127 == 127: " + (a127 == b127));

        Integer a128 = 128;
        Integer b128 = 128;
        // 128 is outside the cache; autoboxing creates two separate Integer objects → false
        System.out.println("128 == 128: " + (a128 == b128));

        // .equals() compares numeric value, not reference — always true for same int value
        System.out.println("127.equals(127): " + a127.equals(b127));
        System.out.println("128.equals(128): " + a128.equals(b128));
        // Rule: always use .equals() (or intValue / unbox to int and use ==) for Integer comparison
    }

    private static void nullUnboxingDemo() {
        System.out.println("\n=== Null unboxing ===");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);

        // int bob = scores.get("Bob"); // NPE: get returns null; unboxing null to int throws NullPointerException

        int bobSafe = scores.getOrDefault("Bob", 0);
        System.out.println("Bob score (getOrDefault): " + bobSafe);

        Integer val = scores.get("Bob");
        int safe = val != null ? val : 0;
        System.out.println("Bob score (null check): " + safe);
    }

    private static void autoboxingPerformanceDemo() {
        System.out.println("\n=== Autoboxing performance (1_000_000 iterations) ===");
        int limit = 1_000_000;

        long startInteger = System.nanoTime();
        Integer sumBoxed = 0;
        for (int i = 1; i <= limit; i++) {
            sumBoxed += i; // autobox/unbox each iteration; new Integer objects → GC pressure
        }
        long integerNanos = System.nanoTime() - startInteger;

        long startPrimitive = System.nanoTime();
        int sumPrimitive = 0;
        for (int i = 1; i <= limit; i++) {
            sumPrimitive += i;
        }
        long primitiveNanos = System.nanoTime() - startPrimitive;

        double ratio = (double) integerNanos / primitiveNanos;
        System.out.printf("Integer loop: %,.0f ms%n", integerNanos / 1_000_000.0);
        System.out.printf("int loop:     %,.0f ms%n", primitiveNanos / 1_000_000.0);
        System.out.printf("Ratio (Integer / int): %.2fx%n", ratio);
    }

    private static void integerUtilityMethodsDemo() {
        System.out.println("\n=== Wrapper utility methods ===");
        System.out.println("Integer.parseInt(\"42\"): " + Integer.parseInt("42"));
        System.out.println("Integer.compare(5, 10): " + Integer.compare(5, 10)); // negative; never use a - b (overflow)
        System.out.println("Integer.toBinaryString(255): " + Integer.toBinaryString(255));
        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        System.out.println("Integer.MIN_VALUE: " + Integer.MIN_VALUE);
        System.out.println("Double.isNaN(0.0 / 0.0): " + Double.isNaN(0.0 / 0.0));
        System.out.println("Double.isInfinite(1.0 / 0.0): " + Double.isInfinite(1.0 / 0.0));
    }

    private static void integerOverflowDemo() {
        System.out.println("\n=== Integer overflow ===");
        int wrapped = Integer.MAX_VALUE + 1;
        System.out.println("Integer.MAX_VALUE + 1: " + wrapped); // silent wraparound to MIN_VALUE

        try {
            Math.addExact(Integer.MAX_VALUE, 1);
        } catch (ArithmeticException arithmeticException) {
            // use Math.addExact when overflow must be an error, not silent wraparound
            System.out.println("Math.addExact overflow: " + arithmeticException.getMessage());
        }
    }
}
