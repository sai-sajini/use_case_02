package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    private Actions actions;

    @FindBy(id = "searchBox")
    private WebElement searchBox;

    @FindBy(id = "searchButton")
    private WebElement searchButton;

    @FindBy(css = "#productTable tbody tr")
    private List<WebElement> productRows;

    @FindBy(id = "categoryDropdown")
    private WebElement categoryDropdown;

    @FindBy(xpath = "//button[contains(text(),'Add to Cart')]")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//input[@type='file' and @id='productFileUpload']")
    private WebElement productFileUpload;

    @FindBy(id = "dragSource")
    private WebElement dragSource;

    @FindBy(id = "dropTarget")
    private WebElement dropTarget;

    @FindBy(id = "sortDropdown")
    private WebElement sortDropdown;

    @FindBy(id = "errorMessage")
    private WebElement errorMessage;

    @FindBy(id = "successMessage")
    private WebElement successMessage;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    public void enterSearch(String searchText) {
        searchBox.clear();
        searchBox.sendKeys(searchText);
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public int getProductCount() {
        return productRows.size();
    }

    public void selectCategory(String category) {
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(category);
    }

    public void sortProducts(String sortOption) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);
    }

    public boolean isProductPresent(String productName) {
        for (WebElement row : productRows) {
            if (row.getText().contains(productName)) {
                return true;
            }
        }
        return false;
    }

    public void addProductToCartByName(String productName) {
        for (int i = 0; i < productRows.size(); i++) {
            WebElement row = productRows.get(i);
            if (row.getText().contains(productName)) {
                addToCartButtons.get(i).click();
                break;
            }
        }
    }

    public String getSuccessMessage() {
        return successMessage.isDisplayed() ? successMessage.getText() : "";
    }

    public String getErrorMessage() {
        return errorMessage.isDisplayed() ? errorMessage.getText() : "";
    }

    public void uploadProductFile(String filePath) {
        productFileUpload.sendKeys(filePath);
    }

    public void dragProductToCart() {
        actions.dragAndDrop(dragSource, dropTarget).build().perform();
    }

    public boolean isDragSuccess() {
        return successMessage.isDisplayed() && successMessage.getText().contains("dragged successfully");
    }

    public boolean isAddToCartButtonEnabledForProduct(String productName) {
        for (int i = 0; i < productRows.size(); i++) {
            WebElement row = productRows.get(i);
            if (row.getText().contains(productName)) {
                return addToCartButtons.get(i).isEnabled();
            }
        }
        return false;
    }
}
