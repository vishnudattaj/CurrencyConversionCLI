import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Currency Converter!");
        System.out.println("1. Convert Currency");
        System.out.println("2. Exit");
        System.out.print("Select an option: ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.print("Enter source currency (e.g. USD): ");
            String sourceCurrency = scanner.next().toUpperCase();

            System.out.print("Enter target currency (e.g. INR): ");
            String targetCurrency = scanner.next().toUpperCase();

            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();

            System.out.println("Converting " + amount + " " + sourceCurrency + " to " + targetCurrency);

        } else if (choice == 2) {
            System.out.println("Goodbye!");
        }

        scanner.close();
    }
}