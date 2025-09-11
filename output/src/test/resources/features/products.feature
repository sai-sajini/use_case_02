Feature: Products Page functionality
  As a user, I want to browse and interact with products
  So that I can view, filter, and add products to my cart

  Background:
    Given I am on the Products page

  Scenario: View all available products
    When I view the product list
    Then I should see all listed products

  Scenario: Filter products by category
    When I filter the products by category "Electronics"
    Then only products in the category "Electronics" should be displayed

  Scenario: Filter products by price range
    When I filter the products by price range 100 to 500
    Then only products with prices between 100 and 500 should be displayed

  Scenario: Add a product to the cart
    When I add the product "Smartphone X" to the cart
    Then the product "Smartphone X" should be added to my cart

  Scenario: Try adding an out-of-stock product to the cart
    When I add the product "Laptop Y (Out of Stock)" to the cart
    Then I should see an error message "Product is out of stock"

  Scenario: Search for a specific product
    When I search for product "Wireless Earbuds"
    Then products matching "Wireless Earbuds" should be displayed

  Scenario: Search with no results
    When I search for product "Nonexistent Gadget"
    Then I should see a message "No products found"
