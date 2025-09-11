package com.yourcompany.stepdefinitions;

import com.yourcompany.pages.ProductsPage;
import com.yourcompany.utils.DriverFactory;
import com.yourcompany.utils.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;

public class ProductsSteps {
    private WebDriver driver = DriverFactory.getDriver();
    private ProductsPage productsPage = new ProductsPage(driver);

    @Given("user is on the Products page")
    public void user_is_on_the_products_page() {
        productsPage.goToProductsPage();
        TestUtil.waitForPageLoad(driver);
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
    }

    @When("user searches for product {string}")
    public void user_searches_for_product(String productName) {
        productsPage.enterSearchText(productName);
        productsPage.clickSearchButton();
        TestUtil.waitForPageLoad(driver);
    }

    @Then("product {string} should be displayed in results list")
    public void product_should_be_displayed_in_results_list(String productName) {
        Assert.assertTrue(productsPage.isProductDisplayed(productName));
    }

    @When("user attempts to search for an unavailable product {string}")
    public void user_attempts_to_search_for_an_unavailable_product(String productName) {
        productsPage.enterSearchText(productName);
        productsPage.clickSearchButton();
        TestUtil.waitForPageLoad(driver);
    }

    @Then("error message {string} is shown on Products page")
    public void error_message_is_shown_on_products_page(String errorMessage) {
        Assert.assertEquals(errorMessage, productsPage.getNoProductFoundMessage());
    }

    @And("user adds product {string} to cart")
    public void user_adds_product_to_cart(String productName) {
        productsPage.clickAddToCartButton(productName);
        TestUtil.waitForPageLoad(driver);
    }

    @Then("product {string} should be present in the cart")
    public void product_should_be_present_in_the_cart(String productName) {
        Assert.assertTrue(productsPage.isProductInCart(productName));
    }

    @When("user views details of product {string}")
    public void user_views_details_of_product(String productName) {
        productsPage.clickViewDetails(productName);
        TestUtil.waitForPageLoad(driver);
    }

    @Then("product details page for {string} should be visible")
    public void product_details_page_for_should_be_visible(String productName) {
        Assert.assertTrue(productsPage.isProductDetailsPage(productName));
    }

    @When("user removes product {string} from cart")
    public void user_removes_product_from_cart(String productName) {
        productsPage.removeProductFromCart(productName);
        TestUtil.waitForPageLoad(driver);
    }

    @Then("product {string} should no longer appear in the cart")
    public void product_should_no_longer_appear_in_the_cart(String productName) {
        Assert.assertFalse(productsPage.isProductInCart(productName));
    }

    @When("user adds out-of-stock product {string} to cart")
    public void user_adds_out_of_stock_product_to_cart(String productName) {
        productsPage.clickAddToCartButton(productName);
        TestUtil.waitForPageLoad(driver);
    }

    @Then("cart error message {string} should be displayed")
    public void cart_error_message_should_be_displayed(String expectedErrorMessage) {
        Assert.assertEquals(expectedErrorMessage, productsPage.getCartErrorMessage());
    }
}
