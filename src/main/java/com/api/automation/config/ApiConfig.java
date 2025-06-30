package com.api.automation.config;

/**
 * Configuration class untuk menyimpan base URLs dan endpoints
 */
public class ApiConfig {
    
    // Base URLs
    public static final String JSONPLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String REQRES_BASE_URL = "https://reqres.in/api";
    public static final String HTTPBIN_BASE_URL = "https://httpbin.org";
    
    // JSONPlaceholder Endpoints
    public static final String POSTS_ENDPOINT = "/posts";
    public static final String USERS_ENDPOINT = "/users";
    public static final String COMMENTS_ENDPOINT = "/comments";
    
    // ReqRes Endpoints
    public static final String REQRES_USERS_ENDPOINT = "/users";
    public static final String REQRES_LOGIN_ENDPOINT = "/login";
    public static final String REQRES_REGISTER_ENDPOINT = "/register";
    
    // HTTPBin Endpoints
    public static final String GET_ENDPOINT = "/get";
    public static final String POST_ENDPOINT = "/post";
    public static final String PUT_ENDPOINT = "/put";
    public static final String DELETE_ENDPOINT = "/delete";
    public static final String STATUS_ENDPOINT = "/status";
    
    // Default timeouts (in seconds)
    public static final int DEFAULT_TIMEOUT = 30;
    public static final int CONNECTION_TIMEOUT = 10;
    
    // HTTP Status Codes
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NO_CONTENT = 204;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;
}
