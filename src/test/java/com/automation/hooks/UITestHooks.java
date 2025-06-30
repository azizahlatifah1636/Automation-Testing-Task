package com.automation.hooks;

import com.automation.utils.BrowserManager;
import com.automation.config.UIConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Cucumber Hooks for UI Test Setup and Teardown
 * 
 * This class contains hooks that run before and after each scenario:
 * - @Before: Initialize WebDriver and browser
 * - @After: Take screenshot on failure, cleanup WebDriver
 * 
 * Features:
 * - Automatic browser setup and teardown
 * - Screenshot capture on test failure
 * - Allure reporting integration
 * - Proper resource cleanup
 * 
 * @author QA Automation Framework
 */
public class UITestHooks {
    
    private static final Logger logger = LoggerFactory.getLogger(UITestHooks.class);
    
    /**
     * Setup hook that runs before each scenario
     * Initializes WebDriver and opens browser
     * 
     * @param scenario Current scenario being executed
     */
    @Before
    public void setUp(Scenario scenario) {
        logger.info("Setting up browser for scenario: {}", scenario.getName());
        BrowserManager.initializeDriver(UIConfig.DEFAULT_BROWSER);
        logger.info("Browser setup completed successfully");
    }
    
    /**
     * Teardown hook that runs after each scenario
     * Takes screenshot on failure and closes browser
     * 
     * @param scenario Current scenario that was executed
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            WebDriver driver = BrowserManager.getDriver();
            
            // Take screenshot if scenario failed
            if (scenario.isFailed() && driver != null) {
                logger.warn("Scenario failed: {}. Taking screenshot...", scenario.getName());
                takeScreenshotOnFailure(scenario, driver);
            }
            
            // Log scenario result
            if (scenario.isFailed()) {
                logger.error("Scenario FAILED: {}", scenario.getName());
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
            }
            
        } catch (Exception e) {
            logger.error("Error in tearDown: {}", e.getMessage(), e);
        } finally {
            // Always cleanup browser resources
            BrowserManager.quitDriver();
            logger.info("Browser cleanup completed");
        }
    }
    
    /**
     * Takes screenshot and attaches it to Cucumber report and Allure report
     * 
     * @param scenario Failed scenario
     * @param driver WebDriver instance
     */
    private void takeScreenshotOnFailure(Scenario scenario, WebDriver driver) {
        try {
            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            
            // Attach to Cucumber report
            scenario.attach(screenshot, "image/png", "Screenshot on Failure");
            
            // Attach to Allure report
            Allure.addAttachment("Screenshot - " + scenario.getName(), 
                               "image/png", 
                               new ByteArrayInputStream(screenshot), 
                               "png");
            
            logger.info("Screenshot captured and attached to reports");
            
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage(), e);
        }
    }
}
