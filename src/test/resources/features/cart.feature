Feature: Shopping Cart Management
  As a user of SauceDemo
  I want to be able to add and remove items from my cart
  So that I can manage my purchases effectively

  Background:
    Given I am on the SauceDemo login page
    When I login with valid credentials
    Then I should be redirected to the inventory page

  @positive @smoke
  Scenario: Add single item to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart should show 1 item
    And the cart icon should display the correct count

  @positive @regression
  Scenario: Add multiple items to cart
    When I add the following items to cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then the cart should show 3 items
    And the cart icon should display the correct count

  @positive @regression
  Scenario: Remove item from cart
    Given I have added "Sauce Labs Backpack" to the cart
    When I remove "Sauce Labs Backpack" from the cart
    Then the cart should show 0 items
    And the cart icon should not display any count

  @negative @edge-case
  Scenario: Verify cart persistence across pages
    When I add "Sauce Labs Backpack" to the cart
    And I navigate to the cart page
    And I navigate back to the inventory page
    Then the cart should still show 1 item

  @boundary @edge-case
  Scenario: Add all available items to cart
    When I add all available items to the cart
    Then the cart should show 6 items
    And all items should be present in the cart
