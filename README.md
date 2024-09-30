# SDY Virtual Medical Claim Test Project

## Overview

This project focuses on automating the functional tests for a Profile Page and a Doctor Calendar Page in a web application using **Selenium WebDriver** with **JUnit 5**.
## Features

- **Page Load Tests**: Ensure each page is loaded after the login procedure.
- **Field Validation**: Ensure values can be entered into each field and correctly reflects the entered value.
- **Mandatory Field Validation**: Ensure mandatory fields cannot be empty in order of creating a new information.
- **Message Validation**: Ensure the message is loaded accordingly.
- **Dropdown Menu Validation**: Ensure each option in the dropdown menu is selectable and correctly reflects the selected value.
- **Checkbox Validation**: Ensure each option checkbox can be checked and unchecked.
- **Button Validation**: Ensure each button functioning accordingly.
- **Calendar Functionality**: Ensure that all calendsrs functionong accordingly.
- **Parameterized Testing**: Uses JUnit 5’s `@ParameterizedTest` to run the test with multiple input values.
- **Selenium WebDriver Automation**: Automates browser interactions to perform dropdown selection and validation.
- **Reset Functionality**: Includes a method to reset values back to its default value.
- **Screenshots**: Captures screenshots.

## Technology Stack

- **Java**: The main programming language used.
- **JUnit 5**: For writing and managing the tests.
- **Selenium WebDriver**: For browser automation.
- **Maven**: For managing dependencies and running the tests.
- **Git**: For version control.

## Prerequisities

- Java 22 or higher
- Maven
- A browser driver (ChromeDriver)
- Git

## Troubleshooting
- **Timeouts**: If the test fails due to timeouts, try increasing the wait duration in the WebDriverWait instance.
- **Driver Issues**: Ensure that the correct version of the browser driver is being used.
