
public class UnitConversion {
    public static void main(String[] args) {
        System.out.print("Hello!, it's Unit Converter Program\n");

        Conversion conversion = new Conversion();
        conversion.temperatureConverter();
        conversion.weightConverter();
        conversion.distanceConverter();
        conversion.timeConverter();
    }
}