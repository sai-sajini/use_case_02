package com.yourcompany.hooks;

import com.yourcompany.utils.DriverManager;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.ExtentReportManager;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private WebDriver driver;
    private LoggerUtil logger = LoggerUtil.getInstance();

    @Before
    public void beforeScenario(Scenario scenario) {
        driver = DriverManager.getDriver();
        logger.info("Starting scenario: " + scenario.getName());
        ExtentReportManager.createTest(scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
                ExtentReportManager.failTestWithScreenshot(screenshot);
                logger.error("Scenario failed. Screenshot captured.");
            } catch (Exception e) {
                logger.error("Error capturing screenshot: " + e.getMessage());
            }
        } else {
            ExtentReportManager.passTest();
        }
        logger.info("Scenario ended: " + scenario.getName());
        DriverManager.quitDriver();
    }
}
