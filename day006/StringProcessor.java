import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StringProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        isPalindrome(scanner);
        longestWord(scanner);
        camelToSnake(scanner);
        snakeToCamel(scanner);

        formatTable();

        truncateWithEllipsis(scanner);
        countOccurrences(scanner);

        scanner.close();
    }




    public static boolean isPalindrome(Scanner scanner) {
        System.out.print("Enter text (isPalindrome): ");
        String s = scanner.nextLine();

        String normalized = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        int left = 0;
        int right = normalized.length() - 1;
        while (left < right) {
            if (normalized.charAt(left) != normalized.charAt(right)) {
                System.out.println(false);
                return false;
            }
            left++;
            right--;
        }
        System.out.println(true);
        return true;
    }




    public static String longestWord(Scanner scanner) {
        System.out.print("Enter sentence (longestWord): ");
        String sentence = scanner.nextLine();

        String[] words = sentence.split("\\s+");

        String longest = "";
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        System.out.println(longest);
        return longest;
    }



    public static String camelToSnake(Scanner scanner) {
        System.out.print("Enter camelCase (camelToSnake): ");
        String camelCase = scanner.nextLine();

        String result = camelCase.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
       
        System.out.println(result);
        return result;
    }



    public static String snakeToCamel(Scanner scanner) {
        System.out.print("Enter snake_case (snakeToCamel): ");
        String snake = scanner.nextLine();

        String[] parts = snake.split("_");

        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) {
                continue;
            }
            result.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                result.append(part.substring(1).toLowerCase());
            }
        }
        System.out.println(result);
        return result.toString();
    }




    public static String formatTable() {
        String[][] data = {
                {"Alice", "30"},
                {"Bob", "25"}
        };
        String[] headers = {"Name", "Age"};

        int numberOfColumns = headers.length;
        int[] columnWidths = new int[numberOfColumns];
        for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
            columnWidths[columnIndex] = headers[columnIndex].length();
        }
        for (String[] dataRow : data) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
                String cellValue = columnIndex < dataRow.length && dataRow[columnIndex] != null
                        ? dataRow[columnIndex]
                        : "";
                columnWidths[columnIndex] = Math.max(columnWidths[columnIndex], cellValue.length());
            }
        }

        StringBuilder tableBuilder = new StringBuilder();
        appendTableRow(tableBuilder, headers, columnWidths);
        appendTableDivider(tableBuilder, columnWidths);
        for (String[] dataRow : data) {
            appendTableRow(tableBuilder, dataRow, columnWidths);
            appendTableDivider(tableBuilder, columnWidths);
        }
        if (!tableBuilder.isEmpty() && tableBuilder.charAt(tableBuilder.length() - 1) == '\n') {
            tableBuilder.setLength(tableBuilder.length() - 1);
        }
        String formattedTable = tableBuilder.toString();

        System.out.println(formattedTable);
        return formattedTable;
    }




    public static String truncateWithEllipsis(Scanner scanner) {
        System.out.print("Enter text (truncateWithEllipsis): ");
        String text = scanner.nextLine();

        System.out.println("--------------------------------");
        System.out.print("Enter maxLength: ");


        int maxLength = Integer.parseInt(scanner.nextLine().trim());

        if (maxLength < 4) {
            throw new IllegalArgumentException("maxLength must be at least 4");
        }

        if (text.length() > maxLength) {
            String result = text.substring(0, maxLength - 3) + "...";
            System.out.println(result);
            return result;
        }

        System.out.println(text);
        return text;
    }




    public static int countOccurrences(Scanner scanner) {
        System.out.print("Enter text (countOccurrences): ");
        String text = scanner.nextLine();

        System.out.print("Enter pattern: ");
        String pattern = scanner.nextLine();


        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern must not be empty");
        }

        int count = 0;
        int index = 0;

        while (index <= text.length() - pattern.length()) {
            int found = text.indexOf(pattern, index);
            if (found == -1) {
                break;
            }
            count++;
            index = found + pattern.length();
        }

        System.out.println(count);
        return count;
    }




    private static void appendTableRow(StringBuilder tableBuilder, String[] rowCells, int[] columnWidths) {

        for (int columnIndex = 0; columnIndex < columnWidths.length; columnIndex++) {
            String cellValue = columnIndex < rowCells.length && rowCells[columnIndex] != null
                    ? rowCells[columnIndex]
                    : "";
            tableBuilder.append(String.format("%-" + columnWidths[columnIndex] + "s", cellValue));
            if (columnIndex < columnWidths.length - 1) {
                tableBuilder.append(" | ");
            }
        }

        tableBuilder.append('\n');
    }



    private static void appendTableDivider(StringBuilder tableBuilder, int[] columnWidths) {

        for (int columnIndex = 0; columnIndex < columnWidths.length; columnIndex++) {
            tableBuilder.append("-".repeat(columnWidths[columnIndex]));
            if (columnIndex < columnWidths.length - 1) {
                tableBuilder.append("--|--");
            }
        }
        
        tableBuilder.append('\n');
    }
}
