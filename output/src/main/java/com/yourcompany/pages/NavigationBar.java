package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import org.apache.logging.log4j.Logger;
import com.yourcompany.utils.LoggerUtil;

public class NavigationBar {
    private WebDriver driver;
    private Logger log = LoggerUtil.getLogger(NavigationBar.class);

    @FindBy(xpath = "//nav//a[contains(text(),'Home')]")
    private WebElement homeLink;

    @FindBy(xpath = "//nav//a[contains(text(),'About')]")
    private WebElement aboutLink;

    @FindBy(xpath = "//nav//a[contains(text(),'Products')]")
    private WebElement productsLink;

    @FindBy(xpath = "//nav//a[contains(text(),'Contact')]")
    private WebElement contactLink;

    @FindBy(xpath = "//nav//button[contains(@class,'dropdown-toggle')]")
    private WebElement moreDropdown;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickHome() {
        log.info("Clicking on Home link in Navigation Bar.");
        homeLink.click();
    }

    public void clickAbout() {
        log.info("Clicking on About link in Navigation Bar.");
        aboutLink.click();
    }

    public void clickProducts() {
        log.info("Clicking on Products link in Navigation Bar.");
        productsLink.click();
    }

    public void clickContact() {
        log.info("Clicking on Contact link in Navigation Bar.");
        contactLink.click();
    }

    public void openMoreDropdown() {
        log.info("Opening More dropdown in Navigation Bar.");
        Actions actions = new Actions(driver);
        actions.moveToElement(moreDropdown).click().perform();
    }

    public boolean isHomeActive() {
        boolean active = homeLink.getAttribute("class") != null && homeLink.getAttribute("class").contains("active");
        log.info("Home link active state: " + active);
        return active;
    }

    public boolean isAboutActive() {
        boolean active = aboutLink.getAttribute("class") != null && aboutLink.getAttribute("class").contains("active");
        log.info("About link active state: " + active);
        return active;
    }

    public boolean isProductsActive() {
        boolean active = productsLink.getAttribute("class") != null && productsLink.getAttribute("class").contains("active");
        log.info("Products link active state: " + active);
        return active;
    }

    public boolean isContactActive() {
        boolean active = contactLink.getAttribute("class") != null && contactLink.getAttribute("class").contains("active");
        log.info("Contact link active state: " + active);
        return active;
    }

    public boolean isDisplayed() {
        boolean visible = homeLink.isDisplayed() && aboutLink.isDisplayed() && productsLink.isDisplayed() && contactLink.isDisplayed();
        log.info("NavigationBar visible: " + visible);
        return visible;
    }
}
