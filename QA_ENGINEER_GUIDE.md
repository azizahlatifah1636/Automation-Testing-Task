# PANDUAN LENGKAP QA ENGINEER: API AUTOMATION FRAMEWORK

**Versi:** 1.0  
**Tanggal:** Juni 2025  
**Framework:** Java + JUnit 5 + REST Assured + Allure  

---

## DAFTAR ISI

1. [Pendahuluan](#pendahuluan)
2. [Struktur Framework](#struktur-framework)
3. [Jenis Testing yang Harus Dikuasai](#jenis-testing)
4. [Skills QA Engineer](#skills-qa-engineer)
5. [Commands dan Tools](#commands-tools)
6. [Workflow Harian QA Engineer](#workflow-harian)
7. [Learning Path](#learning-path)
8. [Best Practices](#best-practices)
9. [Troubleshooting](#troubleshooting)
10. [Appendix](#appendix)

---

## 1. PENDAHULUAN

### Apa itu API Automation Framework?

API Automation Framework adalah kumpulan tools, libraries, dan best practices yang digunakan untuk melakukan testing otomatis pada Application Programming Interface (API). Framework ini memungkinkan QA Engineer untuk:

- **Memvalidasi functionality** API endpoints
- **Memverifikasi data integrity** request dan response
- **Mengotomatisasi regression testing**
- **Generate comprehensive reports**
- **Mendeteksi issues** lebih cepat dan akurat

### Teknologi yang Digunakan

| Komponen | Teknologi | Fungsi |
|----------|-----------|---------|
| **Programming Language** | Java 11+ | Base language untuk framework |
| **Test Framework** | JUnit 5 (Jupiter) | Test runner dan assertions |
| **HTTP Client** | REST Assured | API calls dan validations |
| **Build Tool** | Gradle | Dependency management dan build |
| **Reporting** | Allure | Visual test reports |
| **Test Data** | Faker | Random data generation |
| **JSON Processing** | Jackson | JSON serialization/deserialization |

### Target APIs untuk Testing

Framework ini dikonfigurasi untuk testing dengan:

1. **JSONPlaceholder** (`https://jsonplaceholder.typicode.com`)
   - Public API untuk learning dan testing
   - Tidak memerlukan authentication
   - Support CRUD operations

2. **ReqRes** (`https://reqres.in/api`)
   - API dengan authentication simulation
   - User management endpoints
   - Error handling examples

3. **HTTPBin** (`https://httpbin.org`)
   - HTTP testing service
   - Various HTTP methods testing
   - Request/response inspection

---

## 2. STRUKTUR FRAMEWORK

### Directory Structure

```
src/
├── main/java/com/api/automation/
│   ├── config/
│   │   └── ApiConfig.java                 ← URLs, endpoints, constants
│   ├── models/
│   │   ├── User.java                      ← User POJO class
│   │   └── Post.java                      ← Post POJO class
│   └── utils/
│       └── BaseApiUtils.java              ← REST Assured utilities
└── test/java/com/api/automation/
    ├── tests/
    │   ├── UserApiTest.java               ← User CRUD testing
    │   ├── PostApiTest.java               ← Post CRUD testing
    │   ├── ReqResApiTest.java             ← Advanced examples
    │   └── QaEngineerDebuggingExample.java ← Debugging skills
    └── utils/
        └── TestDataGenerator.java         ← Random data generation

build/
├── allure-results/                        ← Test execution data
├── reports/
│   ├── tests/                            ← JUnit HTML reports
│   └── allure-report/                    ← Allure HTML reports
└── test-results/                         ← Test execution results
```

### File Descriptions

#### **ApiConfig.java**
```java
public class ApiConfig {
    // Base URLs untuk different environments
    public static final String JSONPLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String REQRES_BASE_URL = "https://reqres.in/api";
    
    // Endpoints
    public static final String USERS_ENDPOINT = "/users";
    public static final String POSTS_ENDPOINT = "/posts";
    
    // HTTP Status Codes
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NOT_FOUND = 404;
}
```

#### **BaseApiUtils.java**
```java
public class BaseApiUtils {
    // Setup REST Assured configuration
    public static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .header("Content-Type", "application/json");
    }
}
```

#### **Model Classes**
```java
public class User {
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("email")
    private String email;
    
    // Constructors, getters, setters
}
```

---

## 3. JENIS TESTING YANG HARUS DIKUASAI

### A. Positive Testing ✅

**Tujuan:** Memverifikasi bahwa API berfungsi dengan benar ketika menerima input yang valid.

**Contoh Implementasi:**
```java
@Test
@DisplayName("Test Get All Users - Should Return Valid Response")
void testGetAllUsers_PositiveTest() {
    Response response = BaseApiUtils.getRequestSpec()
            .when()
            .get(ApiConfig.USERS_ENDPOINT)
            .then()
            .statusCode(ApiConfig.OK)                    // Verifikasi status code
            .body("size()", greaterThan(0))              // Verifikasi ada data
            .body("[0].id", notNullValue())              // Verifikasi structure
            .body("[0].name", notNullValue())
            .body("[0].email", notNullValue())
            .extract().response();
    
    // Additional validations
    User[] users = response.as(User[].class);
    assertTrue(users.length > 0, "Users array should not be empty");
    assertNotNull(users[0].getId(), "First user should have an ID");
    assertTrue(users[0].getEmail().contains("@"), "Email should be valid format");
}
```

**Yang Harus Divalidasi:**
- ✅ Status code (200, 201, etc.)
- ✅ Response structure sesuai dengan schema
- ✅ Data types untuk setiap field
- ✅ Required fields tidak null/empty
- ✅ Data format (email, date, etc.)
- ✅ Response time dalam batas yang wajar

### B. Negative Testing ❌

**Tujuan:** Memverifikasi bahwa API menangani input yang tidak valid dengan benar.

**Contoh Implementasi:**
```java
@Test
@DisplayName("Test Get Non-Existent User - Should Return 404")
void testGetNonExistentUser_NegativeTest() {
    int nonExistentUserId = 999;
    
    BaseApiUtils.getRequestSpec()
            .pathParam("id", nonExistentUserId)
            .when()
            .get(ApiConfig.USERS_ENDPOINT + "/{id}")
            .then()
            .statusCode(ApiConfig.NOT_FOUND);           // Verifikasi error handling
}

@Test
@DisplayName("Test Create User with Invalid Data - Should Return 400")
void testCreateUserWithInvalidData_NegativeTest() {
    String invalidJson = "{ invalid json data }";
    
    Response response = BaseApiUtils.getRequestSpec()
            .body(invalidJson)
            .when()
            .post(ApiConfig.USERS_ENDPOINT);
    
    // Verifikasi error response
    assertTrue(response.getStatusCode() == 400 || 
              response.getStatusCode() == 422, 
              "Should return client error for invalid data");
}
```

**Skenario Negative Testing:**
- ❌ Non-existent resource IDs
- ❌ Invalid data types
- ❌ Missing required fields
- ❌ Malformed JSON
- ❌ Invalid authentication
- ❌ Exceeded rate limits
- ❌ SQL injection attempts
- ❌ XSS payload dalam input

### C. Boundary Testing 🔄

**Tujuan:** Memvalidasi behavior API pada nilai-nilai batas (edge cases).

**Contoh Implementasi:**
```java
@ParameterizedTest
@ValueSource(ints = {-1, 0, 1, 100, 101, 999, 1000})
@DisplayName("Test User ID Boundary Values")
void testUserIdBoundaryValues(int userId) {
    Response response = BaseApiUtils.getRequestSpec()
            .pathParam("id", userId)
            .when()
            .get(ApiConfig.USERS_ENDPOINT + "/{id}");
    
    if (userId >= 1 && userId <= 100) {
        // Valid range - should return 200
        response.then().statusCode(200);
        System.out.println("✅ Valid User ID: " + userId);
    } else {
        // Invalid range - should return 404
        response.then().statusCode(404);
        System.out.println("❌ Invalid User ID: " + userId);
    }
}
```

**Boundary Values untuk Testing:**
- 🔄 Minimum dan maximum values
- 🔄 Zero dan negative numbers
- 🔄 Empty strings dan null values
- 🔄 Maximum string length
- 🔄 Unicode characters
- 🔄 Future dan past dates
- 🔄 Large numbers (overflow testing)

### D. Performance Testing ⚡

**Contoh Implementasi:**
```java
@Test
@DisplayName("Test API Response Time - Should Respond Within 2 Seconds")
void testApiResponseTime() {
    long startTime = System.currentTimeMillis();
    
    BaseApiUtils.getRequestSpecWithoutLogging()
            .when()
            .get(ApiConfig.USERS_ENDPOINT)
            .then()
            .statusCode(200)
            .time(lessThan(2000L));                     // Verifikasi response time
    
    long endTime = System.currentTimeMillis();
    long responseTime = endTime - startTime;
    
    assertTrue(responseTime < 2000, 
              "Response time should be less than 2 seconds, actual: " + responseTime + "ms");
}
```

---

## 4. SKILLS QA ENGINEER

### A. Technical Skills

#### **1. API Testing Fundamentals**

**HTTP Methods Understanding:**
```java
// GET - Retrieve data
@Test
void testGetMethod() {
    given().when().get("/users").then().statusCode(200);
}

// POST - Create new resource
@Test
void testPostMethod() {
    given().body(userData).when().post("/users").then().statusCode(201);
}

// PUT - Update existing resource
@Test
void testPutMethod() {
    given().body(updatedData).when().put("/users/1").then().statusCode(200);
}

// DELETE - Remove resource
@Test
void testDeleteMethod() {
    given().when().delete("/users/1").then().statusCode(200);
}
```

**Status Code Knowledge:**
- **2xx Success:** 200 (OK), 201 (Created), 204 (No Content)
- **4xx Client Error:** 400 (Bad Request), 401 (Unauthorized), 404 (Not Found)
- **5xx Server Error:** 500 (Internal Server Error), 502 (Bad Gateway)

#### **2. JSON Processing Skills**

**JSON Path Assertions:**
```java
@Test
void testJsonPathAssertions() {
    given()
        .when().get("/users/1")
        .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("name", notNullValue())
        .body("address.city", equalTo("Gwenborough"))
        .body("company.name", containsString("Romaguera"));
}
```

**JSON Schema Validation:**
```java
@Test
void testJsonSchemaValidation() {
    given()
        .when().get("/users")
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/users-schema.json"));
}
```

#### **3. Test Data Management**

**Using TestDataGenerator:**
```java
@Test
void testWithRandomData() {
    User randomUser = TestDataGenerator.generateRandomUser();
    
    Response response = given()
            .body(randomUser)
            .when()
            .post("/users")
            .then()
            .statusCode(201)
            .extract().response();
    
    User createdUser = response.as(User.class);
    assertEquals(randomUser.getName(), createdUser.getName());
}
```

### B. Debugging dan Investigation Skills

#### **1. Response Analysis**

```java
@Test
void investigateApiResponse() {
    Response response = given().when().get("/users").extract().response();
    
    // Analyze status code
    System.out.println("Status Code: " + response.getStatusCode());
    
    // Analyze headers
    response.getHeaders().forEach(header -> 
        System.out.println(header.getName() + ": " + header.getValue()));
    
    // Analyze response body
    System.out.println("Response Body: " + response.getBody().asString());
    
    // Analyze response time
    System.out.println("Response Time: " + response.getTime() + "ms");
}
```

#### **2. Error Diagnosis**

**Common Issues dan Solutions:**
```java
@Test
void diagnoseCommonIssues() {
    Response response = given().when().get("/api/users");
    
    switch(response.getStatusCode()) {
        case 401:
            System.out.println("🚨 Authentication required - check API credentials");
            break;
        case 403:
            System.out.println("🚨 Permission denied - check user roles");
            break;
        case 404:
            System.out.println("🚨 Endpoint not found - check URL and endpoint");
            break;
        case 500:
            System.out.println("🚨 Server error - coordinate with dev team");
            break;
        case 429:
            System.out.println("🚨 Rate limit exceeded - implement retry logic");
            break;
        default:
            System.out.println("✅ Successful response or needs investigation");
    }
}
```

#### **3. Log Analysis**

**Request/Response Logging:**
```java
@Test
void testWithDetailedLogging() {
    given()
        .filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter())
        .when()
        .get("/users")
        .then()
        .statusCode(200);
}
```

### C. Reporting dan Documentation Skills

#### **1. Allure Annotations Usage**

```java
@Epic("User Management API")
@Feature("User CRUD Operations")
@Story("Get User Details")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Test Get Single User - Should Return Valid User Data")
@Description("This test verifies that API returns valid user data for existing user ID")
@Test
void testGetSingleUser() {
    // Test implementation
}
```

#### **2. Test Documentation**

**Best Practices:**
- **Clear test names:** `testGetAllUsers_PositiveTest()`
- **Meaningful descriptions:** Explain what is being tested
- **Step-by-step comments:** Document test flow
- **Expected vs Actual:** Document expected behavior
- **Prerequisites:** Document test data requirements

---

## 5. COMMANDS DAN TOOLS

### A. Gradle Commands

#### **Build dan Dependency Management**
```bash
# Build project dan download dependencies
./gradlew build

# Clean build artifacts
./gradlew clean

# Check dependencies
./gradlew dependencies

# Update Gradle wrapper
./gradlew wrapper --gradle-version 8.4
```

#### **Test Execution**
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests UserApiTest

# Run tests with specific pattern
./gradlew test --tests "*ApiTest"

# Run tests and continue on failure
./gradlew test --continue

# Run tests with verbose output
./gradlew test --info

# Run tests in parallel
./gradlew test --parallel
```

#### **Test Filtering**
```bash
# Run tests by package
./gradlew test --tests "com.api.automation.tests.*"

# Run single test method
./gradlew test --tests "UserApiTest.testGetAllUsers_PositiveTest"

# Run tests by tag (if using @Tag annotation)
./gradlew test --tests "*" --include-tag "smoke"
```

### B. Allure Reporting Commands

```bash
# Generate static Allure report
./gradlew allureReport

# Serve Allure report (opens browser automatically)
./gradlew allureServe

# Clean previous Allure results
./gradlew cleanAllureResults

# Generate and open report in one command
./gradlew test allureReport && start build/reports/allure-report/allureReport/index.html
```

### C. VS Code Integration

#### **Tasks Configuration (tasks.json)**
```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "Run All Tests",
            "type": "shell",
            "command": "./gradlew",
            "args": ["test"],
            "group": "test"
        },
        {
            "label": "Generate Allure Report",
            "type": "shell",
            "command": "./gradlew",
            "args": ["allureReport"],
            "group": "test"
        }
    ]
}
```

**Keyboard Shortcuts:**
- `Ctrl+Shift+P` → "Tasks: Run Task" → Select task
- `Ctrl+Shift+\`` → Open terminal
- `F5` → Run/Debug current test file

---

## 6. WORKFLOW HARIAN QA ENGINEER

### A. Daily Testing Routine

#### **Morning Setup (15 minutes)**
```bash
# 1. Pull latest code changes
git pull origin main

# 2. Build project dan verify dependencies
./gradlew build

# 3. Run smoke tests untuk verify environment
./gradlew test --tests "*SmokeTest"

# 4. Check previous test results
open build/reports/allure-report/allureReport/index.html
```

#### **Test Development (2-3 hours)**
1. **Analyze requirements** dan API documentation
2. **Design test scenarios** (positive, negative, boundary)
3. **Write test cases** dengan proper annotations
4. **Implement test data** generation
5. **Add assertions** dan validations

#### **Test Execution (30 minutes)**
```bash
# Run newly created tests
./gradlew test --tests "NewTestClass"

# Run regression suite
./gradlew test

# Generate dan review reports
./gradlew allureReport
```

#### **Bug Investigation (1-2 hours)**
1. **Analyze failed tests** dari report
2. **Debug root cause** dengan detailed logging
3. **Document findings** dengan evidence
4. **Coordinate dengan dev team** untuk fixes
5. **Create bug reports** dengan reproduction steps

#### **End of Day (15 minutes)**
```bash
# Run full regression suite
./gradlew test

# Generate comprehensive report
./gradlew allureReport

# Commit test code changes
git add .
git commit -m "feat: add API tests for user management"
git push origin feature/api-tests
```

### B. Weekly Activities

#### **Monday: Planning**
- Review test coverage dan gaps
- Plan new test scenarios untuk sprint
- Update test documentation
- Review API changes dari dev team

#### **Tuesday-Thursday: Development**
- Implement new test cases
- Enhance existing test coverage
- Refactor test code untuk maintainability
- Update test data dan fixtures

#### **Friday: Review dan Cleanup**
- Code review untuk test automation code
- Cleanup test data dan temporary files
- Update test documentation
- Prepare weekly test report

### C. Sprint Activities

#### **Sprint Planning**
- **Estimate testing effort** untuk new features
- **Identify test scenarios** yang perlu automation
- **Plan test data requirements**
- **Coordinate dengan developers** untuk testability

#### **Sprint Execution**
- **Daily test runs** dan result monitoring
- **Continuous integration** dengan development
- **Bug triage dan prioritization**
- **Test case maintenance** dan updates

#### **Sprint Review**
- **Demonstrate test coverage** kepada stakeholders
- **Present test metrics** dan quality insights
- **Discuss test automation ROI**
- **Gather feedback** untuk improvements

#### **Sprint Retrospective**
- **Review testing process** dan identify improvements
- **Discuss tool enhancements** atau upgrades
- **Plan technical debt** resolution
- **Document lessons learned**

---

## 7. LEARNING PATH

### Phase 1: Foundation (Week 1-2)

#### **Week 1: Understanding Framework**
**Day 1-2: Environment Setup**
- [ ] Install Java 11+ dan configure JAVA_HOME
- [ ] Install VS Code dengan Java extensions
- [ ] Clone dan build framework project
- [ ] Run existing tests dan understand output

**Day 3-4: Framework Exploration**
- [ ] Study project structure dan file organization
- [ ] Understand ApiConfig.java dan endpoint configuration
- [ ] Explore model classes (User.java, Post.java)
- [ ] Learn BaseApiUtils.java utilities

**Day 5: First Test Execution**
- [ ] Run `./gradlew test` dan analyze results
- [ ] Generate Allure report dengan `./gradlew allureReport`
- [ ] Understand test failure analysis
- [ ] Practice debugging dengan logging

#### **Week 2: Basic Test Writing**
**Day 1-2: Simple Modifications**
- [ ] Modify existing test assertions
- [ ] Change test data dan observe impact
- [ ] Add new simple test methods
- [ ] Practice dengan different HTTP methods

**Day 3-4: Test Data Management**
- [ ] Learn TestDataGenerator usage
- [ ] Create custom test data scenarios
- [ ] Understand JSON serialization/deserialization
- [ ] Practice dengan dynamic data generation

**Day 5: Allure Reporting**
- [ ] Add Allure annotations (@Epic, @Feature, @Story)
- [ ] Customize test descriptions dan display names
- [ ] Generate dan interpret comprehensive reports
- [ ] Share reports dengan team members

### Phase 2: Intermediate Skills (Week 3-4)

#### **Week 3: Advanced Test Scenarios**
**Day 1-2: Negative Testing**
- [ ] Write tests untuk error scenarios
- [ ] Implement boundary value testing
- [ ] Test dengan invalid input data
- [ ] Handle expected exceptions

**Day 3-4: API Authentication**
- [ ] Learn basic authentication testing
- [ ] Implement token-based authentication
- [ ] Test authorization scenarios
- [ ] Handle session management

**Day 5: Performance Testing**
- [ ] Add response time validations
- [ ] Test dengan large datasets
- [ ] Implement parallel test execution
- [ ] Monitor resource utilization

#### **Week 4: Integration dan CI/CD**
**Day 1-2: Environment Management**
- [ ] Configure multiple environments (dev, staging, prod)
- [ ] Learn environment-specific configuration
- [ ] Implement dynamic base URL handling
- [ ] Practice environment switching

**Day 3-4: CI/CD Integration**
- [ ] Configure GitHub Actions atau Jenkins
- [ ] Implement automated test execution
- [ ] Set up test result notifications
- [ ] Configure scheduled test runs

**Day 5: Code Quality**
- [ ] Implement code review practices
- [ ] Add static code analysis
- [ ] Improve test maintainability
- [ ] Document test procedures

### Phase 3: Advanced Topics (Month 2)

#### **Advanced Framework Features**
- [ ] Custom assertions dan matchers
- [ ] Test data factories dan builders
- [ ] Advanced Allure features (attachments, steps)
- [ ] Performance monitoring integration

#### **Security Testing**
- [ ] SQL injection testing
- [ ] XSS vulnerability testing
- [ ] Authentication bypass testing
- [ ] Data validation security

#### **API Contract Testing**
- [ ] JSON schema validation
- [ ] Contract testing dengan Pact
- [ ] API versioning testing
- [ ] Backward compatibility testing

#### **Advanced Reporting**
- [ ] Custom Allure plugins
- [ ] Integration dengan test management tools
- [ ] Automated test metrics collection
- [ ] Dashboard creation

---

## 8. BEST PRACTICES

### A. Test Design Principles

#### **1. Test Independence**
```java
// ✅ Good: Each test is independent
@Test
void testGetUser() {
    // Creates its own test data
    User testUser = TestDataGenerator.generateRandomUser();
    // Test implementation
}

@Test
void testDeleteUser() {
    // Creates its own test data, doesn't depend on previous test
    User testUser = createTestUser();
    // Test implementation
}

// ❌ Bad: Tests depend on each other
@Test
void testCreateUser() {
    // Creates user with ID 1
}

@Test
void testDeleteUser() {
    // Assumes user with ID 1 exists from previous test
}
```

#### **2. Descriptive Test Names**
```java
// ✅ Good: Clear, descriptive names
@Test
void testGetAllUsers_WhenValidRequest_ShouldReturnUserList() {}

@Test
void testCreateUser_WhenMissingRequiredFields_ShouldReturn400() {}

@Test
void testUpdateUser_WhenUserNotExists_ShouldReturn404() {}

// ❌ Bad: Generic, unclear names
@Test
void testUser1() {}

@Test
void testAPI() {}

@Test
void test() {}
```

#### **3. Comprehensive Assertions**
```java
// ✅ Good: Multiple specific assertions
@Test
void testCreateUser_ShouldReturnValidUserData() {
    User newUser = new User("John Doe", "john@example.com");
    
    Response response = given()
            .body(newUser)
            .when()
            .post("/users")
            .then()
            .statusCode(201)                          // Status validation
            .body("id", notNullValue())               // ID generation
            .body("name", equalTo("John Doe"))        // Data accuracy
            .body("email", equalTo("john@example.com")) // Email validation
            .body("createdAt", notNullValue())        // Timestamp
            .extract().response();
    
    // Additional object-level validations
    User createdUser = response.as(User.class);
    assertNotNull(createdUser.getId());
    assertTrue(createdUser.getId() > 0);
    assertEquals("John Doe", createdUser.getName());
}

// ❌ Bad: Minimal assertions
@Test
void testCreateUser() {
    given().body(user).when().post("/users").then().statusCode(201);
}
```

### B. Code Organization

#### **1. Page Object Pattern untuk APIs**
```java
// API Page Object
public class UserApiClient {
    private static final String USERS_ENDPOINT = "/users";
    
    public Response getAllUsers() {
        return BaseApiUtils.getRequestSpec()
                .when()
                .get(USERS_ENDPOINT);
    }
    
    public Response getUserById(int userId) {
        return BaseApiUtils.getRequestSpec()
                .pathParam("id", userId)
                .when()
                .get(USERS_ENDPOINT + "/{id}");
    }
    
    public Response createUser(User user) {
        return BaseApiUtils.getRequestSpec()
                .body(user)
                .when()
                .post(USERS_ENDPOINT);
    }
}

// Test class using API client
public class UserApiTest {
    private UserApiClient userApi = new UserApiClient();
    
    @Test
    void testGetAllUsers() {
        Response response = userApi.getAllUsers();
        response.then().statusCode(200);
    }
}
```

#### **2. Test Data Management**
```java
// Test data factory
public class UserTestData {
    public static User validUser() {
        return new User("John Doe", "john@example.com");
    }
    
    public static User userWithInvalidEmail() {
        return new User("Jane Doe", "invalid-email");
    }
    
    public static User userWithMissingName() {
        return new User(null, "jane@example.com");
    }
}

// Usage in tests
@Test
void testCreateValidUser() {
    User user = UserTestData.validUser();
    // Test implementation
}
```

### C. Error Handling

#### **1. Graceful Failure Handling**
```java
@Test
void testApiWithRetry() {
    int maxRetries = 3;
    Response response = null;
    
    for (int i = 0; i < maxRetries; i++) {
        try {
            response = given().when().get("/users");
            if (response.getStatusCode() == 200) {
                break;
            }
        } catch (Exception e) {
            if (i == maxRetries - 1) {
                fail("API failed after " + maxRetries + " attempts: " + e.getMessage());
            }
            // Wait before retry
            try { Thread.sleep(1000); } catch (InterruptedException ie) {}
        }
    }
    
    assertNotNull(response);
    response.then().statusCode(200);
}
```

#### **2. Detailed Error Reporting**
```java
@Test
void testWithDetailedErrorInfo() {
    Response response = given().when().get("/users/999");
    
    if (response.getStatusCode() != 404) {
        String errorDetails = String.format(
            "Expected 404 but got %d. Response body: %s. Headers: %s",
            response.getStatusCode(),
            response.getBody().asString(),
            response.getHeaders().toString()
        );
        fail(errorDetails);
    }
}
```

### D. Performance Considerations

#### **1. Test Execution Optimization**
```java
// Use RequestSpecification caching
public class BaseApiUtils {
    private static RequestSpecification cachedSpec;
    
    public static RequestSpecification getRequestSpec() {
        if (cachedSpec == null) {
            cachedSpec = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");
        }
        return cachedSpec;
    }
}

// Parallel test execution
@Execution(ExecutionMode.CONCURRENT)
public class ParallelApiTest {
    @Test
    void testMethod1() { /* ... */ }
    
    @Test
    void testMethod2() { /* ... */ }
}
```

#### **2. Resource Management**
```java
@TestMethodOrder(OrderAnnotation.class)
public class ResourceManagementTest {
    
    @BeforeAll
    static void setupTestData() {
        // Create necessary test data once
    }
    
    @AfterAll
    static void cleanupTestData() {
        // Cleanup test data after all tests
    }
    
    @AfterEach
    void resetState() {
        // Reset any modified state
    }
}
```

---

## 9. TROUBLESHOOTING

### A. Common Issues dan Solutions

#### **1. Build Issues**

**Problem:** Gradle build fails dengan dependency errors
```
Could not resolve all files for configuration ':testRuntimeClasspath'
```

**Solution:**
```bash
# Clear Gradle cache
./gradlew clean
rm -rf ~/.gradle/caches/

# Re-download dependencies
./gradlew build --refresh-dependencies

# Check network connectivity
curl -I https://repo.maven.apache.org/maven2/
```

**Problem:** Java version compatibility
```
Unsupported major.minor version
```

**Solution:**
```bash
# Check Java version
java -version

# Set correct JAVA_HOME
export JAVA_HOME=/path/to/java11

# Update gradle.properties
echo "org.gradle.java.home=/path/to/java11" >> gradle.properties
```

#### **2. Test Execution Issues**

**Problem:** Tests failing dengan connection timeout
```
java.net.SocketTimeoutException: Read timed out
```

**Solution:**
```java
// Increase timeout in BaseApiUtils
RestAssured.config = RestAssuredConfig.config()
        .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 30000)
                .setParam("http.socket.timeout", 60000));
```

**Problem:** Authentication errors (401 Unauthorized)
```
Expected status code <200> but was <401>
```

**Solution:**
```java
// Add authentication headers
@Test
void testWithAuth() {
    given()
        .header("Authorization", "Bearer " + getAuthToken())
        .when()
        .get("/protected-endpoint")
        .then()
        .statusCode(200);
}

// Or switch to public API for learning
BaseApiUtils.setBaseUri("https://jsonplaceholder.typicode.com");
```

#### **3. Allure Report Issues**

**Problem:** Allure report shows loading screen only
```
Report opens but shows only loading animation
```

**Solutions:**
```bash
# Option 1: Use allure serve instead
./gradlew allureServe

# Option 2: Check file permissions
chmod -R 755 build/reports/allure-report/

# Option 3: Clear browser cache
# Open browser in incognito mode

# Option 4: Generate fresh report
./gradlew cleanAllureResults test allureReport
```

**Problem:** Missing test results dalam report
```
Allure report is empty or missing tests
```

**Solution:**
```bash
# Ensure allure-results directory has content
ls -la build/allure-results/

# Re-run tests dan generate report
./gradlew clean test allureReport

# Check system properties
./gradlew test -Dallure.results.directory=build/allure-results
```

### B. Debugging Techniques

#### **1. Request/Response Logging**
```java
@Test
void debugApiCall() {
    given()
        .filter(new RequestLoggingFilter(LogDetail.ALL))
        .filter(new ResponseLoggingFilter(LogDetail.ALL))
        .when()
        .get("/users")
        .then()
        .statusCode(200);
}
```

#### **2. Step-by-Step Debugging**
```java
@Test
void stepByStepDebugging() {
    System.out.println("🔍 Starting API test...");
    
    // Step 1: Prepare request
    RequestSpecification request = given()
            .header("Content-Type", "application/json");
    System.out.println("✅ Request prepared");
    
    // Step 2: Send request
    Response response = request.when().get("/users");
    System.out.println("✅ Request sent");
    
    // Step 3: Analyze response
    System.out.println("📊 Status Code: " + response.getStatusCode());
    System.out.println("⏱️  Response Time: " + response.getTime() + "ms");
    System.out.println("📄 Response Body: " + response.getBody().asString());
    
    // Step 4: Validate response
    response.then().statusCode(200);
    System.out.println("✅ Validation complete");
}
```

#### **3. Environment Verification**
```java
@Test
void verifyEnvironment() {
    System.out.println("🌐 Base URI: " + RestAssured.baseURI);
    System.out.println("🔧 Java Version: " + System.getProperty("java.version"));
    System.out.println("📁 Working Directory: " + System.getProperty("user.dir"));
    
    // Test connectivity
    Response healthCheck = given().when().get("/");
    System.out.println("🏥 Health Check Status: " + healthCheck.getStatusCode());
}
```

### C. Performance Monitoring

#### **1. Response Time Tracking**
```java
@Test
void monitorPerformance() {
    List<Long> responseTimes = new ArrayList<>();
    
    for (int i = 0; i < 10; i++) {
        long startTime = System.currentTimeMillis();
        
        given().when().get("/users")
               .then().statusCode(200);
        
        long endTime = System.currentTimeMillis();
        responseTimes.add(endTime - startTime);
    }
    
    double avgTime = responseTimes.stream().mapToLong(Long::longValue).average().orElse(0);
    long maxTime = responseTimes.stream().mapToLong(Long::longValue).max().orElse(0);
    long minTime = responseTimes.stream().mapToLong(Long::longValue).min().orElse(0);
    
    System.out.println("📊 Performance Metrics:");
    System.out.println("   Average: " + avgTime + "ms");
    System.out.println("   Maximum: " + maxTime + "ms");
    System.out.println("   Minimum: " + minTime + "ms");
    
    assertTrue(avgTime < 1000, "Average response time should be under 1 second");
}
```

---

## 10. APPENDIX

### A. Useful Resources

#### **Documentation Links**
- [REST Assured Documentation](http://rest-assured.io/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
- [JSON Path Documentation](https://github.com/json-path/JsonPath)

#### **API Testing Resources**
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com/)
- [ReqRes API](https://reqres.in/)
- [HTTPBin Testing Service](https://httpbin.org/)
- [Postman API Testing](https://www.postman.com/)

#### **Learning Materials**
- [API Testing Best Practices](https://www.ministryoftesting.com/dojo/lessons/api-testing)
- [REST API Testing Tutorial](https://www.guru99.com/testing-rest-api-manually.html)
- [Java API Testing Course](https://testautomationu.applitools.com/)

### B. Code Templates

#### **Basic Test Template**
```java
@Epic("API Module Name")
@Feature("Feature Being Tested")
public class TemplateApiTest {
    
    @BeforeAll
    static void setup() {
        BaseApiUtils.setBaseUri(ApiConfig.BASE_URL);
    }
    
    @Test
    @Story("User Story")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Description")
    @Description("Detailed test description")
    void testTemplate() {
        // Arrange
        Object testData = TestDataGenerator.generateTestData();
        
        // Act
        Response response = BaseApiUtils.getRequestSpec()
                .body(testData)
                .when()
                .post("/endpoint");
        
        // Assert
        response.then()
                .statusCode(expectedStatusCode)
                .body("field", expectedValue);
        
        // Additional validations
        Object responseObject = response.as(ResponseClass.class);
        assertNotNull(responseObject);
    }
}
```

#### **Data Provider Template**
```java
public class TestDataProvider {
    
    public static Stream<Arguments> validUserData() {
        return Stream.of(
            Arguments.of("John Doe", "john@example.com", "Developer"),
            Arguments.of("Jane Smith", "jane@example.com", "Tester"),
            Arguments.of("Bob Johnson", "bob@example.com", "Manager")
        );
    }
    
    public static Stream<Arguments> invalidUserData() {
        return Stream.of(
            Arguments.of("", "john@example.com", "Name cannot be empty"),
            Arguments.of("John", "invalid-email", "Invalid email format"),
            Arguments.of("Jane", "jane@example.com", "")
        );
    }
}

// Usage in test
@ParameterizedTest
@MethodSource("TestDataProvider#validUserData")
void testCreateUserWithValidData(String name, String email, String role) {
    // Test implementation
}
```

### C. Configuration Examples

#### **gradle.properties**
```properties
# Project settings
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true

# Java settings
org.gradle.java.home=/path/to/java11

# Memory settings
org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m

# Test settings
test.maxParallelForks=4
test.forkEvery=100
```

#### **logback-test.xml**
```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <logger name="io.restassured" level="DEBUG"/>
    <logger name="com.api.automation" level="INFO"/>
    
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

### D. Cheat Sheet

#### **Quick Commands**
```bash
# Build & Test
./gradlew clean build test

# Generate Reports
./gradlew allureReport
./gradlew allureServe

# Run Specific Tests
./gradlew test --tests "*UserApiTest*"
./gradlew test --tests "*.testGetAllUsers*"

# Debug Mode
./gradlew test --debug-jvm
./gradlew test --info

# Parallel Execution
./gradlew test --parallel --max-workers=4
```

#### **Common Assertions**
```java
// Status Code
.statusCode(200)
.statusCode(anyOf(200, 201, 202))

// Response Body
.body("name", equalTo("John"))
.body("age", greaterThan(18))
.body("email", containsString("@"))
.body("items", hasSize(5))
.body("items[0].id", notNullValue())

// Headers
.header("Content-Type", "application/json")
.header("X-Total-Count", notNullValue())

// Response Time
.time(lessThan(2000L))
```

#### **JSON Path Examples**
```java
// Simple fields
.body("id", equalTo(1))
.body("name", equalTo("John Doe"))

// Nested objects
.body("address.street", equalTo("Main St"))
.body("company.name", containsString("Tech"))

// Arrays
.body("hobbies", hasSize(3))
.body("hobbies[0]", equalTo("reading"))
.body("skills", hasItems("Java", "Python"))

// Complex queries
.body("users.findAll { it.age > 25 }.name", hasItems("John", "Jane"))
```

---

## KESIMPULAN

Framework API Automation ini menyediakan foundation yang solid untuk QA Engineer dalam melakukan testing API yang comprehensive. Dengan mengikuti panduan ini, QA Engineer dapat:

✅ **Memahami struktur** dan komponen framework  
✅ **Menulis test cases** yang effective dan maintainable  
✅ **Melakukan debugging** dan troubleshooting dengan confidence  
✅ **Generate reports** yang informatif untuk stakeholders  
✅ **Implement best practices** dalam API testing  
✅ **Continuous improvement** dalam testing skills  

**Ingat:** API testing adalah skill yang berkembang dengan practice. Mulai dengan basic tests, pahami fundamentals, lalu gradually tingkatkan complexity sesuai dengan project requirements.

---

**Contact & Support:**
- Framework Repository: [Project GitHub Link]
- Documentation: [Internal Wiki Link]
- Team Slack: #qa-automation
- Training Materials: [Learning Resources Link]

**Version History:**
- v1.0 - Initial framework implementation
- v1.1 - Added Allure reporting integration
- v1.2 - Enhanced error handling dan debugging features

---

*© 2025 QA Engineering Team. All rights reserved.*
