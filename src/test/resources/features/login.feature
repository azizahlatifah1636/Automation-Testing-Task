Feature: User Login Functionality
  As a user of SauceDemo website
  I want to be able to login with valid credentials
  So that I can access the application

  Background:
    Given I am on the login page

  @positive @smoke
  Scenario: Successful login with valid credentials
    When I enter username "standard_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page
    And I should see the products page

  @negative
  Scenario: Login with invalid username
    When I enter username "invalid_user" and password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

  @negative
  Scenario: Login with invalid password
    When I enter username "standard_user" and password "invalid_password"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

  @negative
  Scenario: Login with locked out user
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click the login button
    Then I should see an error message containing "locked out"
    And I should remain on the login page

  @boundary
  Scenario: Login with empty credentials
    When I enter username "" and password ""
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

  @boundary
  Scenario Outline: Login with various invalid credential combinations
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

    Examples:
      | username      | password      |
      |               | secret_sauce  |
      | standard_user |               |
      | special_chars | !@#$%^&*()   |
      | very_long_username_that_exceeds_normal_length | secret_sauce |
