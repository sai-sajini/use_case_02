package com.yourcompany.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.yourcompany.utils.DriverManager;
import com.yourcompany.utils.ExtentReportManager;
import com.yourcompany.utils.LoggerUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class Hooks {
    private WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        LoggerUtil.getLogger().info("==============================");
        LoggerUtil.getLogger().info("Starting Scenario: " + scenario.getName());
        DriverManager.initDriver();
        driver = DriverManager.getDriver();
        ExtentReportManager.initReport();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Scenario Screenshot");
                LoggerUtil.getLogger().error("Scenario failed. Screenshot taken.");
            } catch (Exception e) {
                LoggerUtil.getLogger().error("Unable to take screenshot: " + e.getMessage());
            }
            ExtentCucumberAdapter.addTestStepLog("Scenario failed: screenshot captured.");
        } else {
            LoggerUtil.getLogger().info("Scenario passed.");
        }

        ExtentReportManager.flushReport();
        DriverManager.quitDriver();
        LoggerUtil.getLogger().info("Browser closed and driver quit.");
        LoggerUtil.getLogger().info("==============================");
    }
}
