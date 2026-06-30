import java.util.Scanner;

class Fibonacci {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter Fibonacci index \n\n");
                int n = Integer.parseInt(scanner.nextLine());

                int recursiveResult = fibonacci(n);
                int iterativeResult = fibonacciIterative(n);

                System.out.println("Recursive fibonacci(" + n + ") = " + recursiveResult);
                System.out.println("Iterative fibonacci(" + n + ") = " + iterativeResult);
                break;
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
            }
        }

        scanner.close();
    }

    public static int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }


    public static int fibonacciIterative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (n <= 1) {
            return n;
        }
        int previous = 0;
        int current = 1;
        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }
}
