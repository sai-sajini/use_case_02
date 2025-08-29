package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;

    @FindBy(xpath = "//h1[contains(text(),'Products')]")
    private WebElement productsHeader;

    @FindBy(id = "searchBox")
    private WebElement searchBox;

    @FindBy(id = "searchBtn")
    private WebElement searchBtn;

    @FindBy(id = "sortDropdown")
    private WebElement sortDropdown;

    @FindBy(css = "table#productsTable tbody tr")
    private List<WebElement> productRows;

    @FindBy(css = "button.add-product")
    private WebElement addProductBtn;

    @FindBy(id = "productName")
    private WebElement productNameInput;

    @FindBy(id = "productPrice")
    private WebElement productPriceInput;

    @FindBy(id = "productCategory")
    private WebElement productCategoryDropdown;

    @FindBy(id = "productUpload")
    private WebElement productUploadInput;

    @FindBy(id = "submitProduct")
    private WebElement submitProductBtn;

    @FindBy(id = "productFormAlert")
    private WebElement productFormAlert;

    @FindBy(css = "button.delete-product")
    private List<WebElement> deleteProductBtns;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isProductsHeaderDisplayed() {
        return productsHeader.isDisplayed();
    }

    public void searchProduct(String productName) {
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchBtn.click();
    }

    public void sortProductsBy(String criteria) {
        new Select(sortDropdown).selectByVisibleText(criteria);
    }

    public int getProductCount() {
        return productRows.size();
    }

    public boolean isProductVisible(String productName) {
        for (WebElement row : productRows) {
            if (row.getText().contains(productName)) return true;
        }
        return false;
    }

    public void clickAddProduct() {
        addProductBtn.click();
    }

    public void enterProductDetails(String name, String price, String category) {
        productNameInput.clear();
        productNameInput.sendKeys(name);
        productPriceInput.clear();
        productPriceInput.sendKeys(price);
        new Select(productCategoryDropdown).selectByVisibleText(category);
    }

    public void uploadProductImage(String filePath) {
        productUploadInput.sendKeys(filePath);
    }

    public void submitProductForm() {
        submitProductBtn.click();
    }

    public String getProductFormAlertText() {
        return productFormAlert.getText();
    }

    public void deleteProductByName(String productName) {
        for (WebElement row : productRows) {
            if (row.getText().contains(productName)) {
                WebElement deleteBtn = row.findElement(org.openqa.selenium.By.cssSelector("button.delete-product"));
                deleteBtn.click();
                break;
            }
        }
    }

    public boolean isProductDeleted(String productName) {
        return !isProductVisible(productName);
    }
}
