import java.util.Scanner;

public class Conversion {


    public static void temperatureConverter () {
        Scanner temperatureScanner = new Scanner(System.in);
        
        System.out.println("\nEnter 1 : Celsius → Kelvin \nEnter 2 : Celsius → Fahrenheit\n");
        int conversionChoice = temperatureScanner.nextInt();

        if (conversionChoice != 1 && conversionChoice != 2) {
            System.out.println("\nInvalid conversion choice\n");
            return;
        }


        System.out.println("\nEnter tempreture value\n");
        double temperatureValue = temperatureScanner.nextDouble();

        if (Double.isNaN(temperatureValue)) {
            System.out.println("\nInvalid temperature value\n");
            return;
        }


        if ( conversionChoice == 1) {
            double kelvin = temperatureValue + 273.15;
            System.out.println("\nCelsius → Kelvin :" + kelvin );
        }
        else if ( conversionChoice == 2 ) {
            double fahrenheit = temperatureValue * 9.0/5 + 32;
            System.out.println("\nCelsius → Fahrenheit :" + fahrenheit);
        }
    }

    public static void weightConverter () {
        Scanner weightScanner = new Scanner(System.in);

        System.out.println("\nEnter 1 : Kilograms → Pounds \nEnter 2 : Kilograms → Grams\n");
        int conversionChoice = weightScanner.nextInt();

        if (conversionChoice != 1 && conversionChoice != 2) {
            System.out.println("\nInvalid conversion choice\n");
            return;
        }

        System.out.println("\nEnter weight value\n");
        double weightValue = weightScanner.nextDouble();

        if (Double.isNaN(weightValue)) {
            System.out.println("\nInvalid weight value\n");
            return;
        }

        if ( conversionChoice == 1) {
            double pounds = weightValue * 2.20462;
            System.out.println("\nKilograms → Pounds :" + pounds);
        }
        else if ( conversionChoice == 2 ) {
            double grams = weightValue * 1000;
            System.out.println("\nKilograms → Grams :" + grams);
        }
    }

    public static void distanceConverter () {
        Scanner distanceScanner = new Scanner(System.in);

        System.out.println("\nEnter 1 : Meters → Feet \nEnter 2 : Meters → Miles\n");
        int conversionChoice = distanceScanner.nextInt();

        if (conversionChoice != 1 && conversionChoice != 2) {
            System.out.println("\nInvalid conversion choice\n");
            return;
        }

        System.out.println("\nEnter distance value\n");
        double distanceValue = distanceScanner.nextDouble();

        if (Double.isNaN(distanceValue)) {
            System.out.println("\nInvalid distance value\n");
            return;
        }

        if ( conversionChoice == 1) {
            double feet = distanceValue * 3.28084;
            System.out.println("\nMeters → Feet :" + feet);
        }
        else if ( conversionChoice == 2 ) {
            double miles = distanceValue / 1609.344;
            System.out.println("\nMeters → Miles :" + miles);
        }
    }

    public static void timeConverter () {
        Scanner timeScanner = new Scanner(System.in);
        System.out.println("\nWhat unit of time is it?\n");
        System.out.println("\nEnter 1 : Hours \nEnter 2 : Minutes \nEnter 3 : Seconds \n");
        int conversionChoice = timeScanner.nextInt();

        if (conversionChoice != 1 && conversionChoice != 2 && conversionChoice != 3) {
            System.out.println("\nInvalid conversion choice\n");
            return;
        }

        System.out.println("\nEnter time value\n");
        double timeValue = timeScanner.nextDouble();

        if (Double.isNaN(timeValue)) {
            System.out.println("\nInvalid time value\n");
            return;
        }

        double hours = 0, minutes = 0, seconds = 0;

        // could be enhancesd if i come-up with some generic equation
        if ( conversionChoice == 1) {
             minutes = timeValue * 60;
             seconds = timeValue * 60 * 60;
        }
        else if (conversionChoice == 2) {
             hours = timeValue / 60;
             seconds = timeValue * 60;
        }
        else if (conversionChoice == 3) {
            minutes = seconds / 60.0;
            hours = seconds / 60.0 / 60.0;
        }

        System.out.println("\n" + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
    }
}