package com.api.automation.utils;

import com.github.javafaker.Faker;
import com.api.automation.models.Post;
import com.api.automation.models.User;

/**
 * Utility class untuk generate test data menggunakan Faker
 */
public class TestDataGenerator {
    
    private static final Faker faker = new Faker();
    
    /**
     * Generate random Post object
     * @return Post object dengan data random
     */
    public static Post generateRandomPost() {
        return new Post(
                faker.number().numberBetween(1, 10),
                faker.lorem().sentence(),
                faker.lorem().paragraph()
        );
    }
    
    /**
     * Generate random Post object dengan userId tertentu
     * @param userId userId untuk post
     * @return Post object dengan data random
     */
    public static Post generateRandomPost(int userId) {
        return new Post(
                userId,
                faker.lorem().sentence(),
                faker.lorem().paragraph()
        );
    }
    
    /**
     * Generate random User object
     * @return User object dengan data random
     */
    public static User generateRandomUser() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String email = username + "@" + faker.internet().domainName();
        
        return new User(
                firstName + " " + lastName,
                username,
                email
        );
    }
    
    /**
     * Generate invalid email format
     * @return String dengan format email yang tidak valid
     */
    public static String generateInvalidEmail() {
        return faker.lorem().word() + "invalid.email";
    }
    
    /**
     * Generate very long string untuk boundary testing
     * @param length panjang string yang diinginkan
     * @return String dengan panjang tertentu
     */
    public static String generateLongString(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(faker.lorem().word()).append(" ");
        }
        return sb.toString().substring(0, length);
    }
    
    /**
     * Generate random valid user ID
     * @return Integer user ID yang valid (1-10)
     */
    public static int generateValidUserId() {
        return faker.number().numberBetween(1, 11);
    }
    
    /**
     * Generate random invalid user ID
     * @return Integer user ID yang tidak valid
     */
    public static int generateInvalidUserId() {
        int[] invalidIds = {-1, 0, 999, 1000};
        return invalidIds[faker.number().numberBetween(0, invalidIds.length)];
    }
}
