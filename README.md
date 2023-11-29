# BookStore Trade Application

## Overview

The BookStore Trade Application is a Java-based command-line application for managing book orders and trading activities. It provides functionality for users to log in, place orders, close orders, check profit/loss, and perform various trading-related tasks.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Testing](#testing)
- [Contributing](#contributing)
- [Login User (Optional)](#login-user-optional)
- [Contact](#contact)

## Prerequisites

- Java Development Kit (JDK) 21 or later
- Apache Maven (for building and managing dependencies)

## Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/BookStoreTradeApp.git
    ```

2. Navigate to the project directory:

    ```bash
    cd BookStoreTradeApp
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/BookStoreTradingApp-jar-with-dependencies.jar
    ```

## Usage

- Follow the on-screen instructions to navigate through the application.
- If you're a new user, choose the "Register" option to create an account.
- If you're an existing user, choose the "Login" option and provide your credentials.
- Use the main menu options to perform various tasks such as placing orders, checking profit/loss, and more.

## Project Structure

The project follows a modular structure:

- **com.m2.bookstore:** Main package containing application logic.
- **com.m2.bookstore.utils.dialog:** Utility classes for handling user dialogs.
- **com.m2.bookstore.utils.order:** Utility classes for order-related calculations and printing.
- **com.m2.bookstore.service:** Interfaces and implementations for user and order services.
- **com.m2.bookstore.model:** Classes representing entities like User and Order.
- **com.m2.bookstore.price:** Classes related to price data and generation.
- **com.m2.bookstore.scheduler:** Classes for scheduling tasks related to trade updates.

## Dependencies

- Java 21
- Sonar
- JUnit 5 (for testing)
- Mockito (for mocking in tests)
- AssertJ (for fluent assertions in tests)

## Testing

The project includes unit tests to ensure the correctness of the implemented functionalities. You can run the tests using Maven:

  ```bash
  mvn test
  ```
## Contributing
Feel free to contribute by opening issues, suggesting features, or submitting pull requests. Contributions are welcome!

## Login User (Optional)

For testing purposes, you can use the following optional login credentials:

- Username: `engmoabdu`
- Password: `messi`

Please note that these credentials are for testing the application and if you need to register new one you can do that.

## Contact

If you have any questions, concerns, or suggestions, feel free to reach out to us:

- Email: `eng.mo.abdu@gmail.com`
- Phone: `+971 (50) 141-4755`
- GitHub Repository: [BookStoreTradeApp](https://github.com/engMoAbdu/BookStoreTradingApp)
