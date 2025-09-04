package com.yourcompany.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.yourcompany.steps", "com.yourcompany.hooks"},
        plugin = {
            "pretty",
            "html:target/cucumber-html-report",
            "json:target/cucumber.json",
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        tags = "@Smoke or @Regression"
)
public class TestRunner {
    // Test Runner for executing Cucumber tests
}
