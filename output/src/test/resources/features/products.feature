Feature: Products Management
  As a user
  I want to be able to view, search, and add products
  So that I can manage the products easily

  Background:
    Given I am on the products page

  Scenario: User views the products list
    When I see the products table
    Then the products table should be displayed

  Scenario: User searches for a valid product
    When I search for product "Laptop"
    Then I should see product "Laptop" in the results

  Scenario: User searches for an invalid product
    When I search for product "XYZInvalid"
    Then I should see a message "No products found"

  Scenario: User adds a new product successfully
    When I click on Add Product button
    And I enter product details from config
    And I submit the new product
    Then I should see the new product in the products table

  Scenario: User adds a product with missing required fields
    When I click on Add Product button
    And I enter incomplete product details
    And I submit the new product
    Then I should see an error message "All fields are required"
