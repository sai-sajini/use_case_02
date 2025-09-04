package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class CartComponent {
    WebDriver driver;

    @FindBy(id = "cart-icon")
    private WebElement cartIcon;

    @FindBy(css = "#cart-modal")
    private WebElement cartModal;

    @FindBy(css = ".cart-items-table tbody tr")
    private List<WebElement> cartItemsRows;

    @FindBy(css = ".remove-item-btn")
    private List<WebElement> removeItemButtons;

    @FindBy(id = "checkout-btn")
    private WebElement checkoutButton;

    @FindBy(id = "cart-total")
    private WebElement cartTotal;

    @FindBy(id = "quantity-select")
    private List<WebElement> quantityDropdowns;

    @FindBy(id = "clear-cart-btn")
    private WebElement clearCartButton;

    @FindBy(id = "upload-cart-file")
    private WebElement uploadCartFileInput;

    @FindBy(css = "#drag-drop-zone")
    private WebElement dragDropZone;

    public CartComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openCart() {
        cartIcon.click();
    }

    public boolean isCartModalDisplayed() {
        return cartModal.isDisplayed();
    }

    public int getCartItemsCount() {
        return cartItemsRows.size();
    }

    public String getCartTotal() {
        return cartTotal.getText();
    }

    public void clickCheckout() {
        checkoutButton.click();
    }

    public void removeItemByIndex(int index) {
        if(index >= 0 && index < removeItemButtons.size()) {
            removeItemButtons.get(index).click();
        }
    }

    public void updateItemQuantity(int itemIndex, int quantity) {
        if(itemIndex >= 0 && itemIndex < quantityDropdowns.size()) {
            Select select = new Select(quantityDropdowns.get(itemIndex));
            select.selectByValue(String.valueOf(quantity));
        }
    }

    public void clearCart() {
        clearCartButton.click();
    }

    public void uploadCartFile(String filePath) {
        uploadCartFileInput.sendKeys(filePath);
    }

    public boolean isDragDropZoneDisplayed() {
        return dragDropZone.isDisplayed();
    }

    public void dragAndDropToCart(WebElement source) {
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.dragAndDrop(source, dragDropZone).perform();
    }

    public List<WebElement> getCartItemRows() {
        return cartItemsRows;
    }

    public String getCartItemName(int index) {
        if(index >=0 && index < cartItemsRows.size()) {
            WebElement row = cartItemsRows.get(index);
            return row.findElement(org.openqa.selenium.By.cssSelector(".cart-item-name")).getText();
        }
        return null;
    }

    public String getCartItemPrice(int index) {
        if(index >=0 && index < cartItemsRows.size()) {
            WebElement row = cartItemsRows.get(index);
            return row.findElement(org.openqa.selenium.By.cssSelector(".cart-item-price")).getText();
        }
        return null;
    }

    public void increaseQuantity(int itemIndex) {
        if(itemIndex >= 0 && itemIndex < quantityDropdowns.size()) {
            Select select = new Select(quantityDropdowns.get(itemIndex));
            int current = Integer.parseInt(select.getFirstSelectedOption().getText());
            select.selectByValue(String.valueOf(current + 1));
        }
    }

    public void decreaseQuantity(int itemIndex) {
        if(itemIndex >= 0 && itemIndex < quantityDropdowns.size()) {
            Select select = new Select(quantityDropdowns.get(itemIndex));
            int current = Integer.parseInt(select.getFirstSelectedOption().getText());
            int next = current > 1 ? current - 1 : 1;
            select.selectByValue(String.valueOf(next));
        }
    }
}
