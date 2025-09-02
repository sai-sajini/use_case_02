package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;

    @FindBy(id = "products-title")
    private WebElement productsTitle;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(id = "search-button")
    private WebElement searchButton;

    @FindBy(xpath = "//table[@id='products-table']/tbody/tr")
    private List<WebElement> productRows;

    @FindBy(xpath = "//select[@id='category-dropdown']")
    private WebElement categoryDropdown;

    @FindBy(id = "add-product-button")
    private WebElement addProductButton;

    @FindBy(id = "product-name")
    private WebElement inputProductName;

    @FindBy(id = "product-desc")
    private WebElement inputProductDesc;

    @FindBy(id = "product-price")
    private WebElement inputProductPrice;

    @FindBy(xpath = "//select[@id='product-category']")
    private WebElement selectProductCategory;

    @FindBy(css = "button.submit-product")
    private WebElement submitProductButton;

    @FindBy(xpath = "//span[@id='product-upload-label']")
    private WebElement productUploadLabel;

    @FindBy(xpath = "//input[@type='file' and @id='product-file-upload']")
    private WebElement fileUploadInput;

    @FindBy(xpath = "//div[@id='drag-drop-area']")
    private WebElement dragDropArea;

    @FindBy(xpath = "//div[@id='message-box']")
    private WebElement messageBox;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isProductsTitleDisplayed() {
        return productsTitle.isDisplayed();
    }

    public void searchProduct(String productName) {
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchButton.click();
    }

    public int getProductsCount() {
        return productRows.size();
    }

    public void selectCategory(String category) {
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(category);
    }

    public void clickAddProductButton() {
        addProductButton.click();
    }

    public void enterProductDetails(String name, String desc, String price, String category) {
        inputProductName.clear();
        inputProductName.sendKeys(name);
        inputProductDesc.clear();
        inputProductDesc.sendKeys(desc);
        inputProductPrice.clear();
        inputProductPrice.sendKeys(price);
        Select select = new Select(selectProductCategory);
        select.selectByVisibleText(category);
    }

    public void submitProduct() {
        submitProductButton.click();
    }

    public String getMessageBoxText() {
        return messageBox.getText();
    }

    public void uploadProductFile(String absoluteFilePath) {
        fileUploadInput.sendKeys(absoluteFilePath);
    }

    public void dragAndDropProductFile(String absoluteFilePath) {
        // Use Selenium's drag and drop
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.dragAndDrop(fileUploadInput, dragDropArea).perform();
    }

    public boolean isProductPresent(String productName) {
        for (WebElement row : productRows) {
            if (row.getText().contains(productName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductUploadLabelDisplayed() {
        return productUploadLabel.isDisplayed();
    }
}
