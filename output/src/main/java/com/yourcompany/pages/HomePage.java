package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(css = "#searchBox")
    private WebElement searchBox;

    @FindBy(css = "#searchButton")
    private WebElement searchButton;

    @FindBy(css = ".product-list .product-item")
    private List<WebElement> productItems;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadInput;

    @FindBy(id = "dragDropArea")
    private WebElement dragDropArea;

    @FindBy(css = "#cartIcon")
    private WebElement cartIcon;

    @FindBy(xpath = "//div[@class='message']")
    private WebElement messageDiv;

    @FindBy(css = "nav .nav-link")
    private List<WebElement> navigationLinks;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void clickRegister() {
        registerLink.click();
    }

    public void searchProduct(String productName) {
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchButton.click();
    }

    public int getProductCount() {
        return productItems.size();
    }

    public void selectProductByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            productItems.get(index).click();
        }
    }

    public void uploadFile(String absoluteFilePath) {
        fileUploadInput.sendKeys(absoluteFilePath);
    }

    public void dragAndDrop(String sourceCssSelector) {
        WebElement source = driver.findElement(org.openqa.selenium.By.cssSelector(sourceCssSelector));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, dragDropArea).perform();
    }

    public void clickCartIcon() {
        cartIcon.click();
    }

    public String getMessageText() {
        return messageDiv.getText();
    }

    public void clickNavigationLink(String linkText) {
        for (WebElement link : navigationLinks) {
            if (link.getText().trim().equalsIgnoreCase(linkText)) {
                link.click();
                break;
            }
        }
    }
}
