# Currency Converter

## Project Goal
Build a Java command-line application that converts amounts between different currencies using real-time exchange rates from an external API.

## MVP Features
- Accept source currency, target currency, and amount through the CLI.
- Retrieve current exchange rates using an external API.
- Calculate and display converted amounts.
- Handle invalid inputs and API errors.

## Future Plans
- Develop a REST API using Spring Boot.
- Add user authentication.
- Store conversion history using an H2 database.

## Tech Stack
- Java: Main programming language
- Java HttpClient: Send HTTP requests
- Jackson: Parse JSON responses
- Scanner: Handle CLI input
- ExchangeRate-API: Retrieve exchange rates
- Git/GitHub: Version control

## Project Documentation
- [Eisenhower Matrix](docs/Eisenhower_Matrix.pdf)
- [System Diagram](docs/ProjectFlowchart.png)

## Development Plan
1. Set up Java project and GitHub repository.
2. Create basic CLI interface.
3. Implement currency and amount input.
4. Connect to ExchangeRate-API.
5. Parse API responses using Jackson.
6. Implement currency conversion calculations.
7. Add error handling.
8. Test the application.
