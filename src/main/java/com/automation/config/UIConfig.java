package com.automation.config;

/**
 * Configuration class untuk UI Testing
 * Berisi base URLs, timeouts, dan konfigurasi browser
 */
public class UIConfig {
    
    // Base URLs untuk UI Testing
    public static final String BASE_URL = "https://www.saucedemo.com";
    public static final String DEMO_QA_URL = "https://demoqa.com";
    public static final String HEROKUAPP_URL = "https://the-internet.herokuapp.com";
    
    // Browser Configuration
    public static final String DEFAULT_BROWSER = "chrome";
    public static final boolean HEADLESS_MODE = false;
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 30;
    public static final int PAGE_LOAD_TIMEOUT = 60;
    
    // Test Data
    public static final String VALID_USERNAME = "standard_user";
    public static final String VALID_PASSWORD = "secret_sauce";
    public static final String LOCKED_USERNAME = "locked_out_user";
    public static final String INVALID_USERNAME = "invalid_user";
    public static final String INVALID_PASSWORD = "invalid_password";
    
    // Element Timeouts
    public static final int SHORT_WAIT = 5;
    public static final int MEDIUM_WAIT = 15;
    public static final int LONG_WAIT = 30;
    
    // Screen Resolution
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
}
