import java.util.Scanner;

public class UnitConversion {
    public static void main(String[] args) {
        System.out.print("Hello!, it's Unit Converter Program\n");
        Scanner scanner = new Scanner(System.in);

        Conversion.temperatureConverter(scanner);
        Conversion.weightConverter(scanner);
        Conversion.distanceConverter(scanner);
        Conversion.timeConverter(scanner);
    }
}