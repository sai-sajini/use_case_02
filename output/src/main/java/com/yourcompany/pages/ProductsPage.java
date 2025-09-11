package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class ProductsPage {
    WebDriver driver;

    @FindBy(xpath = "//h1[contains(text(),'Products')]")
    public WebElement productsHeader;

    @FindBy(xpath = "//input[@id='search-products']")
    public WebElement searchInput;

    @FindBy(xpath = "//button[@id='search-btn']")
    public WebElement searchButton;

    @FindBy(xpath = "//div[@class='product-list']//div[contains(@class,'product-item')]")
    public List<WebElement> productItems;

    @FindBy(xpath = "//span[@id='no-product-message']")
    public WebElement noProductMessage;

    @FindBy(xpath = "//button[contains(@class,'add-to-cart-btn')]")
    public List<WebElement> addToCartButtons;

    @FindBy(xpath = "//div[contains(@class,'cart-popup')]//button[text()='Close']")
    public WebElement closeCartPopupBtn;

    @FindBy(xpath = "//span[@id='cart-count']")
    public WebElement cartCountIcon;

    @FindBy(xpath = "//div[@id='error-overlay']//button[text()='Dismiss']")
    public WebElement errorOverlayDismissBtn;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getProductsHeader() {
        return productsHeader.getText();
    }

    public void enterProductSearch(String query) {
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public int getProductsDisplayedCount() {
        return productItems.size();
    }

    public String getNoProductMessage() {
        return noProductMessage.isDisplayed() ? noProductMessage.getText() : "";
    }

    public void addFirstProductToCart() {
        if (addToCartButtons.size() > 0) {
            addToCartButtons.get(0).click();
        }
    }

    public void addProductToCartByIndex(int index) {
        if (addToCartButtons.size() > index) {
            addToCartButtons.get(index).click();
        }
    }

    public void closeCartPopup() {
        if (closeCartPopupBtn.isDisplayed()) {
            closeCartPopupBtn.click();
        }
    }

    public int getCartCount() {
        String countText = cartCountIcon.getText();
        try {
            return Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void dismissErrorOverlayIfPresent() {
        try {
            if (errorOverlayDismissBtn.isDisplayed()) {
                errorOverlayDismissBtn.click();
            }
        } catch (Exception ignored) {
        }
    }
}
