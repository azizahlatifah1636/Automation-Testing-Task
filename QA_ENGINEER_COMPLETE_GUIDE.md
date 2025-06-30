# PANDUAN LENGKAP UNTUK QA ENGINEER
# API Automation Framework

## 🎯 APA YANG HARUS DILAKUKAN QA ENGINEER?

### 1. PERSIAPAN PERTAMA KALI (ONE-TIME SETUP)

#### Install Prerequisites:
```bash
# 1. Pastikan Java 11+ sudah terinstall
java -version

# 2. Clone atau download project ini
# 3. Buka project di IDE (IntelliJ/Eclipse/VS Code)
```

#### Verifikasi Setup:
```bash
# Di terminal, jalankan:
./gradlew build

# Jika berhasil, lanjut dengan:
./gradlew test
```

### 2. WORKFLOW HARIAN QA ENGINEER

#### Morning Routine (Setiap Hari):
1. **Pull Latest Code**:
   ```bash
   git pull origin main
   ```

2. **Run Smoke Tests**:
   ```bash
   ./gradlew test --tests "*SmokeTest*"
   ```

3. **Check Build Status**:
   ```bash
   ./gradlew build
   ```

#### Development Cycle:
1. **Analyze New Requirements** 📋
   - Baca requirement document
   - Identify API endpoints yang perlu ditest
   - List test scenarios (positive, negative, boundary)

2. **Write Test Cases** ✍️
   - Create new test class atau extend existing
   - Follow naming convention: `testAction_Scenario()`
   - Add Allure annotations

3. **Execute Tests** ▶️
   ```bash
   # Run specific test class
   ./gradlew test --tests UserApiTest
   
   # Run specific test method
   ./gradlew test --tests UserApiTest.testGetUser_ValidId
   ```

4. **Generate Reports** 📊
   ```bash
   ./gradlew allureReport
   ./gradlew allureServe
   ```

### 3. CARA MENULIS TEST BARU

#### Template Test Class:
```java
@Epic("API Testing")
@Feature("Your Feature Name")
public class YourApiTest extends BaseApiUtils {
    
    @Test
    @Story("Your Story")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test description")
    public void testYourAction_Scenario() {
        // Given (Setup)
        String endpoint = ApiConfig.YOUR_ENDPOINT;
        
        // When (Action)
        Response response = given()
            .when()
            .get(endpoint);
        
        // Then (Assertion)
        response.then()
            .statusCode(200)
            .body("key", equalTo("expectedValue"));
    }
}
```

#### Test Types yang Wajib Dibuat:

1. **Positive Test** ✅
   ```java
   @Test
   public void testCreateUser_ValidData() {
       User user = TestDataGenerator.generateValidUser();
       
       Response response = given()
           .contentType(ContentType.JSON)
           .body(user)
           .when()
           .post(ApiConfig.USERS_ENDPOINT);
       
       response.then()
           .statusCode(201)
           .body("name", equalTo(user.getName()));
   }
   ```

2. **Negative Test** ❌
   ```java
   @Test
   public void testCreateUser_InvalidData() {
       // Test dengan data invalid
       User user = new User();
       user.setName(""); // Empty name
       
       Response response = given()
           .contentType(ContentType.JSON)
           .body(user)
           .when()
           .post(ApiConfig.USERS_ENDPOINT);
       
       response.then()
           .statusCode(400); // Bad Request
   }
   ```

3. **Boundary Test** 🔢
   ```java
   @ParameterizedTest
   @ValueSource(ints = {0, -1, 9999})
   public void testGetUser_InvalidIds(int invalidId) {
       Response response = given()
           .when()
           .get(ApiConfig.USERS_ENDPOINT + "/" + invalidId);
       
       response.then()
           .statusCode(404); // Not Found
   }
   ```

### 4. DEBUGGING KETIKA TEST GAGAL

#### Step-by-Step Debugging:

1. **Check Error Message** 📝
   ```java
   // Tambahkan logging
   System.out.println("Response: " + response.asString());
   System.out.println("Status Code: " + response.getStatusCode());
   ```

2. **Validate Request** 🔍
   ```java
   // Print request details
   given()
       .log().all() // Log semua request details
       .when()
       .get(endpoint)
       .then()
       .log().all(); // Log semua response details
   ```

3. **Common Issues & Solutions** 🔧
   | Error | Penyebab | Solusi |
   |-------|----------|--------|
   | 401 Unauthorized | Missing auth | Add headers/token |
   | 404 Not Found | Wrong endpoint | Check URL |
   | 500 Server Error | API issue | Check API status |
   | Timeout | Slow response | Increase timeout |

### 5. BEST PRACTICES YANG WAJIB DIIKUTI

#### Code Quality:
- ✅ **Descriptive test names**: `testGetUser_ValidId_ReturnsUserData()`
- ✅ **Clear assertions**: Validate both status code dan response body
- ✅ **Independent tests**: Setiap test harus bisa berjalan sendiri
- ✅ **Clean test data**: Use TestDataGenerator untuk data konsisten

#### Allure Annotations:
```java
@Epic("API Testing")           // Level aplikasi
@Feature("User Management")    // Fitur spesifik
@Story("Get User Details")     // User story
@Severity(SeverityLevel.CRITICAL)  // Tingkat kepentingan
@Description("Detailed description of what this test does")
```

#### REST Assured Best Practices:
```java
// Setup common configurations di BaseApiUtils
given()
    .baseUri(ApiConfig.BASE_URL)
    .contentType(ContentType.JSON)
    .header("Accept", "application/json")
    .when()
    .get("/endpoint")
    .then()
    .statusCode(200)
    .contentType(ContentType.JSON);
```

