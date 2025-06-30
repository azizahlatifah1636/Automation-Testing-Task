# Automation Testing Framework

Framework otomasi testing lengkap untuk API dan UI menggunakan Java, JUnit 5, REST Assured, Selenium, Cucumber, dan Allure untuk reporting.

## 🚀 Fitur Framework

### API Testing
- **Test Framework**: JUnit 5 (Jupiter)
- **HTTP Client**: REST Assured
- **Reporting**: Allure Reports
- **Build Tool**: Gradle
- **Test Data**: Faker untuk generate data random
- **Logging**: SLF4J dengan Logback

### UI Testing
- **Web Automation**: Selenium WebDriver
- **BDD Framework**: Cucumber dengan Gherkin syntax
- **Page Object Model**: Struktur test yang maintainable
- **Cross-Browser Support**: Chrome, Firefox, Edge
- **Screenshot Capture**: Otomatis pada test failure
- **Headless Mode**: Support untuk CI/CD environments

## 📁 Struktur Project

```
src/
├── main/java/com/
│   ├── api/automation/          # API Automation Framework
│   │   ├── config/              # API endpoints dan constants
│   │   ├── models/              # POJO classes untuk API responses
│   │   └── utils/               # API utility classes
│   └── automation/              # UI Automation Framework
│       ├── config/              # UI configurations (browsers, timeouts)
│       ├── utils/               # Browser management utilities
│       └── pages/               # Page Object Model classes
└── test/
    ├── java/com/
    │   ├── api/automation/      # API Test Classes
    │   │   ├── tests/           # API test classes
    │   │   └── utils/           # Test data generators
    │   └── automation/          # UI Test Classes
    │       ├── runners/         # Cucumber test runners
    │       ├── stepdefinitions/ # Cucumber step definitions
    │       └── hooks/           # Test setup/teardown hooks
    └── resources/
        └── features/            # Gherkin feature files
```

## 🎯 Jenis Testing yang Diimplementasikan

### 1. Positive Testing
- Test dengan input yang valid
- Memverifikasi response yang benar
- Status code yang sesuai
- Data response yang akurat

### 2. Negative Testing
- Test dengan input yang tidak valid
- ID yang tidak ada
- Data yang kosong
- Format yang salah

### 3. Boundary Testing
- Test dengan nilai batas (boundary values)
- ID minimum dan maksimum
- String dengan panjang maksimum
- Nilai edge cases

## 🌐 Technology Stack Detail

### Selenium WebDriver
**Selenium** adalah tool otomasi web browser yang memungkinkan kita untuk:
- **Cross-Browser Testing**: Mendukung Chrome, Firefox, Edge, Safari
- **Element Interaction**: Click, type, drag-drop, dan interaksi lainnya
- **Browser Navigation**: Forward, back, refresh, window management
- **JavaScript Execution**: Menjalankan custom JavaScript di browser
- **Screenshot Capture**: Mengambil screenshot untuk debugging
- **Headless Mode**: Menjalankan tests tanpa membuka browser UI

**WebDriverManager** digunakan untuk:
- Otomatis download dan setup browser drivers
- Mengelola versi driver yang kompatibel
- Menghindari manual driver management

### Cucumber BDD Framework
**Cucumber** adalah tool untuk Behavior-Driven Development (BDD) yang:
- **Gherkin Syntax**: Menulis test scenarios dalam bahasa natural
- **Feature Files**: Menyimpan scenarios dalam format `.feature`
- **Step Definitions**: Implementasi Java untuk setiap Gherkin step
- **Data Tables**: Passing data kompleks ke test scenarios
- **Tags**: Mengelompokkan dan filtering test scenarios
- **Hooks**: Setup dan teardown untuk setiap scenario

**Contoh Gherkin Syntax:**
```gherkin
Feature: User Login
  Scenario: Successful login
    Given I am on the login page
    When I enter username "standard_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page
```

