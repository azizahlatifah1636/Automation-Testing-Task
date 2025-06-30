package com.api.automation.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.api.automation.config.ApiConfig;
import com.api.automation.utils.BaseApiUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CONTOH PRAKTIS: Test class untuk ReqRes API
 * Ini menunjukkan bagaimana QA Engineer membuat test cases
 */
@Epic("ReqRes API Testing")
@Feature("User Management ReqRes")
public class ReqResApiTest {
    
    @BeforeAll
    static void setup() {
        BaseApiUtils.setBaseUri(ApiConfig.REQRES_BASE_URL);
    }
    
    /**
     * CONTOH 1: Positive Test - Test yang harus BERHASIL
     */
    @Test
    @Story("Get Users")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CONTOH: Get Users from ReqRes - Should Return Valid Response")
    @Description("QA Engineer harus memverifikasi bahwa API mengembalikan data users yang valid")
    void contohPositiveTest_GetUsers() {
        // LANGKAH 1: Kirim request GET ke API
        Response response = BaseApiUtils.getRequestSpec()
                .queryParam("page", 2)
                .when()
                .get(ApiConfig.REQRES_USERS_ENDPOINT)
                .then()
                .statusCode(200)  // VERIFIKASI: Status code harus 200
                .body("page", equalTo(2))  // VERIFIKASI: Page number benar
                .body("data.size()", greaterThan(0))  // VERIFIKASI: Ada data users
                .body("data[0].email", containsString("@reqres.in"))  // VERIFIKASI: Email format benar
                .extract().response();
        
        // LANGKAH 2: Additional assertions untuk validasi lebih detail
        String jsonResponse = response.asString();
        assertTrue(jsonResponse.contains("data"), "Response harus mengandung field 'data'");
        assertTrue(jsonResponse.contains("total"), "Response harus mengandung field 'total'");
        
        System.out.println("✅ POSITIVE TEST PASSED: API mengembalikan data users yang valid");
    }
    
    /**
     * CONTOH 2: Negative Test - Test yang harus GAGAL/ERROR
     */
    @Test
    @Story("Get Single User")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CONTOH: Get Non-Existent User - Should Return 404")
    @Description("QA Engineer harus memverifikasi bahwa API mengembalikan error untuk user yang tidak ada")
    void contohNegativeTest_GetNonExistentUser() {
        // LANGKAH 1: Request user dengan ID yang tidak ada
        int nonExistentUserId = 23;
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", nonExistentUserId)
                .when()
                .get(ApiConfig.REQRES_USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(404)  // VERIFIKASI: Harus return 404 Not Found
                .body("$", hasKey("data"))  // VERIFIKASI: Response structure tetap ada
                .body("data", nullValue());  // VERIFIKASI: Data harus null/empty
        
        System.out.println("✅ NEGATIVE TEST PASSED: API mengembalikan 404 untuk user yang tidak ada");
    }
    
    /**
     * CONTOH 3: Boundary Test - Test dengan nilai batas
     */
    @ParameterizedTest
    @CsvSource({
        "1, true",    // User pertama (valid)
        "12, true",   // User terakhir (valid) 
        "0, false",   // Invalid: ID 0
        "-1, false",  // Invalid: ID negatif
        "999, false"  // Invalid: ID terlalu besar
    })
    @Story("Get Single User")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CONTOH: Boundary Testing untuk User ID")
    @Description("QA Engineer harus test boundary values untuk memastikan API handle edge cases")
    void contohBoundaryTest_UserIds(int userId, boolean shouldExist) {
        Response response = BaseApiUtils.getRequestSpec()
                .pathParam("id", userId)
                .when()
                .get(ApiConfig.REQRES_USERS_ENDPOINT + "/{id}");
        
        if (shouldExist) {
            // VERIFIKASI: User yang valid harus return 200
            response.then()
                    .statusCode(200)
                    .body("data.id", equalTo(userId));
            System.out.println("✅ BOUNDARY TEST: User ID " + userId + " valid (200 OK)");
        } else {
            // VERIFIKASI: User yang invalid harus return 404
            response.then()
                    .statusCode(404);
            System.out.println("✅ BOUNDARY TEST: User ID " + userId + " invalid (404 Not Found)");
        }
    }
    
    /**
     * CONTOH 4: POST Request Test - Create User
     */
    @Test
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CONTOH: Create New User - Should Return Created User with ID")
    @Description("QA Engineer harus memverifikasi bahwa API dapat membuat user baru")
    void contohCreateUser_PostRequest() {
        // LANGKAH 1: Prepare test data
        String requestBody = "{\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"job\": \"QA Engineer\"\n" +
                "}";
        
        // LANGKAH 2: Kirim POST request
        Response response = BaseApiUtils.getRequestSpec()
                .body(requestBody)
                .when()
                .post(ApiConfig.REQRES_USERS_ENDPOINT)
                .then()
                .statusCode(201)  // VERIFIKASI: Status 201 Created
                .body("name", equalTo("John Doe"))  // VERIFIKASI: Name sesuai input
                .body("job", equalTo("QA Engineer"))  // VERIFIKASI: Job sesuai input
                .body("id", notNullValue())  // VERIFIKASI: ID ter-generate
                .body("createdAt", notNullValue())  // VERIFIKASI: Timestamp ada
                .extract().response();
        
        // LANGKAH 3: Additional validations
        String createdId = response.jsonPath().getString("id");
        assertNotNull(createdId, "User ID harus ter-generate setelah create");
        assertTrue(Integer.parseInt(createdId) > 0, "User ID harus berupa angka positif");
        
        System.out.println("✅ CREATE USER TEST PASSED: User berhasil dibuat dengan ID: " + createdId);
    }
    
    /**
     * CONTOH 5: Performance/Load Test Sederhana
     */
    @Test
    @Story("Performance")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("CONTOH: Response Time Test - Should Respond Within 2 Seconds")
    @Description("QA Engineer harus memverifikasi bahwa API response time dalam batas wajar")
    void contohPerformanceTest_ResponseTime() {
        long startTime = System.currentTimeMillis();
        
        BaseApiUtils.getRequestSpecWithoutLogging()  // Tanpa logging untuk performance
                .when()
                .get(ApiConfig.REQRES_USERS_ENDPOINT)
                .then()
                .statusCode(200)
                .time(lessThan(2000L));  // VERIFIKASI: Response dalam 2 detik
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        assertTrue(responseTime < 2000, "Response time harus kurang dari 2 detik, actual: " + responseTime + "ms");
        System.out.println("✅ PERFORMANCE TEST PASSED: Response time: " + responseTime + "ms");
    }
}
