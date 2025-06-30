package com.api.automation.utils;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import com.api.automation.config.ApiConfig;

/**
 * Base class untuk setup REST Assured configuration
 */
public class BaseApiUtils {
    
    static {
        setupRestAssured();
    }
    
    /**
     * Setup konfigurasi dasar REST Assured
     */
    private static void setupRestAssured() {
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", ApiConfig.CONNECTION_TIMEOUT * 1000)
                        .setParam("http.socket.timeout", ApiConfig.DEFAULT_TIMEOUT * 1000));
    }
    
    /**
     * Mendapatkan RequestSpecification dengan logging
     * @return RequestSpecification dengan logging enabled
     */
    public static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
    
    /**
     * Mendapatkan RequestSpecification tanpa logging (untuk performance tests)
     * @return RequestSpecification tanpa logging
     */
    public static RequestSpecification getRequestSpecWithoutLogging() {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
    
    /**
     * Setup base URI untuk testing
     * @param baseUri Base URI yang akan digunakan
     */
    public static void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
    }
}
