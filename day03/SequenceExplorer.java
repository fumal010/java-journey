import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SequenceExplorer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        collatzSequence(scanner);
        pascalsTriangle(scanner);
        primeSieve(scanner);
        digitSumLoop(scanner);
        spiralMatrix(scanner);

        scanner.close();
    }



    private static void collatzSequence(Scanner scanner) {
        System.out.print("Enter starting number n: ");
        long n = Long.parseLong(scanner.nextLine().trim());
        if (n <= 0) {
            System.out.println("Starting value must be greater than 0.");
            return;
        }

        long maxValue = n;
        int steps = 0;
        System.out.print(n);
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            steps++;
            maxValue = Math.max(maxValue, n);
            System.out.print(" -> " + n);
        }
        System.out.println();
        System.out.println("Steps to reach 1: " + steps);
        System.out.println("Maximum value reached: " + maxValue);
    }




    private static void pascalsTriangle(Scanner scanner) {
        System.out.print("Enter number of rows (1-15): ");
        int rowCount = Integer.parseInt(scanner.nextLine().trim());
        if (rowCount < 1 || rowCount > 15) {
            System.out.println("Row count must be between 1 and 15.");
            return;
        }

        int[][] triangle = new int[rowCount][];
        for (int row = 0; row < rowCount; row++) {
            triangle[row] = new int[row + 1];
            for (int col = 0; col <= row; col++) {
                if (col == 0 || col == row) {
                    triangle[row][col] = 1;
                } else {
                    triangle[row][col] = triangle[row - 1][col - 1] + triangle[row - 1][col];
                }
            }
        }

        int cellWidth = String.valueOf(triangle[rowCount - 1][rowCount / 2]).length() + 1;
        for (int row = 0; row < rowCount; row++) {
            int leadingSpaces = (rowCount - row - 1) * (cellWidth / 2 + 1);
            System.out.print(" ".repeat(Math.max(0, leadingSpaces)));
            for (int col = 0; col <= row; col++) {
                System.out.printf("%" + cellWidth + "d", triangle[row][col]);
            }
            System.out.println();
        }
    }



    private static void primeSieve(Scanner scanner) {
        System.out.print("Enter limit n (up to 100,000): ");
        int limit = Integer.parseInt(scanner.nextLine().trim());
        if (limit < 2 || limit > 100_000) {
            System.out.println("Limit must be between 2 and 100,000.");
            return;
        }

        long startNanos = System.nanoTime();
        boolean[] isComposite = new boolean[limit + 1];
        for (int prime = 2; prime * prime <= limit; prime++) {
            if (!isComposite[prime]) {
                for (int multiple = prime * prime; multiple <= limit; multiple += prime) {
                    isComposite[multiple] = true;
                }
            }
        }
        long elapsedNanos = System.nanoTime() - startNanos;

        List<Integer> primes = new ArrayList<>();
        for (int value = 2; value <= limit; value++) {
            if (!isComposite[value]) {
                primes.add(value);
            }
        }

        System.out.println("Primes found: " + primes.size());
        System.out.println("Largest prime: " + primes.get(primes.size() - 1));
        System.out.print("First 20 primes: ");
        int printCount = Math.min(20, primes.size());
        for (int i = 0; i < printCount; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(primes.get(i));
        }

        System.out.println();
        System.out.printf("Runtime: %.3f ms%n", elapsedNanos / 1_000_000.0);
    }



    private static void digitSumLoop(Scanner scanner) {
        long digitSum = 0;
        int evenDigitNumberCount = 0;
        int primeNumberCount = 0;

        int number;
        do {
            System.out.print("Enter an integer (0 to stop): ");
            number = Integer.parseInt(scanner.nextLine().trim());
            if (number == 0) {
                break;
            }

            digitSum += sumOfDigits(number);
            if (hasOnlyEvenDigits(number)) {
                evenDigitNumberCount++;
            }
            boolean prime = isPrime(Math.abs(number));
            if (prime) {
                primeNumberCount++;
            }
            System.out.println(number + " is " + (prime ? "" : "not ") + "prime");
        } while (number != 0);

        System.out.println("Sum of all digits: " + digitSum);
        System.out.println("Count of even-digit numbers: " + evenDigitNumberCount);
        System.out.println("Count of prime numbers entered: " + primeNumberCount);
    }



    private static void spiralMatrix(Scanner scanner) {
        System.out.print("Enter matrix size n (1-8): ");

        int size = Integer.parseInt(scanner.nextLine().trim());
        if (size < 1 || size > 8) {
            System.out.println("Size must be between 1 and 8.");
            return;
        }

        int[][] matrix = new int[size][size];
        int value = 1;
        int top = 0;
        int bottom = size - 1;
        int left = 0;
        int right = size - 1;

        while (value <= size * size) {
            for (int col = left; col <= right && value <= size * size; col++) {
                matrix[top][col] = value++;
            }
            top++;

            for (int row = top; row <= bottom && value <= size * size; row++) {
                matrix[row][right] = value++;
            }
            right--;

            for (int col = right; col >= left && value <= size * size; col--) {
                matrix[bottom][col] = value++;
            }
            bottom--;

            for (int row = bottom; row >= top && value <= size * size; row--) {
                matrix[row][left] = value++;
            }
            left++;
        }

        int cellWidth = String.valueOf(size * size).length() + 1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.printf("%" + cellWidth + "d", matrix[row][col]);
            }
            
            System.out.println();
        }
    }

    private static long sumOfDigits(int number) {
        long sum = 0;
        int remaining = Math.abs(number);
        while (remaining > 0) {
            sum += remaining % 10;
            remaining /= 10;
        }
        return sum;
    }

    private static boolean hasOnlyEvenDigits(int number) {
        int remaining = Math.abs(number);
        if (remaining == 0) {
            return true;
        }
        while (remaining > 0) {
            int digit = remaining % 10;
            if (digit % 2 != 0) {
                return false;
            }
            remaining /= 10;
        }
        return true;
    }

    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int divisor = 3; divisor * divisor <= number; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
