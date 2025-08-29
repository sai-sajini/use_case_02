package com.yourcompany.steps;

import com.yourcompany.pages.ProductsPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ProductsSteps {
    ProductsPage productsPage = new ProductsPage();
    Logger logger = LoggerUtil.getLogger(ProductsSteps.class);

    @Given("user is on the Products page")
    public void user_is_on_products_page() {
        logger.info("Navigating to Products page");
        productsPage.goToProductsPage();
        Assert.assertTrue("Products page did not load!", productsPage.isProductsPageLoaded());
    }

    @When("user searches for a product with name {string}")
    public void user_searches_for_product(String productName) {
        logger.info("Searching for product: " + productName);
        productsPage.enterSearchText(productName);
        productsPage.clickSearchButton();
    }

    @Then("the product titled {string} should be displayed in the results")
    public void product_should_be_displayed(String expectedProductName) {
        boolean found = productsPage.isProductInResults(expectedProductName);
        logger.info("Product display verification for: " + expectedProductName);
        Assert.assertTrue("Product not found in results!", found);
    }

    @Then("the following products should appear in the results:")
    public void products_should_appear(io.cucumber.datatable.DataTable dataTable) {
        List<String> expectedProducts = dataTable.asList();
        for (String prod : expectedProducts) {
            logger.info("Verifying product in results: " + prod);
            Assert.assertTrue(productsPage.isProductInResults(prod));
        }
    }

    @When("user adds product with name {string} to cart")
    public void user_adds_product_to_cart(String productName) {
        logger.info("Adding product to cart: " + productName);
        productsPage.addProductToCart(productName);
        Assert.assertTrue("Product was not added to cart!", productsPage.isProductInCart(productName));
    }

    @Then("the cart should contain product with name {string}")
    public void cart_should_contain_product(String productName) {
        logger.info("Verifying product in cart: " + productName);
        Assert.assertTrue(productsPage.isProductInCart(productName));
    }

    @When("user tries to add an unavailable product to cart")
    public void user_adds_unavailable_product() {
        String unavailableProduct = ConfigReader.get("unavailableProductName");
        logger.info("Trying to add unavailable product: " + unavailableProduct);
        productsPage.addProductToCart(unavailableProduct);
    }

    @Then("an error message {string} should be displayed")
    public void error_message_should_be_displayed(String errorMsg) {
        String actualError = productsPage.getErrorMessage();
        logger.info("Verifying error message: Expecting: " + errorMsg + ", Actual: " + actualError);
        Assert.assertEquals(errorMsg, actualError);
    }

    @When("user filters products by category {string}")
    public void user_filters_products_by_category(String category) {
        logger.info("Filtering products by category: " + category);
        productsPage.selectProductCategory(category);
    }

    @Then("only products from category {string} should be visible")
    public void only_category_products_visible(String category) {
        List<String> visibleProducts = productsPage.getVisibleProductCategories();
        for (String prodCat : visibleProducts) {
            logger.info("Visible product category: " + prodCat);
            Assert.assertEquals(category, prodCat);
        }
    }

    @When("user uploads product test data file")
    public void user_uploads_testdata_file() {
        String filePath = ConfigReader.get("productsTestDataPath");
        logger.info("Uploading product data file: " + filePath);
        productsPage.uploadProductDataFile(filePath);
    }

    @Then("the product list should update accordingly")
    public void product_list_should_update() {
        boolean updated = productsPage.isProductListUpdatedAfterUpload();
        logger.info("Verifying product list update after file upload");
        Assert.assertTrue("Product list did not update!", updated);
    }

    @When("user performs drag and drop of product {string} to cart")
    public void user_drags_product_to_cart(String productName) {
        logger.info("Dragging and dropping product to cart: " + productName);
        productsPage.dragProductToCart(productName);
    }

    @Then("drag and drop confirmation for product {string} should appear")
    public void drag_and_drop_confirmation_should_appear(String productName) {
        boolean confirmed = productsPage.isDragAndDropConfirmed(productName);
        logger.info("Drag and drop confirmation for: " + productName);
        Assert.assertTrue("Drag and Drop confirmation failed", confirmed);
    }
}
