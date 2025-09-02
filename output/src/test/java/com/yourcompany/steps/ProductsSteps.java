package com.yourcompany.steps;

import com.yourcompany.pages.ProductsPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class ProductsSteps {
    private static final Logger logger = LoggerUtil.getLogger(ProductsSteps.class);
    ProductsPage productsPage = new ProductsPage();
    String addedProductName = "";

    @Given("User is on the Products page")
    public void user_is_on_products_page() {
        String baseUrl = ConfigReader.getProperty("baseURL");
        productsPage.navigateToProductsPage(baseUrl);
        logger.info("Navigated to Products page.");
    }

    @When("User searches for product {string}")
    public void user_searches_for_product(String product) {
        productsPage.enterSearchQuery(product);
        productsPage.clickOnSearchButton();
        logger.info("Searched for product: {}", product);
    }

    @Then("Product results should contain {string}")
    public void product_results_should_contain(String product) {
        boolean result = productsPage.isProductDisplayed(product);
        logger.info("Checking search result for product: {}. Found: {}", product, result);
        Assert.assertTrue("Product not found in the results", result);
    }

    @When("User adds product {string} to cart")
    public void user_adds_product_to_cart(String product) {
        productsPage.addProductToCart(product);
        addedProductName = product;
        logger.info("Added product to cart: {}", product);
    }

    @Then("Product {string} should appear in the shopping cart")
    public void product_should_appear_in_cart(String product) {
        boolean result = productsPage.isProductInCart(product);
        logger.info("Checking if product is in cart: {} => {}", product, result);
        Assert.assertTrue("Product was not added to the cart", result);
    }

    @When("User tries to add out of stock product {string}")
    public void user_tries_to_add_out_of_stock_product(String product) {
        productsPage.addProductToCart(product);
        logger.info("Attempted to add out of stock product: {}", product);
    }

    @Then("User should see an out of stock error message")
    public void out_of_stock_error_message() {
        boolean isError = productsPage.isOutOfStockMessageDisplayed();
        logger.info("Verifying out-of-stock error message. Displayed: {}", isError);
        Assert.assertTrue("Out of stock message not displayed", isError);
    }

    @And("User applies filter {string}")
    public void user_applies_filter(String filterOption) {
        productsPage.selectFilterOption(filterOption);
        logger.info("Filter applied: {}", filterOption);
    }

    @Then("Product list updates according to the filter {string}")
    public void product_list_updates_according_to_filter(String filterOption) {
        boolean filteredCorrectly = productsPage.isProductListFiltered(filterOption);
        logger.info("Product list filtered with option '{}': {}", filterOption, filteredCorrectly);
        Assert.assertTrue("Product list not filtered correctly", filteredCorrectly);
    }

    @When("User uploads product import file")
    public void user_uploads_product_import_file() {
        String filePath = ConfigReader.getProperty("productUploadFile");
        productsPage.uploadProductFile(filePath);
        logger.info("Product import file uploaded: {}", filePath);
    }

    @Then("Products should be imported successfully")
    public void products_should_be_imported_successfully() {
        boolean importStatus = productsPage.isImportSuccessMessageDisplayed();
        logger.info("Products import success status: {}", importStatus);
        Assert.assertTrue("Products not imported successfully", importStatus);
    }
}