### Page Object Model (POM)
Framework ini mengimplementasikan **Page Object Model** untuk:
- **Separation of Concerns**: Memisahkan page elements dari test logic
- **Reusability**: Page objects dapat digunakan di multiple test scenarios
- **Maintainability**: Perubahan UI hanya perlu update di satu tempat
- **Readability**: Test code menjadi lebih clean dan readable

**Struktur POM:**
- `BasePage.java`: Common methods untuk semua pages
- `LoginPage.java`: Locators dan methods untuk login page
- `InventoryPage.java`: Locators dan methods untuk inventory page

## 🔧 Setup dan Instalasi

### Prerequisites
- Java 11 atau lebih tinggi
- Gradle 8.4
- Allure Command Line (untuk generate reports)

### Instalasi Allure
```bash
# Windows (menggunakan Scoop)
scoop install allure

# macOS (menggunakan Homebrew)
brew install allure

# Atau download dari https://github.com/allure-framework/allure2/releases
```

### Build Project
```bash
# Build project
./gradlew build

# Download dependencies
./gradlew dependencies
```

## 🏃‍♂️ Menjalankan Tests

### Menjalankan Semua Tests (API + UI)
```bash
./gradlew test
```

### Menjalankan Hanya API Tests
```bash
./gradlew test --tests "com.api.automation.tests.*"
```

### Menjalankan Hanya UI Tests (Cucumber)
```bash
./gradlew test --tests "CucumberTestRunner"
```

### Menjalankan Test Specific Class
```bash
# API Tests
./gradlew test --tests UserApiTest
./gradlew test --tests PostApiTest

# UI Tests by Feature
./gradlew test --tests CucumberTestRunner
```

### Menjalankan Test dengan Tag Tertentu
```bash
# Menjalankan hanya critical tests
./gradlew test -Dgroups="critical"

# Cucumber dengan tags
./gradlew test -Dcucumber.filter.tags="@smoke"
```

## 📊 Generate dan Lihat Reports

### Generate Allure Report
```bash
./gradlew allureReport
```

### Serve Allure Report (otomatis buka di browser)
```bash
./gradlew allureServe
```

Report akan tersedia di: `build/allure-report/index.html`

## 🧪 Test Cases yang Diimplementasikan

### API Tests

#### User API Tests (`UserApiTest.java`)
- ✅ Get All Users - Positive Test
- ✅ Get Single User - Positive Test
- ✅ Get Non-Existent User - Negative Test
- ✅ Get User with Invalid IDs - Boundary Test
- ✅ Create New User - Positive Test
- ✅ Create User with Invalid Data - Negative Test
- ✅ Update User - Positive Test
- ✅ Delete User - Positive Test

#### Post API Tests (`PostApiTest.java`)
- ✅ Get All Posts - Positive Test
- ✅ Get Single Post - Positive Test
- ✅ Get Non-Existent Post - Negative Test
- ✅ Get Post with Boundary Values - Boundary Test
- ✅ Create New Post - Positive Test
- ✅ Create Post with Empty Data - Negative Test
- ✅ Create Post with Invalid UserId - Negative Test
- ✅ Update Post - Positive Test
- ✅ Delete Post - Positive Test
- ✅ Get Posts by UserId - Positive Test

### UI Tests (Cucumber/BDD)

#### Login Functionality (`login.feature`)
- ✅ Successful login with valid credentials
- ✅ Login with invalid username
- ✅ Login with invalid password
- ✅ Login with locked out user
- ✅ Login with empty credentials
- ✅ Login with various invalid credential combinations (parameterized)

#### Shopping Cart Management (`cart.feature`)
- ✅ Add single item to cart
- ✅ Add multiple items to cart
- ✅ Remove item from cart
- ✅ Verify cart persistence across pages
- ✅ Add all available items to cart

## 🖥️ UI Testing Workflow dengan Cucumber

### 1. **Feature Files (Gherkin)**
Feature files ditulis dalam Gherkin syntax dan disimpan di `src/test/resources/features/`:

```gherkin
Feature: Shopping Cart Management
  Background:
    Given I am on the SauceDemo login page
    When I login with valid credentials
    Then I should be redirected to the inventory page

  @smoke @positive
  Scenario: Add single item to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart should show 1 item
```

