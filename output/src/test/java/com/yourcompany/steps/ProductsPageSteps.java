package com.yourcompany.steps;

import com.yourcompany.pages.ProductsPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import com.yourcompany.utils.TestDataReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.List;
import java.util.Map;

public class ProductsPageSteps {
    WebDriver driver = Hooks.getDriver();
    ProductsPage productsPage = new ProductsPage(driver);
    String baseUrl = ConfigReader.getProperty("baseURL");
    String productSearchKeyword = ConfigReader.getProperty("productSearchKeyword");
    Map<String, String> testData = TestDataReader.getTestData("products_testdata.xlsx");

    @Given("user is on products page")
    public void userIsOnProductsPage() {
        driver.get(baseUrl + "/products");
        LoggerUtil.info("Navigated to Products page");
        Assert.assertTrue(productsPage.isProductsHeaderDisplayed(), "Products header is not displayed");
    }

    @When("user searches for a product")
    public void userSearchesForAProduct() {
        String keyword = testData.get("search_keyword") != null ? testData.get("search_keyword") : productSearchKeyword;
        productsPage.enterSearchText(keyword);
        productsPage.clickSearchButton();
        LoggerUtil.info("Searched for product: " + keyword);
    }

    @Then("the product list should show relevant results")
    public void productListShouldShowRelevantResults() {
        List<WebElement> resultList = productsPage.getSearchResults();
        Assert.assertTrue(resultList.size() > 0, "No products found in search results");
        String keyword = testData.get("search_keyword") != null ? testData.get("search_keyword") : productSearchKeyword;
        for (WebElement item : resultList) {
            Assert.assertTrue(item.getText().toLowerCase().contains(keyword.toLowerCase()), "Product does not match search keyword: " + item.getText());
        }
        LoggerUtil.info("Search results validated");
    }

    @When("user adds a product to cart from list")
    public void userAddsProductToCartFromList() {
        String productName = testData.get("add_to_cart_product");
        boolean success = productsPage.addProductToCartByName(productName);
        Assert.assertTrue(success, "Could not add product to cart: " + productName);
        LoggerUtil.info("Added product to cart: " + productName);
    }

    @Then("the cart badge should increment")
    public void cartBadgeShouldIncrement() {
        int cartCount = productsPage.getCartBadgeCount();
        Assert.assertTrue(cartCount > 0, "Cart badge did not increment");
        LoggerUtil.info("Cart badge count after addition: " + cartCount);
    }

    @When("user tries to add out-of-stock product to cart")
    public void userTriesToAddOutOfStockProductToCart() {
        String productName = testData.get("out_of_stock_product");
        boolean success = productsPage.addProductToCartByName(productName);
        Assert.assertFalse(success, "Out-of-stock product was added to cart: " + productName);
        LoggerUtil.info("Attempted to add out-of-stock product: " + productName);
    }

    @Then("user should see out-of-stock error message")
    public void userShouldSeeOutOfStockErrorMessage() {
        Assert.assertTrue(productsPage.isOutOfStockErrorDisplayed(), "Out-of-stock error message not displayed");
        LoggerUtil.info("Out-of-stock error message validated");
    }

    @Then("product details for {string} should be correct")
    public void productDetailsForShouldBeCorrect(String productName) {
        Map<String, String> productDetails = productsPage.getProductDetails(productName);
        Assert.assertEquals(productDetails.get("Name"), productName, "Product name mismatch");
        Assert.assertNotNull(productDetails.get("Price"), "Product price missing");
        Assert.assertNotNull(productDetails.get("Stock"), "Product stock information missing");
        LoggerUtil.info("Product details validated for: " + productName);
    }

    @When("user uploads a product image for {string}")
    public void userUploadsProductImageFor(String productName) {
        String imagePath = testData.get("product_image_path");
        boolean uploadSuccess = productsPage.uploadProductImageByName(productName, imagePath);
        Assert.assertTrue(uploadSuccess, "Product image upload failed for: " + productName);
        LoggerUtil.info("Uploaded image for product: " + productName);
    }

    @Then("the product image should be updated for {string}")
    public void productImageShouldBeUpdatedFor(String productName) {
        Assert.assertTrue(productsPage.isProductImageUpdated(productName), "Product image not updated for: " + productName);
        LoggerUtil.info("Product image updated for: " + productName);
    }

    @When("user performs drag and drop of {string} to featured section")
    public void userPerformsDragAndDropOfToFeaturedSection(String productName) {
        boolean dragDropSuccess = productsPage.dragAndDropProductToFeatured(productName);
        Assert.assertTrue(dragDropSuccess, "Failed to drag and drop product: " + productName);
        LoggerUtil.info("Dragged and dropped product to featured: " + productName);
    }

    @Then("featured section should include {string}")
    public void featuredSectionShouldInclude(String productName) {
        Assert.assertTrue(productsPage.isProductInFeaturedSection(productName), "Product not found in featured section: " + productName);
        LoggerUtil.info("Featured section includes product: " + productName);
    }
}
