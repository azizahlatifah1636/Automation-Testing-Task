package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automation.config.UIConfig;

/**
 * Login Page Object untuk SauceDemo
 * Implements Page Object Model pattern
 */
public class LoginPage extends BasePage {
    
    // Page Elements using @FindBy
    @FindBy(id = "user-name")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;
    
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;
    
    @FindBy(className = "login_logo")
    private WebElement loginLogo;
    
    // Constructor
    public LoginPage() {
        super();
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions
    public void navigateToLoginPage() {
        navigateToUrl(UIConfig.BASE_URL);
    }
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }
    
    public void clickLoginButton() {
        click(loginButton);
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    // Validation Methods
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(loginLogo);
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(usernameField);
    }
    
    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField);
    }
    
    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }
}
