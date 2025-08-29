Feature: Products Page
  Products page displays a list of products and allows user actions such as search, filter, add to cart, and submit order.

  Background:
    Given user is on the Home page
    And navigates to Products page

  Scenario: Positive - Search for available product and add to cart
    When user searches for product "Laptop" in the search box
    And filters by category "Electronics"
    And adds product "Laptop Model X" to the cart
    Then product "Laptop Model X" should be listed in the cart
    And order button should be enabled

  Scenario: Negative - Search for unavailable product
    When user searches for product "RandomNonExistingProduct" in the search box
    Then the products list should display "No products found"
    And add to cart button should not be enabled

  Scenario: Positive - Submit order for multiple products
    When user adds product "Laptop Model X" to the cart
    And adds product "Mouse Pro" to the cart
    And clicks order button
    Then confirmation message should display "Order submitted successfully!"

  Scenario: Negative - Attempt order with empty cart
    When user clicks order button
    Then error message should display "Cart is empty. Please add products before ordering."
