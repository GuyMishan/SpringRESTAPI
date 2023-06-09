package com.example.restservice.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public User() {
        this.id = 0;
        this.name = "";
        this.email = "";
    }
    
    @JsonCreator
    public User fromJson(JsonNode json) {
        User user = new User();
        user.id = json.get("id").asInt();
        user.name = json.get("name").asText();
        user.email = json.get("email").asText();
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