### 2. **Step Definitions**
Step definitions mengimplementasikan setiap Gherkin step dalam Java:

```java
@When("I add {string} to the cart")
public void i_add_item_to_cart(String itemName) {
    inventoryPage.addItemToCart(itemName);
}

@Then("the cart should show {int} item(s)")
public void the_cart_should_show_items(int expectedCount) {
    int actualCount = inventoryPage.getCartItemCount();
    assertEquals(expectedCount, actualCount);
}
```

### 3. **Page Object Model**
Page objects encapsulate page elements dan actions:

```java
public class InventoryPage extends BasePage {
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;
    
    public void addItemToCart(String itemName) {
        String buttonId = "add-to-cart-" + itemName.toLowerCase().replace(" ", "-");
        WebElement addButton = driver.findElement(By.id(buttonId));
        click(addButton);
    }
}
```

### 4. **Test Execution Flow**
1. **Setup**: Hooks initialize WebDriver dan browser
2. **Feature Execution**: Cucumber reads feature files
3. **Step Mapping**: Cucumber maps Gherkin steps ke step definitions
4. **Page Interaction**: Step definitions call page object methods
5. **Assertion**: Verify expected outcomes
6. **Teardown**: Browser cleanup dan screenshot on failure

### 5. **Reporting & Debugging**
- **Allure Reports**: Comprehensive test reports dengan screenshots
- **Console Logs**: Real-time execution feedback
- **Screenshot on Failure**: Otomatis capture pada test failure
- **Browser Console Logs**: Capture JavaScript errors

## 📚 Sebagai QA Engineer, Anda Harus:

### 1. **Memahami Framework Architecture**
- Pelajari struktur project dan bagaimana components berinteraksi
- Pahami separation of concerns (config, models, utils, tests)
- Mengerti bagaimana REST Assured bekerja

### 2. **Menulis Test Cases yang Efektif**
- Gunakan naming convention yang jelas dan deskriptif
- Implement positive, negative, dan boundary testing
- Tambahkan assertions yang comprehensive
- Gunakan parameterized tests untuk efisiensi

### 3. **Best Practices Testing**
- Setup data test yang konsisten
- Cleanup setelah testing
- Handle flaky tests
- Implement proper error handling

### 4. **Reporting dan Documentation**
- Gunakan Allure annotations untuk better reporting
- Dokumentasikan test scenarios dengan jelas
- Include screenshots dan logs untuk debugging
- Maintain test data dan expected results

### 5. **Continuous Improvement**
- Review dan refactor test code secara berkala
- Update framework sesuai kebutuhan project
- Monitor test execution time dan optimize
- Implement parallel test execution

## 🔍 Target Testing Applications

