package com.automation.stepdefinitions;

import com.automation.pages.InventoryPage;
import com.automation.utils.BrowserManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Step Definitions for Shopping Cart Management
 * 
 * Contains step implementations for cart-related scenarios:
 * - Adding items to cart
 * - Removing items from cart
 * - Verifying cart state
 * - Cart persistence testing
 * 
 * @author QA Automation Framework
 */
public class CartStepDefinitions {
    
    private WebDriver driver = BrowserManager.getDriver();
    private InventoryPage inventoryPage = new InventoryPage(driver);
    
    @When("I add {string} to the cart")
    @Step("Adding item '{0}' to cart")
    public void iAddItemToCart(String itemName) {
        inventoryPage.addItemToCart(itemName);
    }
    
    @When("I add the following items to cart:")
    @Step("Adding multiple items to cart")
    public void iAddMultipleItemsToCart(DataTable dataTable) {
        List<String> items = dataTable.asList();
        for (String item : items) {
            inventoryPage.addItemToCart(item);
        }
    }
    
    @When("I remove {string} from the cart")
    @Step("Removing item '{0}' from cart")
    public void iRemoveItemFromCart(String itemName) {
        inventoryPage.removeItemFromCart(itemName);
    }
    
    @When("I add all available items to the cart")
    @Step("Adding all available items to cart")
    public void iAddAllItemsToCart() {
        inventoryPage.addAllItemsToCart();
    }
    
    @When("I navigate to the cart page")
    @Step("Navigating to cart page")
    public void iNavigateToCartPage() {
        inventoryPage.goToCart();
    }
    
    @When("I navigate back to the inventory page")
    @Step("Navigating back to inventory page")
    public void iNavigateBackToInventoryPage() {
        driver.navigate().back();
        // Wait for page to load
        inventoryPage.waitForPageLoad();
    }
    
    @Then("the cart should show {int} item(s)")
    @Step("Verifying cart shows {0} items")
    public void theCartShouldShowItems(int expectedCount) {
        int actualCount = inventoryPage.getCartItemCount();
        Assertions.assertEquals(expectedCount, actualCount, 
            String.format("Expected cart to show %d items but found %d", expectedCount, actualCount));
    }
    
    @Then("the cart icon should display the correct count")
    @Step("Verifying cart icon displays correct count")
    public void theCartIconShouldDisplayCorrectCount() {
        Assertions.assertTrue(inventoryPage.isCartIconDisplayingCount(), 
            "Cart icon should display item count when items are present");
    }
    
    @Then("the cart icon should not display any count")
    @Step("Verifying cart icon does not display count")
    public void theCartIconShouldNotDisplayCount() {
        Assertions.assertFalse(inventoryPage.isCartIconDisplayingCount(), 
            "Cart icon should not display count when cart is empty");
    }
    
    @Then("the cart should still show {int} item(s)")
    @Step("Verifying cart persistence shows {0} items")
    public void theCartShouldStillShowItems(int expectedCount) {
        // This is the same as checking cart count, but with different step name for clarity
        theCartShouldShowItems(expectedCount);
    }
    
    @Then("all items should be present in the cart")
    @Step("Verifying all items are present in cart")
    public void allItemsShouldBePresentInCart() {
        // For this demo, we'll verify that the cart count matches expected total items
        // In a real implementation, you might verify specific items are present
        int totalAvailableItems = inventoryPage.getTotalAvailableItems();
        int cartCount = inventoryPage.getCartItemCount();
        Assertions.assertEquals(totalAvailableItems, cartCount, 
            String.format("Expected all %d items to be in cart but found %d", totalAvailableItems, cartCount));
    }
}
