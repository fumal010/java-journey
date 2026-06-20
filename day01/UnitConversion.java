import java.util.Scanner;

public class UnitConversion {
    public static void main(String[] args) {
        System.out.print("Hello!, it's Unit Converter Program\n");
        Scanner scanner = new Scanner(System.in);

        try {
            Conversion.temperatureConverter(scanner);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Conversion.weightConverter(scanner);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Conversion.distanceConverter(scanner);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Conversion.timeConverter(scanner);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        scanner.close();
    }
}
