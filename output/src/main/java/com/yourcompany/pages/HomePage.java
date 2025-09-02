package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
    private WebDriver driver;
    private Actions actions;

    @FindBy(xpath = "//a[text()='Login']")
    private WebElement loginLink;

    @FindBy(id = "search-input")
    private WebElement searchInput;

    @FindBy(id = "search-btn")
    private WebElement searchButton;

    @FindBy(xpath = "//a[text()='Contact']")
    private WebElement contactLink;

    @FindBy(xpath = "//a[text()='About']")
    private WebElement aboutLink;

    @FindBy(xpath = "//a[text()='Products']")
    private WebElement productsLink;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileUploadInput;

    @FindBy(id = "upload-btn")
    private WebElement uploadButton;

    @FindBy(id = "drag-source")
    private WebElement dragSource;

    @FindBy(id = "drop-target")
    private WebElement dropTarget;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    public void clickLogin() {
        loginLink.click();
    }

    public void enterSearchKeyword(String keyword) {
        searchInput.clear();
        searchInput.sendKeys(keyword);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void goToContactPage() {
        contactLink.click();
    }

    public void goToAboutPage() {
        aboutLink.click();
    }

    public void goToProductsPage() {
        productsLink.click();
    }

    public void uploadFile(String filePath) {
        fileUploadInput.sendKeys(filePath);
        uploadButton.click();
    }

    public void performDragAndDrop() {
        actions.dragAndDrop(dragSource, dropTarget).perform();
    }

    public boolean isLoginLinkDisplayed() {
        return loginLink.isDisplayed();
    }

    public boolean isSearchInputDisplayed() {
        return searchInput.isDisplayed();
    }

    public boolean isContactLinkDisplayed() {
        return contactLink.isDisplayed();
    }
}
