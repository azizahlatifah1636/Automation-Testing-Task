package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

/**
 * Inventory Page Object untuk SauceDemo
 * Represents the products/inventory page after login
 */
public class InventoryPage extends BasePage {
    
    // Page Elements
    @FindBy(className = "title")
    private WebElement pageTitle;
    
    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;
    
    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;
    
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;
    
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;
    
    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;
    
    @FindBy(className = "btn_inventory")
    private List<WebElement> addToCartButtons;
    
    // Constructors
    public InventoryPage() {
        super();
        PageFactory.initElements(driver, this);
    }
    
    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions
    public boolean isInventoryPageDisplayed() {
        return isElementDisplayed(pageTitle) && 
               getCurrentUrl().contains("inventory.html");
    }
    
    public boolean areProductsDisplayed() {
        return !inventoryItems.isEmpty() && inventoryItems.size() > 0;
    }
    
    public String getPageTitle() {
        return getText(pageTitle);
    }
    
    public int getNumberOfProducts() {
        return inventoryItems.size();
    }
    
    public List<String> getProductNames() {
        return productNames.stream()
                          .map(this::getText)
                          .collect(java.util.stream.Collectors.toList());
    }
    
    public List<String> getProductPrices() {
        return productPrices.stream()
                           .map(this::getText)
                           .collect(java.util.stream.Collectors.toList());
    }
    
    public void addFirstProductToCart() {
        if (!addToCartButtons.isEmpty()) {
            click(addToCartButtons.get(0));
        }
    }
    
    public void clickShoppingCart() {
        click(shoppingCartLink);
    }
    
    public boolean isShoppingCartDisplayed() {
        return isElementDisplayed(shoppingCartLink);
    }
    
    // New methods for cart management
    public void addItemToCart(String itemName) {
        // Find the item by name and click its "Add to cart" button
        String buttonId = "add-to-cart-" + itemName.toLowerCase()
                                                  .replace(" ", "-")
                                                  .replace("labs", "labs");
        WebElement addButton = driver.findElement(By.id(buttonId));
        click(addButton);
    }
    
    public void removeItemFromCart(String itemName) {
        // Find the item by name and click its "Remove" button
        String buttonId = "remove-" + itemName.toLowerCase()
                                            .replace(" ", "-")
                                            .replace("labs", "labs");
        WebElement removeButton = driver.findElement(By.id(buttonId));
        click(removeButton);
    }
    
    public void addAllItemsToCart() {
        // Click all "Add to cart" buttons
        List<WebElement> addButtons = driver.findElements(By.xpath("//button[contains(@id, 'add-to-cart')]"));
        for (WebElement button : addButtons) {
            if (button.isDisplayed() && button.isEnabled()) {
                click(button);
            }
        }
    }
    
    public int getCartItemCount() {
        try {
            if (isElementDisplayed(cartBadge)) {
                String countText = getText(cartBadge);
                return Integer.parseInt(countText);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
    
    public boolean isCartIconDisplayingCount() {
        return isElementDisplayed(cartBadge);
    }
    
    public void goToCart() {
        click(shoppingCartLink);
    }
    
    public void waitForPageLoad() {
        waitForElementToBeVisible(pageTitle);
    }
    
    public int getTotalAvailableItems() {
        return inventoryItems.size();
    }
}