### API Testing Target
Framework ini menggunakan JSONPlaceholder API (<https://jsonplaceholder.typicode.com>) sebagai target testing:

- **Users**: `/users`
- **Posts**: `/posts`
- **Comments**: `/comments`

### UI Testing Target
Framework UI menggunakan SauceDemo (<https://www.saucedemo.com>) sebagai target testing:

- **Login Page**: Authentication testing dengan berbagai skenario
- **Inventory Page**: Product listing dan shopping cart functionality
- **Cart Management**: Add/remove items, persistence testing

## 🚀 Next Steps untuk QA Engineer

1. **Eksplorasi Framework**: Jalankan tests dan lihat hasil Allure reports
2. **Modifikasi Tests**: Coba tambah test cases baru atau modifikasi existing
3. **Integrasikan dengan CI/CD**: Setup framework untuk berjalan di pipeline
4. **Extend Framework**: Tambah support untuk authentication, database validation, dll
5. **Performance Testing**: Integrasikan dengan tools seperti JMeter atau Gatling
6. **Cross-Browser Testing**: Extend untuk testing di berbagai browser dan mobile
7. **API + UI Integration**: Combine API and UI tests untuk end-to-end scenarios

## 📝 Tips untuk Pembelajaran

### API Testing
- Mulai dengan menjalankan existing API tests
- Pahami bagaimana REST Assured assertions bekerja
- Coba buat test case baru dengan scenario berbeda
- Eksperimen dengan different HTTP methods dan parameters
- Pelajari cara debug ketika API tests fail

### UI Testing
- Jalankan Cucumber tests dan lihat browser automation
- Pelajari Gherkin syntax untuk BDD scenarios
- Pahami Page Object Model untuk maintainable UI tests
- Eksperimen dengan different browser configurations
- Practice writing clear step definitions

### General Testing
- Practice writing clear dan maintainable test code
- Understand test reporting dengan Allure
- Learn debugging techniques untuk failed tests
- Explore parallel test execution untuk performance

Happy Testing! 🎉

---

## 📚 **GITHUB REPOSITORY SUBMISSION**

### 🎯 **Tujuan Proyek**
Repositori ini berisi **Complete Automation Testing Framework** yang mengimplementasikan:

1. **UI Web Automation** menggunakan Cucumber, Selenium, Java, dan Gradle
2. **API Automation** menggunakan REST Assured, JUnit 5, dan Allure
3. **Page Object Model (POM)** untuk maintainable UI tests
4. **Behavior-Driven Development (BDD)** dengan Gherkin syntax
5. **Comprehensive Reporting** dengan Allure Reports

### 🏗️ **Implementasi yang Disertakan**

#### ✅ **Framework Components**
- **Selenium WebDriver**: Cross-browser automation (Chrome, Firefox, Edge)
- **Cucumber BDD**: Feature files dengan Gherkin syntax
- **Page Object Model**: BasePage, LoginPage, InventoryPage
- **Test Runners**: CucumberTestRunner untuk eksekusi tests
- **Hooks & Utilities**: Setup/teardown, screenshot capture, browser management

#### ✅ **Test Cases Sampel**
- **Login Functionality**: 9 scenarios (positive, negative, boundary)
- **Shopping Cart Management**: 5 scenarios (add, remove, persistence)
- **API Testing**: 33 test cases untuk Users dan Posts endpoints
- **Parameterized Tests**: Data-driven testing examples

#### ✅ **Documentation**
- **Complete README**: Setup, configuration, dan running instructions
- **Quick Start Guide**: Step-by-step untuk QA Engineers
- **Technical Documentation**: Architecture dan best practices
- **PDF Guides**: Comprehensive learning materials

### 🚀 **Quick Start untuk Reviewer**

1. **Clone Repository**:
   ```bash
   git clone https://github.com/USERNAME/automation-testing-framework.git
   cd automation-testing-framework
   ```

2. **Run All Tests**:
   ```bash
   ./gradlew test
   ```

3. **Generate Allure Report**:
   ```bash
   ./gradlew allureServe
   ```

4. **Run Specific Test Types**:
   ```bash
   # UI Tests only
   ./gradlew test --tests "CucumberTestRunner"
   
   # API Tests only  
   ./gradlew test --tests "com.api.automation.tests.*"
   ```

### 📊 **Expected Results**
- **Total Tests**: 47 (33 API + 14 UI scenarios)
- **Success Rate**: ~87% (beberapa intentional failures untuk learning)
- **UI Tests**: 100% pass dengan browser automation
- **Reports**: Comprehensive Allure reports dengan screenshots

### 🎓 **Learning Value**
Framework ini mendemonstrasikan:
- Professional automation testing practices
- BDD implementation dengan real scenarios
- Cross-browser testing capabilities
- API dan UI testing integration
- Comprehensive reporting dan debugging

### 📝 **Repository Structure**
```
automation-testing-framework/
├── src/main/java/com/
│   ├── api/automation/          # API Framework
│   └── automation/              # UI Framework (POM)
├── src/test/
│   ├── java/com/automation/     # Test Classes & Step Definitions
│   └── resources/features/      # Gherkin Feature Files
├── README.md                    # This comprehensive guide
├── build.gradle                 # Gradle configuration
└── Documentation/               # Additional guides
```

**Repository URL**: `https://github.com/USERNAME/automation-testing-framework`

---
