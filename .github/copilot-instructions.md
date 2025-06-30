<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# API Automation Framework - Copilot Instructions

This is an API Automation Framework project using Java, JUnit 5, REST Assured, and Allure for reporting.

## Project Structure
- Use Java 11+ features and best practices
- Follow REST Assured patterns for API testing
- Implement comprehensive test scenarios (positive, negative, boundary)
- Use Allure annotations for better reporting
- Follow Page Object Model pattern for API endpoints

## Code Style Guidelines
- Use descriptive test method names with pattern: `test{Action}_{TestType}`
- Add proper Allure annotations (@Epic, @Feature, @Story, @Severity)
- Include comprehensive assertions for all test cases
- Use TestDataGenerator for creating test data
- Implement proper error handling and logging

## Testing Best Practices
- Write independent and isolated tests
- Use parameterized tests for boundary testing
- Include setup and teardown methods when needed
- Add meaningful descriptions for test cases
- Validate both status codes and response body content

## Framework Components
- `ApiConfig`: Contains all API endpoints and configuration
- `BaseApiUtils`: REST Assured configuration and common utilities
- `TestDataGenerator`: Generate random test data using Faker
- Model classes: POJO classes for API request/response mapping

When generating new test classes or methods, follow the existing patterns and include proper documentation.
