package com.automation.pages;

import com.automation.utils.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import com.automation.config.UIConfig;
import java.time.Duration;

/**
 * Base Page Object class yang berisi common methods
 * untuk semua page objects
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage() {
        this.driver = BrowserManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(UIConfig.EXPLICIT_WAIT));
    }
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(UIConfig.EXPLICIT_WAIT));
    }
    
    // Common WebDriver operations
    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }
    
    protected void sendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }
    
    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }
    
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void navigateToUrl(String url) {
        driver.get(url);
    }
    
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    protected void refreshPage() {
        driver.navigate().refresh();
    }
}
