Feature: Products Page Functionality
  As an e-commerce site user
  I want to view, search, filter, add products to cart, and verify error conditions
  So that I can shop seamlessly and reliably

  Background:
    Given I am on the Products page

  @positive
  Scenario: Search for a product and view details
    When I enter "${search_product}" in the search bar
    And I click the search button
    Then I should see the product "${search_product}" listed in the results
    When I click on the product "${search_product}" name
    Then I should see the product details page with name "${search_product}"

  @positive
  Scenario: Filter products by category
    When I select the category "${filter_category}" from category dropdown
    Then all listed products should belong to category "${filter_category}"

  @positive
  Scenario: Add product to cart successfully
    When I search for product "${add_product_name}"
    And I add product "${add_product_name}" to the cart
    Then I should see the success message "Product added to cart successfully"
    And the cart badge should show count "1"

  @negative
  Scenario: Add out-of-stock product to cart
    When I search for out-of-stock product "${out_of_stock_product}"
    And I attempt to add product "${out_of_stock_product}" to the cart
    Then I should see the error message "Product is out of stock"
    And the cart badge should show count "0"

  @negative
  Scenario: Search for non-existent product
    When I enter "${invalid_search_product}" in the search bar
    And I click the search button
    Then I should see message "No products found"