### 6. COMMANDS YANG HARUS DIKUASAI

#### Build & Test Commands:
```bash
# Clean dan build ulang
./gradlew clean build

# Run semua tests
./gradlew test

# Run test berdasarkan pattern
./gradlew test --tests "*User*"

# Run test dengan output detail
./gradlew test --info

# Skip tests (untuk build saja)
./gradlew build -x test
```

#### Allure Report Commands:
```bash
# Generate report
./gradlew allureReport

# Serve report di browser
./gradlew allureServe

# Clean allure results
./gradlew clean
```

### 7. METRICS & KPI UNTUK QA

#### Test Coverage Metrics:
- **API Endpoints**: 90%+ harus ter-cover
- **HTTP Methods**: GET, POST, PUT, DELETE
- **Status Codes**: 2xx, 4xx, 5xx scenarios
- **Test Distribution**: 60% positive, 30% negative, 10% boundary

#### Performance Metrics:
- **Test Execution Time**: < 5 menit untuk full suite
- **Individual Test**: < 30 detik per test
- **Report Generation**: < 2 menit

#### Quality Metrics:
- **Test Pass Rate**: > 95%
- **Bug Detection Rate**: Measure bugs found by automation
- **Test Maintenance**: Time spent fixing flaky tests

### 8. TROUBLESHOOTING GUIDE

#### Jika Test Gagal:
1. **Check API Status** 🌐
   ```bash
   # Test manual dengan curl
   curl -X GET "https://jsonplaceholder.typicode.com/users/1"
   ```

2. **Verify Test Data** 📊
   ```java
   // Print test data untuk debugging
   System.out.println("Test data: " + TestDataGenerator.generateUser());
   ```

3. **Check Environment** 🔧
   ```bash
   # Verify Java version
   java -version
   
   # Check Gradle version
   ./gradlew --version
   ```

#### Jika Allure Report Tidak Muncul:
1. **Clear browser cache**
2. **Regenerate report**:
   ```bash
   ./gradlew clean allureReport
   ```
3. **Check port availability**:
   ```bash
   netstat -an | findstr :9999
   ```

### 9. LEARNING PATH

#### Week 1-2: Basic Understanding
- [ ] Setup dan run existing tests
- [ ] Understand project structure
- [ ] Read dan modify simple test cases
- [ ] Generate Allure reports

#### Week 3-4: Writing Tests
- [ ] Write new positive test cases
- [ ] Add negative test scenarios
- [ ] Implement parameterized tests
- [ ] Debug failing tests

#### Week 5-6: Advanced Topics
- [ ] Custom assertions dan utilities
- [ ] Framework customization
- [ ] CI/CD integration planning
- [ ] Performance testing basics

#### Week 7+: Mastery
- [ ] Mentor junior QA engineers
- [ ] Framework improvements
- [ ] Advanced reporting
- [ ] Test strategy planning

### 10. DAILY CHECKLIST

#### Morning (15 menit):
- [ ] Pull latest code changes
- [ ] Run smoke tests
- [ ] Check overnight build results
- [ ] Review failed tests dari kemarin

#### During Development (Throughout day):
- [ ] Analyze new requirements
- [ ] Write test cases untuk new features
- [ ] Execute dan validate tests
- [ ] Update test documentation

#### End of Day (10 menit):
- [ ] Push code changes
- [ ] Generate daily test report
- [ ] Update team pada test status
- [ ] Plan tomorrow's testing activities

### 11. COLLABORATION & COMMUNICATION

#### Dengan Developer:
- **Share test results** via Allure reports
- **Report bugs** dengan detailed steps to reproduce
- **Provide API test coverage** information
- **Discuss test scenarios** untuk new features

#### Dengan Product Owner:
- **Present test coverage** metrics
- **Demo automated test execution**
- **Explain quality metrics** dan test results
- **Provide confidence level** untuk releases

#### Dengan Team:
- **Knowledge sharing** tentang new test techniques
- **Code review** untuk test automation code
- **Best practices sharing**
- **Tool dan framework updates**

### 12. TOOLS & RESOURCES

#### Essential Tools:
- **IDE**: IntelliJ IDEA (recommended), Eclipse, VS Code
- **API Testing**: Postman (untuk manual testing)
- **Version Control**: Git
- **Browser**: Chrome/Firefox (untuk Allure reports)

#### Documentation Links:
- [REST Assured](https://rest-assured.io/)
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com/)

#### Cheat Sheet:
```java
// Common REST Assured patterns
given().when().get(url)                    // GET request
given().body(object).when().post(url)      // POST request
.statusCode(200)                           // Status assertion
.body("key", equalTo("value"))             // JSON assertion
.time(lessThan(5000L))                     // Performance assertion
```

## 🎯 KESIMPULAN

Sebagai QA Engineer menggunakan framework ini, fokus utama Anda adalah:

1. **Memastikan kualitas API** melalui comprehensive testing
2. **Automasi test cases** untuk efisiensi dan consistency
3. **Generate meaningful reports** untuk stakeholder visibility
4. **Continuous improvement** dari testing process
5. **Collaboration** dengan development team untuk better quality

**Ingat**: Your job is not just finding bugs, but preventing them! 🚀

---

*Framework ini dirancang untuk memudahkan QA Engineer dalam melakukan API testing yang efektif dan efisien. Ikuti panduan ini step-by-step untuk mencapai hasil terbaik.*
