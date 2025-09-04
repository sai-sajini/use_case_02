Feature: Cart Component
  In order to manage my shopping efficiently
  As a user
  I want to add, view, update, and remove products in the cart and proceed to checkout

  Background:
    Given I am logged in as user using valid credentials
    And I am on the Products page

  # Positive Scenario: Adding an item to cart
  Scenario: Add product to cart successfully
    When I add product "<productName>" with quantity "<quantity>" to the cart
    Then the cart should display "<productName>" with quantity "<quantity>"
    And the total price for "<productName>" should be calculated correctly
    And log cart details

  # Negative Scenario: Adding out of stock product
  Scenario: Add out-of-stock product to cart
    When I add product "<outOfStockProduct>" with quantity "1" to the cart
    Then an error message should be displayed for out-of-stock product
    And product should not be listed in the cart

  # Positive Scenario: Update quantity in cart
  Scenario: Update product quantity in cart
    Given I have "<productName>" in the cart with quantity "1"
    When I update the quantity of "<productName>" to "5" in the cart
    Then the cart should display "<productName>" with quantity "5"
    And the total price for "<productName>" should reflect updated quantity

  # Negative Scenario: Update quantity to zero
  Scenario: Update cart quantity to zero
    Given I have "<productName>" in the cart with quantity "1"
    When I update the quantity of "<productName>" to "0" in the cart
    Then "<productName>" should be removed from the cart
    And cart should display appropriate empty message

  # Positive Scenario: Remove product from cart
  Scenario: Remove product from cart
    Given I have "<productName>" in the cart
    When I remove "<productName>" from the cart
    Then "<productName>" should not be visible in the cart
    And the cart items count should decrease

  # Positive Scenario: Checkout flow
  Scenario: Proceed to checkout from cart
    Given I have items in the cart
    When I click the "Checkout" button
    Then I should be navigated to the checkout page
    And the cart items should be displayed for confirmation
    And log checkout start

  Examples:
    | productName      | quantity | outOfStockProduct |
    | Laptop Pro 15    | 2        | Magic Headphones  |
    | Wireless Mouse   | 3        | Phantom Speaker   |
