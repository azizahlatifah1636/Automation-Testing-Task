package com.api.automation.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.api.automation.config.ApiConfig;
import com.api.automation.models.Post;
import com.api.automation.utils.BaseApiUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class untuk Post API endpoints
 * Menggunakan JSONPlaceholder API untuk testing
 */
@Epic("Post Management API")
@Feature("Post Operations")
public class PostApiTest {
    
    @BeforeAll
    static void setup() {
        BaseApiUtils.setBaseUri(ApiConfig.JSONPLACEHOLDER_BASE_URL);
    }
    
    @Test
    @Story("Get All Posts")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Get All Posts - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengambil semua posts dengan response yang benar")
    void testGetAllPosts_PositiveTest() {
        Response response = BaseApiUtils.getRequestSpec()
                .when()
                .get(ApiConfig.POSTS_ENDPOINT)
                .then()
                .statusCode(ApiConfig.OK)
                .body("size()", equalTo(100))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue())
                .body("[0].userId", notNullValue())
                .extract().response();
        
        Post[] posts = response.as(Post[].class);
        assertEquals(100, posts.length, "Should return exactly 100 posts");
        assertNotNull(posts[0].getId(), "First post should have an ID");
        assertNotNull(posts[0].getTitle(), "First post should have a title");
    }
    
    @Test
    @Story("Get Single Post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Get Single Post - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengambil post berdasarkan ID yang valid")
    void testGetSinglePost_PositiveTest() {
        int postId = 1;
        
        Response response = BaseApiUtils.getRequestSpec()
                .pathParam("id", postId)
                .when()
                .get(ApiConfig.POSTS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.OK)
                .body("id", equalTo(postId))
                .body("title", notNullValue())
                .body("body", notNullValue())
                .body("userId", notNullValue())
                .extract().response();
        
        Post post = response.as(Post.class);
        assertEquals(postId, post.getId().intValue(), "Post ID should match requested ID");
        assertNotNull(post.getTitle(), "Post title should not be null");
        assertNotNull(post.getBody(), "Post body should not be null");
        assertNotNull(post.getUserId(), "Post userId should not be null");
    }
    
    @Test
    @Story("Get Single Post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Get Non-Existent Post - Negative Test")
    @Description("Memverifikasi bahwa API mengembalikan 404 untuk post ID yang tidak ada")
    void testGetNonExistentPost_NegativeTest() {
        int nonExistentPostId = 999;
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", nonExistentPostId)
                .when()
                .get(ApiConfig.POSTS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.NOT_FOUND);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 101, 999})
    @Story("Get Single Post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Get Post with Boundary Values - Boundary Test")
    @Description("Memverifikasi bahwa API menangani boundary values dengan benar")
    void testGetPostWithBoundaryValues_BoundaryTest(int boundaryPostId) {
        Response response = BaseApiUtils.getRequestSpec()
                .pathParam("id", boundaryPostId)
                .when()
                .get(ApiConfig.POSTS_ENDPOINT + "/{id}");
        
        if (boundaryPostId <= 0 || boundaryPostId > 100) {
            assertEquals(ApiConfig.NOT_FOUND, response.getStatusCode(),
                    "Should return 404 for out of range post IDs");
        } else {
            assertEquals(ApiConfig.OK, response.getStatusCode(),
                    "Should return 200 for valid post IDs");
        }
    }
    
    @Test
    @Story("Create Post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Create New Post - Positive Test")
    @Description("Memverifikasi bahwa API dapat membuat post baru dengan data yang valid")
    void testCreatePost_PositiveTest() {
        Post newPost = new Post(1, "Test Post Title", "This is a test post body content");
        
        Response response = BaseApiUtils.getRequestSpec()
                .body(newPost)
                .when()
                .post(ApiConfig.POSTS_ENDPOINT)
                .then()
                .statusCode(ApiConfig.CREATED)
                .body("title", equalTo(newPost.getTitle()))
                .body("body", equalTo(newPost.getBody()))
                .body("userId", equalTo(newPost.getUserId()))
                .body("id", notNullValue())
                .extract().response();
        
        Post createdPost = response.as(Post.class);
        assertNotNull(createdPost.getId(), "Created post should have an ID");
        assertEquals(newPost.getTitle(), createdPost.getTitle(), "Title should match");
        assertEquals(newPost.getBody(), createdPost.getBody(), "Body should match");
        assertEquals(newPost.getUserId(), createdPost.getUserId(), "UserId should match");
    }
    
    @Test
    @Story("Create Post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Create Post with Empty Data - Negative Test")
    @Description("Memverifikasi bahwa API menangani data post yang kosong")
    void testCreatePostWithEmptyData_NegativeTest() {
        Post emptyPost = new Post();
        
        Response response = BaseApiUtils.getRequestSpec()
                .body(emptyPost)
                .when()
                .post(ApiConfig.POSTS_ENDPOINT);
        
        // JSONPlaceholder akan tetap create post dengan data kosong
        // Dalam real API, ini harus return 400 Bad Request
        assertTrue(response.getStatusCode() == ApiConfig.CREATED || 
                  response.getStatusCode() == ApiConfig.BAD_REQUEST,
                  "Should handle empty data appropriately");
    }
    
    @Test
    @Story("Create Post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Create Post with Invalid UserId - Negative Test")
    @Description("Memverifikasi bahwa API menangani userId yang tidak valid")
    void testCreatePostWithInvalidUserId_NegativeTest() {
        Post postWithInvalidUserId = new Post(-1, "Test Title", "Test Body");
        
        Response response = BaseApiUtils.getRequestSpec()
                .body(postWithInvalidUserId)
                .when()
                .post(ApiConfig.POSTS_ENDPOINT);
        
        // Dalam real API, ini harus validate userId
        assertTrue(response.getStatusCode() == ApiConfig.CREATED || 
                  response.getStatusCode() == ApiConfig.BAD_REQUEST,
                  "Should handle invalid userId appropriately");
    }
    
    @Test
    @Story("Update Post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Update Post - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengupdate post yang sudah ada")
    void testUpdatePost_PositiveTest() {
        int postId = 1;
        Post updatedPost = new Post(1, "Updated Post Title", "This is an updated post body");
        updatedPost.setId(postId);
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", postId)
                .body(updatedPost)
                .when()
                .put(ApiConfig.POSTS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.OK)
                .body("id", equalTo(postId))
                .body("title", equalTo(updatedPost.getTitle()))
                .body("body", equalTo(updatedPost.getBody()))
                .body("userId", equalTo(updatedPost.getUserId()));
    }
    
    @Test
    @Story("Delete Post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test Delete Post - Positive Test")
    @Description("Memverifikasi bahwa API dapat menghapus post yang ada")
    void testDeletePost_PositiveTest() {
        int postId = 1;
        
        BaseApiUtils.getRequestSpec()
                .pathParam("id", postId)
                .when()
                .delete(ApiConfig.POSTS_ENDPOINT + "/{id}")
                .then()
                .statusCode(ApiConfig.OK);
    }
    
    @Test
    @Story("Get Posts by User")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Get Posts by UserId - Positive Test")
    @Description("Memverifikasi bahwa API dapat mengambil posts berdasarkan userId")
    void testGetPostsByUserId_PositiveTest() {
        int userId = 1;
        
        Response response = BaseApiUtils.getRequestSpec()
                .queryParam("userId", userId)
                .when()
                .get(ApiConfig.POSTS_ENDPOINT)
                .then()
                .statusCode(ApiConfig.OK)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(userId)))
                .extract().response();
        
        Post[] posts = response.as(Post[].class);
        assertTrue(posts.length > 0, "Should return posts for the user");
        for (Post post : posts) {
            assertEquals(userId, post.getUserId().intValue(), 
                    "All posts should belong to the specified user");
        }
    }
}
