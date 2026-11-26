import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class CurrencyConverter {

    public static String fetchRates(String sourceCurrency) throws Exception {
        String url = "https://open.er-api.com/v6/latest/" + sourceCurrency;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static double parseRate(String response, String targetCurrency) throws Exception {ObjectMapper mapper = new ObjectMapper();JsonNode root = mapper.readTree(response);
        return root.get("rates").get(targetCurrency).asDouble();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
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

                String response = fetchRates(sourceCurrency);
                double rate = parseRate(response, targetCurrency);

                System.out.println("Exchange rate: " + rate);
                System.out.println();

            } else if (choice == 2) {
                System.out.println();
                System.out.println("Goodbye!");
                break;
            }
        }

        scanner.close();
    }
}