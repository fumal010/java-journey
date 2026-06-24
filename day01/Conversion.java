import java.util.Scanner;

public class Conversion {

    private static void requireChoice(int choice, int minimum, int maximum) {
        if (choice < minimum || choice > maximum) {
            throw new IllegalArgumentException("\nInvalid conversion choice\n");
        }
    }

    public static void temperatureConverter(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nEnter 1 : Celsius → Kelvin \nEnter 2 : Celsius → Fahrenheit\n");
                int conversionChoice = Integer.parseInt(scanner.nextLine().trim());
                requireChoice(conversionChoice, 1, 2);

                System.out.println("\nEnter tempreture value\n");
                double temperatureValue = Double.parseDouble(scanner.nextLine().trim());

                if (Double.isNaN(temperatureValue)) {
                    throw new IllegalArgumentException("\nInvalid temperature value\n");
                }

                if (conversionChoice == 1) {
                    double kelvin = temperatureValue + 273.15;
                    System.out.println("\nCelsius → Kelvin :" + kelvin);
                } else {
                    double fahrenheit = temperatureValue * 9.0 / 5 + 32;
                    System.out.println("\nCelsius → Fahrenheit :" + fahrenheit);
                }
                return;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Invalid input — please enter a number");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
            }
        }
    }

    public static void weightConverter(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nEnter 1 : Kilograms → Pounds \nEnter 2 : Kilograms → Grams\n");
                int conversionChoice = Integer.parseInt(scanner.nextLine().trim());
                requireChoice(conversionChoice, 1, 2);

                System.out.println("\nEnter weight value\n");
                double weightValue = Double.parseDouble(scanner.nextLine().trim());

                if (Double.isNaN(weightValue)) {
                    throw new IllegalArgumentException("\nInvalid weight value\n");
                }

                if (conversionChoice == 1) {
                    double pounds = weightValue * 2.20462;
                    System.out.println("\nKilograms → Pounds :" + pounds);
                } else {
                    double grams = weightValue * 1000;
                    System.out.println("\nKilograms → Grams :" + grams);
                }
                return;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Invalid input — please enter a number");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
            }
        }
    }

    public static void distanceConverter(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nEnter 1 : Meters → Feet \nEnter 2 : Meters → Miles\n");
                int conversionChoice = Integer.parseInt(scanner.nextLine().trim());
                requireChoice(conversionChoice, 1, 2);

                System.out.println("\nEnter distance value\n");
                double distanceValue = Double.parseDouble(scanner.nextLine().trim());

                if (Double.isNaN(distanceValue)) {
                    throw new IllegalArgumentException("\nInvalid distance value\n");
                }

                if (conversionChoice == 1) {
                    double feet = distanceValue * 3.28084;
                    System.out.println("\nMeters → Feet :" + feet);
                } else {
                    double miles = distanceValue / 1609.344;
                    System.out.println("\nMeters → Miles :" + miles);
                }
                return;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Invalid input — please enter a number");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
            }
        }
    }

    public static void timeConverter(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nWhat unit of time is it?\n");
                System.out.println("\nEnter 1 : Hours \nEnter 2 : Minutes \nEnter 3 : Seconds \n");
                int conversionChoice = Integer.parseInt(scanner.nextLine().trim());
                requireChoice(conversionChoice, 1, 3);

                System.out.println("\nEnter time value\n");
                double timeValue = Double.parseDouble(scanner.nextLine().trim());

                if (Double.isNaN(timeValue)) {
                    throw new IllegalArgumentException("\nInvalid time value\n");
                }

                double hours = 0;
                double minutes = 0;
                double seconds = 0;

                if (conversionChoice == 1) {
                    minutes = timeValue * 60;
                    seconds = timeValue * 60 * 60;
                } else if (conversionChoice == 2) {
                    hours = timeValue / 60;
                    seconds = timeValue * 60;
                } else {
                    minutes = timeValue / 60.0;
                    hours = timeValue / 60.0 / 60.0;
                }

                System.out.println("\n" + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
                return;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Invalid input — please enter a number");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
            }
        }
    }
}
