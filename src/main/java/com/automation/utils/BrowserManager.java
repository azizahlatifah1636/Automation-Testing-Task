package com.automation.utils;

import com.automation.config.UIConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.Dimension;
import java.time.Duration;

/**
 * Browser Manager untuk mengelola browser instances
 * Menggunakan WebDriverManager untuk auto-download drivers
 */
public class BrowserManager {
    
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    public static void initializeDriver(String browserName) {
        WebDriver driver = createDriver(browserName.toLowerCase());
        configureDriver(driver);
        driverThreadLocal.set(driver);
    }
    
    private static WebDriver createDriver(String browserName) {
        WebDriver driver;
        
        switch (browserName) {
            case "chrome":
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (UIConfig.HEADLESS_MODE) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=" + UIConfig.WINDOW_WIDTH + "," + UIConfig.WINDOW_HEIGHT);
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (UIConfig.HEADLESS_MODE) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (UIConfig.HEADLESS_MODE) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        return driver;
    }
    
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(UIConfig.IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(UIConfig.PAGE_LOAD_TIMEOUT));
        driver.manage().window().setSize(new Dimension(UIConfig.WINDOW_WIDTH, UIConfig.WINDOW_HEIGHT));
    }
    
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
