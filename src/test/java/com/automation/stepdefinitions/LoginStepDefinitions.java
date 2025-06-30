package com.automation.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import com.automation.pages.LoginPage;
import com.automation.pages.InventoryPage;
import com.automation.utils.BrowserManager;
import com.automation.config.UIConfig;
import io.qameta.allure.Step;

/**
 * Step Definitions untuk Login functionality
 * Implements Gherkin steps dengan Cucumber
 */
public class LoginStepDefinitions {
    
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    
    public LoginStepDefinitions() {
        this.loginPage = new LoginPage();
        this.inventoryPage = new InventoryPage();
    }
    
    @Given("I am on the login page")
    @Step("Navigate to login page")
    public void i_am_on_the_login_page() {
        BrowserManager.initializeDriver(UIConfig.DEFAULT_BROWSER);
        loginPage.navigateToLoginPage();
        assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
    }
    
    @Given("I am on the SauceDemo login page")
    @Step("Navigate to SauceDemo login page")
    public void i_am_on_the_saucedemo_login_page() {
        loginPage.navigateToLoginPage();
        assertTrue(loginPage.isLoginPageDisplayed(), "SauceDemo login page should be displayed");
    }
    
    @When("I enter username {string} and password {string}")
    @Step("Enter username: {0} and password: {1}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }
    
    @When("I login with valid credentials")
    @Step("Login with valid credentials")
    public void i_login_with_valid_credentials() {
        loginPage.enterUsername(UIConfig.VALID_USERNAME);
        loginPage.enterPassword(UIConfig.VALID_PASSWORD);
        loginPage.clickLoginButton();
    }
    
    @When("I click the login button")
    @Step("Click login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }
    
    @Then("I should be redirected to the inventory page")
    @Step("Verify redirection to inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        assertTrue(inventoryPage.isInventoryPageDisplayed(), 
                  "Should be redirected to inventory page");
    }
    
    @Then("I should see the products page")
    @Step("Verify products are displayed")
    public void i_should_see_the_products_page() {
        assertTrue(inventoryPage.areProductsDisplayed(), 
                  "Products should be displayed on the page");
    }
    
    @Then("I should see an error message")
    @Step("Verify error message is displayed")
    public void i_should_see_an_error_message() {
        assertTrue(loginPage.isErrorMessageDisplayed(), 
                  "Error message should be displayed");
    }
    
    @Then("I should remain on the login page")
    @Step("Verify still on login page")
    public void i_should_remain_on_the_login_page() {
        assertTrue(loginPage.isLoginPageDisplayed(), 
                  "Should remain on login page");
    }
    
    @Then("I should see an error message containing {string}")
    @Step("Verify error message contains: {0}")
    public void i_should_see_an_error_message_containing(String expectedText) {
        assertTrue(loginPage.isErrorMessageDisplayed(), 
                  "Error message should be displayed");
        String actualErrorMessage = loginPage.getErrorMessage();
        assertTrue(actualErrorMessage.toLowerCase().contains(expectedText.toLowerCase()),
                  "Error message should contain: " + expectedText + 
                  ", but actual message was: " + actualErrorMessage);
    }
    
    @Then("I have added {string} to the cart")
    @Step("Given that item '{0}' was added to cart")
    public void i_have_added_item_to_cart(String itemName) {
        inventoryPage.addItemToCart(itemName);
    }
}
