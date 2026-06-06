import java.util.Scanner;

public class InsurancePremium {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        InsurancePremium.calculatePremium(scanner);
    }



    private static final double basePremium = 200.00;
    private static double totalPremium = 200.00;


    private static void calculatePremium (Scanner scanner) {
        InsurancePremium.applyAgeEffect(scanner);
        InsurancePremium.applyPersonalFactorEffect(scanner);
        InsurancePremium.applyCoverageTierEffect(scanner);
        InsurancePremium.applyAnnualDeductibleEffect(scanner);
    }

    private static void applyAgeEffect (Scanner scanner) {
        double effect = 1.0;

        System.out.println("\n What is your age? \n");
        double age = scanner.nextDouble();

        if (age < 18) effect = 0.5;
        else if (age < 25) effect = 0.8;
        else if (age < 45) effect = 1.0;
        else if (age < 60) effect = 1.3;
        else effect = 1.6;

        InsurancePremium.totalPremium = InsurancePremium.basePremium *effect;
        InsurancePremium.displayTotalPremium();
    }


    private static void applyCoverageTierEffect (Scanner scanner) {
        double effect = 1;

        System.out.println("\n What is your coverage tier? \n Enter a number: \n\n BASIC:1 \n STANDARD:2 \n PREMIUM:3\n");
        int coverage = scanner.nextInt();

        switch (coverage){
            case 1 -> effect = 1;
            case 2 -> effect = 1.3;
            case 3 -> effect = 1.8;
        }

        InsurancePremium.totalPremium *=effect;
        InsurancePremium.displayTotalPremium();
    }


    private static void applyPersonalFactorEffect (Scanner scanner) {
        System.out.println("\n Are you a smoker? \n Enter a number: \n\n Yes:1 \n No:2  \n");
        boolean isSmoker = scanner.nextInt() == 1;
        double smokingEffect = isSmoker ? 0.4 * InsurancePremium.totalPremium : 0;

        System.out.println("\n Do you have any pre-existing conditions? \n Enter a number: \n\n Yes:1 \n No:2  \n");
        boolean hasPrevConditions = scanner.nextInt() == 1;
        double prevConditionsEffect = hasPrevConditions ? 0.25 * InsurancePremium.totalPremium : 0;

        InsurancePremium.totalPremium = InsurancePremium.totalPremium
                + smokingEffect
                + prevConditionsEffect;

        InsurancePremium.displayTotalPremium();
    }


    private static void applyAnnualDeductibleEffect (Scanner scanner) {
        System.out.println("\n What is your annual deductible? \n");
        boolean hasAnnualDeductible = scanner.nextDouble() > 5000;
        double annualDeductibleEffect = hasAnnualDeductible ? 0.15 * InsurancePremium.totalPremium : 0;

        InsurancePremium.totalPremium = InsurancePremium.totalPremium
                - annualDeductibleEffect;

        InsurancePremium.displayTotalPremium();
    }


    private static void displayTotalPremium () {
        System.out.println("\n Your Premium is : " + InsurancePremium.totalPremium + "\n");
    }
}