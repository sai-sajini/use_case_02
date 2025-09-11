package com.yourcompany.hooks;

import com.yourcompany.utils.DriverFactory;
import com.yourcompany.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        String browser = ConfigReader.getProperty("browser");
        driver = DriverFactory.initDriver(browser);
        driver.manage().window().maximize();
        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicit_wait", "10"));
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(implicitWait));
        driver.get(ConfigReader.getProperty("url"));
    }

    @After
    public void tearDown(Scenario scenario) {
        driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
        DriverFactory.quitDriver();
    }
}
