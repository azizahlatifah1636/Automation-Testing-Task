package com.api.automation.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class untuk User entity
 */
public class User {
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("website")
    private String website;
    
    @JsonProperty("address")
    private Address address;
    
    @JsonProperty("company")
    private Company company;
    
    // Constructors
    public User() {}
    
    public User(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    
    // Nested classes
    public static class Address {
        @JsonProperty("street")
        private String street;
        
        @JsonProperty("suite")
        private String suite;
        
        @JsonProperty("city")
        private String city;
        
        @JsonProperty("zipcode")
        private String zipcode;
        
        @JsonProperty("geo")
        private Geo geo;
        
        // Getters and Setters
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        
        public String getSuite() { return suite; }
        public void setSuite(String suite) { this.suite = suite; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getZipcode() { return zipcode; }
        public void setZipcode(String zipcode) { this.zipcode = zipcode; }
        
        public Geo getGeo() { return geo; }
        public void setGeo(Geo geo) { this.geo = geo; }
    }
    
    public static class Geo {
        @JsonProperty("lat")
        private String lat;
        
        @JsonProperty("lng")
        private String lng;
        
        // Getters and Setters
        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }
        
        public String getLng() { return lng; }
        public void setLng(String lng) { this.lng = lng; }
    }
    
    public static class Company {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("catchPhrase")
        private String catchPhrase;
        
        @JsonProperty("bs")
        private String bs;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getCatchPhrase() { return catchPhrase; }
        public void setCatchPhrase(String catchPhrase) { this.catchPhrase = catchPhrase; }
        
        public String getBs() { return bs; }
        public void setBs(String bs) { this.bs = bs; }
    }
}
