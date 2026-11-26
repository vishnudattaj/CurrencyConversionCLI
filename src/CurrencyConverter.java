import java.util.Scanner;
import java.util.InputMismatchException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
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

        if (!root.path("result").asText().equals("success")) {
            throw new IllegalArgumentException("Invalid source currency.");
        }

        if (!root.path("rates").has(targetCurrency)) {
            throw new IllegalArgumentException("Invalid target currency.");
        }

        return root.get("rates").get(targetCurrency).asDouble();
    }

    public static double convertCurrency(double amount, double rate) {
        return amount * rate;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Currency Converter!");
            System.out.println("1. Convert Currency");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter source currency (e.g. USD): ");
                    String sourceCurrency = scanner.next().toUpperCase();

                    System.out.print("Enter target currency (e.g. INR): ");
                    String targetCurrency = scanner.next().toUpperCase();

                    if (!sourceCurrency.matches("[A-Z]{3}") || !targetCurrency.matches("[A-Z]{3}")) {
                        throw new IllegalArgumentException("Currency codes must contain three letters.");
                    }

                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();

                    if (amount < 0) {
                        throw new IllegalArgumentException("Enter a valid non-negative amount.");
                    }

                    System.out.println("Converting " + amount + " " + sourceCurrency + " to " + targetCurrency);

                    String response = fetchRates(sourceCurrency);
                    double rate = parseRate(response, targetCurrency);
                    double convertedAmount = convertCurrency(amount, rate);

                    System.out.println("Exchange rate: " + rate);
                    System.out.printf("%.2f %s = %.2f %s%n", amount, sourceCurrency, convertedAmount, targetCurrency);

                } else if (choice == 2) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option. Please select 1 or 2.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IOException e) {
                System.out.println("Error: Unable to retrieve or process exchange rates.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Request interrupted.");
                break;

            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }

            System.out.println();
        }

        scanner.close();
    }
}