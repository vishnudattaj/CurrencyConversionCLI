import java.util.Scanner;
import java.util.InputMismatchException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class CurrencyConverter {

    public static String fetchRates(String sourceCurrency) throws Exception {
        String url = "https://open.er-api.com/v6/latest/" + sourceCurrency;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API request failed.");
        }

        return response.body();
    }

    public static double parseRate(String response, String targetCurrency) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        // The API can return an error even when an HTTP request succeeds.
        if (!root.path("result").asText().equals("success")) {
            throw new IllegalArgumentException("Invalid source currency.");
        }

        // A valid three-letter currency code may not be supported by the API.
        if (!root.path("rates").has(targetCurrency)) {
            throw new IllegalArgumentException("Invalid target currency.");
        }

        return root.get("rates").get(targetCurrency).asDouble();
    }

    public static double convertCurrency(double amount, double rate) {
        return amount * rate;
    }

    public static void saveConversion(String conversion) throws IOException {
        // Append mode preserves previous conversions between program sessions.
        try (FileWriter writer = new FileWriter("history.txt", true)) {
            writer.write(conversion + "\n");
        }
    }

    public static void viewHistory() throws IOException {
        File file = new File("history.txt");

        if (!file.exists()) {
            System.out.println("\nNo conversion history found.");
            return;
        }

        System.out.println("\nConversion History:");

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
        }
    }

    public static void clearHistory() throws IOException {
        // Overwrite the file instead of deleting it to keep storage consistent.
        try (FileWriter writer = new FileWriter("history.txt", false)) {
            writer.write("");
        }

        System.out.println("Conversion history cleared.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Currency Converter!");
            System.out.println("1. Convert Currency");
            System.out.println("2. View Conversion History");
            System.out.println("3. Clear Conversion History");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter source currency (e.g. USD): ");
                    String sourceCurrency = scanner.next().toUpperCase();

                    System.out.print("Enter target currency (e.g. INR): ");
                    String targetCurrency = scanner.next().toUpperCase();

                    // Validate currency formats before making an unnecessary API request.
                    if (!sourceCurrency.matches("[A-Z]{3}") || !targetCurrency.matches("[A-Z]{3}")) {
                        throw new IllegalArgumentException("Currency codes must contain three letters.");
                    }

                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();

                    // Scanner accepts NaN and Infinity as doubles, so we reject them.
                    if (amount < 0 || !Double.isFinite(amount)) {
                        throw new IllegalArgumentException("Enter a valid non-negative amount.");
                    }

                    System.out.println("Converting " + amount + " " + sourceCurrency + " to " + targetCurrency);

                    String response = fetchRates(sourceCurrency);
                    double rate = parseRate(response, targetCurrency);
                    double convertedAmount = convertCurrency(amount, rate);

                    String conversion = String.format("%.2f %s = %.2f %s",
                            amount, sourceCurrency, convertedAmount, targetCurrency);

                    System.out.println("Exchange rate: " + rate);
                    System.out.println(conversion);

                    saveConversion(conversion);

                } else if (choice == 2) {
                    viewHistory();

                } else if (choice == 3) {
                    clearHistory();

                } else if (choice == 4) {
                    System.out.println("Goodbye!");
                    break;

                } else {
                    System.out.println("Invalid option. Please select 1, 2, 3, or 4.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IOException e) {
                System.out.println("Error: API request or file operation failed.");

            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }

            System.out.println();
        }

        scanner.close();
    }
}