package com.yourcompany.steps;

import com.yourcompany.pages.CartComponent;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.TestDataReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CartSteps {
    private WebDriver driver;
    private CartComponent cartComponent;
    private String baseUrl;
    private String productName;
    private String productQty;
    private String couponCode;

    public CartSteps() {
        this.driver = Hooks.getDriver();
        cartComponent = new CartComponent(driver);
        baseUrl = ConfigReader.getProperty("baseURL");
        productName = TestDataReader.getExcelCell("cart_testdata.xlsx", "AddToCart", 1, 0);
        productQty = TestDataReader.getExcelCell("cart_testdata.xlsx", "AddToCart", 1, 1);
        couponCode = TestDataReader.getExcelCell("cart_testdata.xlsx", "Coupon", 1, 0);
    }

    @Given("user is on the products page")
    public void userIsOnProductsPage() {
        LoggerUtil.info("Navigating to Products page: " + baseUrl + "/products");
        driver.get(baseUrl + "/products");
    }

    @When("user adds product to the cart")
    public void userAddsProductToCart() {
        LoggerUtil.info("Adding product: " + productName);
        cartComponent.addProductToCart(productName, Integer.parseInt(productQty));
    }

    @Then("cart should reflect the added product and correct quantity")
    public void cartShouldReflectProductAndQuantity() {
        LoggerUtil.info("Verifying product and quantity in cart.");
        Assert.assertTrue(cartComponent.isProductInCart(productName), "Product not in cart");
        Assert.assertEquals(cartComponent.getProductQuantity(productName), Integer.parseInt(productQty), "Incorrect quantity in cart");
    }

    @And("user updates quantity to {int}")
    public void userUpdatesQuantity(int newQty) {
        LoggerUtil.info("Updating product quantity for " + productName + " to " + newQty);
        cartComponent.updateProductQuantity(productName, newQty);
    }

    @Then("cart should show the updated quantity")
    public void cartShouldShowUpdatedQuantity() {
        LoggerUtil.info("Verifying updated quantity for " + productName);
        Assert.assertEquals(cartComponent.getProductQuantity(productName), cartComponent.getUpdatedQuantity(productName), "Quantity not updated in cart");
    }

    @And("user applies a valid coupon code")
    public void userAppliesValidCouponCode() {
        LoggerUtil.info("Applying coupon code: " + couponCode);
        cartComponent.applyCouponCode(couponCode);
    }

    @Then("cart should display the discounted price")
    public void cartShouldDisplayDiscountedPrice() {
        double discountedPrice = cartComponent.getDiscountedPrice();
        double originalPrice = cartComponent.getOriginalPrice();
        LoggerUtil.info("Verifying discounted price: before=" + originalPrice + ", after=" + discountedPrice);
        Assert.assertTrue(discountedPrice < originalPrice, "Discount not applied");
    }

    @And("user removes the product from cart")
    public void userRemovesTheProductFromCart() {
        LoggerUtil.info("Removing product from cart: " + productName);
        cartComponent.removeProductFromCart(productName);
    }

    @Then("cart should be empty")
    public void cartShouldBeEmpty() {
        LoggerUtil.info("Verifying cart is empty.");
        Assert.assertTrue(cartComponent.isCartEmpty(), "Cart is not empty after removal");
    }

    @When("user tries to add invalid product")
    public void userTriesToAddInvalidProduct() {
        String invalidProduct = TestDataReader.getExcelCell("cart_testdata.xlsx", "InvalidProduct", 1, 0);
        LoggerUtil.info("Attempting to add invalid product: " + invalidProduct);
        cartComponent.addProductToCart(invalidProduct, 1);
    }

    @Then("system should show error message for invalid product")
    public void systemShowsErrorMessageForInvalidProduct() {
        LoggerUtil.info("Verifying error message for invalid product.");
        Assert.assertTrue(cartComponent.isErrorMessageDisplayed(), "Error message not displayed for invalid product");
    }

    @When("user tries to apply invalid coupon code")
    public void userTriesToApplyInvalidCouponCode() {
        String invalidCoupon = TestDataReader.getExcelCell("cart_testdata.xlsx", "InvalidCoupon", 1, 0);
        LoggerUtil.info("Applying invalid coupon code: " + invalidCoupon);
        cartComponent.applyCouponCode(invalidCoupon);
    }

    @Then("system should show error for invalid coupon code")
    public void systemShouldShowErrorForInvalidCouponCode() {
        LoggerUtil.info("Verifying error for invalid coupon code.");
        Assert.assertTrue(cartComponent.isCouponErrorDisplayed(), "Error not displayed for invalid coupon code");
    }
}
