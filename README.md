# Currency Converter

A Java command-line application that converts currencies using current exchange rates and stores conversion history.

## The Problem

Manually looking up exchange rates and calculating currency conversions can be inconvenient. This project provides a simple command-line tool that allows users to quickly convert currencies and keep track of previous conversions.

## How It Works

The application accepts a source currency, target currency, and amount through a command-line interface. It retrieves current exchange rates from ExchangeRate-API using Java HttpClient and parses the JSON response using Jackson.

The application calculates and displays the converted amount and saves successful conversions to a local text file. Users can also view or clear their conversion history.

## Features

- Convert between supported currencies.
- Retrieve current exchange rates from an external API.
- Handle invalid inputs and API failures.
- Automatically save successful conversions.
- View and clear persistent conversion history.

## Tech Stack

- Java: Main programming language
- Java HttpClient: HTTP requests
- Jackson: JSON parsing
- Scanner: Command-line input
- Java File I/O: Persistent conversion history
- ExchangeRate-API: Currency exchange rate data
- JUnit 5: Unit testing
- Git/GitHub: Version control

## Setup and Installation

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Git
- Internet connection

All required external JAR files are included in the `lib/` directory. Maven and an API key are not required.

### 1. Clone the Repository

```bash
git clone https://github.com/vishnudattaj/CurrencyConversionCLI.git
cd CurrencyConversionCLI
```

### 2. Compile the Application

```bash
javac -cp "lib/*" src/CurrencyConverter.java
```

### 3. Run the Application

```bash
java -cp "src:lib/*" CurrencyConverter
```

For Windows, replace the classpath separator (`:`) with a semicolon (`;`).

### Example Usage

```text
Welcome to Currency Converter!
1. Convert Currency
2. View Conversion History
3. Clear Conversion History
4. Exit
Select an option: 1
Enter source currency (e.g. USD): USD
Enter target currency (e.g. INR): INR
Enter amount: 100
Converting 100.0 USD to INR
Exchange rate: 88.5
100.00 USD = 8850.00 INR
```

Exchange rates and conversion results will vary based on the latest available API data.

### Conversion History

Successful conversions are automatically saved to `history.txt`.

- Select option 2 to view previous conversions.
- Select option 3 to clear all saved conversions.
- Select option 4 to exit the application.

Conversion history persists between sessions.

## Running Tests

The project uses JUnit 5 to test conversion calculations, JSON parsing, and error handling.

Compile the application and tests:

```bash
javac -cp "lib/*:src" src/*.java tests/*.java
```

Run the tests:

```bash
java -cp "lib/*:src:tests" org.junit.platform.console.ConsoleLauncher execute --scan-classpath
```

## Project Documentation

- [Eisenhower Matrix](docs/Eisenhower_Matrix.pdf)
- [System Diagram](docs/ProjectFlowchart.png)

## Roadmap

### Completed

- [x] Set up Java project and GitHub repository.
- [x] Create basic CLI interface.
- [x] Implement currency and amount input.
- [x] Connect to ExchangeRate-API.
- [x] Parse API responses using Jackson.
- [x] Implement currency conversion calculations.
- [x] Add error handling for invalid inputs and API failures.
- [x] Implement JUnit tests.
- [x] Implement persistent conversion history using file I/O.
- [x] Add functionality to view and clear conversion history.

### Future Plans

- [ ] Develop a REST API using Spring Boot.
- [ ] Add user authentication.
- [ ] Migrate file-based storage to a database.
