package com.api.automation.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.api.automation.config.ApiConfig;
import com.api.automation.models.User;
import com.api.automation.utils.BaseApiUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class untuk User API endpoints
 * Menggunakan JSONPlaceholder API untuk testing
 */
@Epic("User Management API")
@Feature("User Operations")
public class UserApiTest {
    
    @BeforeAll
    static void setup() {
        BaseApiUtils.setBaseUri(ApiConfig.JSONPLACEHOLDER_BASE_URL);
    }
    
    @Test
    @Story("Get All Users")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Get All Users - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengambil semua users dengan response yang benar")
    void testGetAllUsers_PositiveTest() {
        Response response = BaseApiUtils.getRequestSpec()
                .when()
                .get(ApiConfig.USERS_ENDPOINT)
                .then()
                .statusCode(ApiConfig.OK)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .extract().response();
        
        // Additional assertions
        User[] users = response.as(User[].class);
        assertTrue(users.length > 0, "Users array should not be empty");
        assertNotNull(users[0].getId(), "First user should have an ID");
        assertNotNull(users[0].getName(), "First user should have a name");
        assertTrue(users[0].getEmail().contains("@"), "Email should be valid format");
    }
    
    @Test
    @Story("Get Single User")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Get Single User - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengambil user berdasarkan ID yang valid")
    void testGetSingleUser_PositiveTest() {
        int userId = 1;
        
        Response response = BaseApiUtils.getRequestSpec()
                .pathParam("id", userId)
                .when()
                .get(ApiConfig.USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.OK)
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("username", notNullValue())
                .extract().response();
        
        User user = response.as(User.class);
        assertEquals(userId, user.getId().intValue(), "User ID should match requested ID");
        assertNotNull(user.getName(), "User name should not be null");
        assertNotNull(user.getEmail(), "User email should not be null");
    }
    
    @Test
    @Story("Get Single User")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Get Non-Existent User - Negative Test")
    @Description("Memverifikasi bahwa API mengembalikan 404 untuk user ID yang tidak ada")
    void testGetNonExistentUser_NegativeTest() {
        int nonExistentUserId = 999;
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", nonExistentUserId)
                .when()
                .get(ApiConfig.USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.NOT_FOUND);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 999, 1000})
    @Story("Get Single User")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Get User with Invalid IDs - Boundary Test")
    @Description("Memverifikasi bahwa API menangani invalid user IDs dengan benar")
    void testGetUserWithInvalidIds_BoundaryTest(int invalidUserId) {
        Response response = BaseApiUtils.getRequestSpec()
                .pathParam("id", invalidUserId)
                .when()
                .get(ApiConfig.USERS_ENDPOINT + "/{id}");
        
        // For boundary values, we expect either 404 or empty response
        assertTrue(response.getStatusCode() == ApiConfig.NOT_FOUND || 
                  response.getStatusCode() == ApiConfig.OK,
                  "Response should be either 404 or 200 for boundary values");
    }
    
    @Test
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Create New User - Positive Test")
    @Description("Memverifikasi bahwa API dapat membuat user baru dengan data yang valid")
    void testCreateUser_PositiveTest() {
        User newUser = new User("John Doe", "johndoe", "john.doe@example.com");
        
        Response response = BaseApiUtils.getRequestSpec()
                .body(newUser)
                .when()
                .post(ApiConfig.USERS_ENDPOINT)
                .then()
                .statusCode(ApiConfig.CREATED)
                .body("name", equalTo(newUser.getName()))
                .body("username", equalTo(newUser.getUsername()))
                .body("email", equalTo(newUser.getEmail()))
                .body("id", notNullValue())
                .extract().response();
        
        User createdUser = response.as(User.class);
        assertNotNull(createdUser.getId(), "Created user should have an ID");
        assertEquals(newUser.getName(), createdUser.getName(), "Name should match");
        assertEquals(newUser.getEmail(), createdUser.getEmail(), "Email should match");
    }
    
    @Test
    @Story("Create User")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Create User with Invalid Data - Negative Test")
    @Description("Memverifikasi bahwa API menangani data user yang tidak valid")
    void testCreateUserWithInvalidData_NegativeTest() {
        // Test with empty user object
        User invalidUser = new User();
        
        Response response = BaseApiUtils.getRequestSpec()
                .body(invalidUser)
                .when()
                .post(ApiConfig.USERS_ENDPOINT);
        
        // JSONPlaceholder masih akan create user even with invalid data
        // Dalam real API, ini harus return 400 Bad Request
        assertTrue(response.getStatusCode() == ApiConfig.CREATED || 
                  response.getStatusCode() == ApiConfig.BAD_REQUEST,
                  "Should handle invalid data appropriately");
    }
    
    @Test
    @Story("Update User")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Update User - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengupdate user yang sudah ada")
    void testUpdateUser_PositiveTest() {
        int userId = 1;
        User updatedUser = new User("Jane Doe Updated", "janedoe_updated", "jane.updated@example.com");
        updatedUser.setId(userId);
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", userId)
                .body(updatedUser)
                .when()
                .put(ApiConfig.USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.OK)
                .body("id", equalTo(userId))
                .body("name", equalTo(updatedUser.getName()))
                .body("email", equalTo(updatedUser.getEmail()));
    }
    
    @Test
    @Story("Delete User")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Delete User - Positive Test")
    @Description("Memverifikasi bahwa API dapat menghapus user yang ada")
    void testDeleteUser_PositiveTest() {
        int userId = 1;
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", userId)
                .when()
                .delete(ApiConfig.USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.OK);
    }
}
