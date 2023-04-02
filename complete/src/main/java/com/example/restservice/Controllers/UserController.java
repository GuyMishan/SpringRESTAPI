package com.example.restservice.Controllers;

import com.example.restservice.Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File dataFile = new File("src/main/resources/data.json");

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() throws IOException {
        User[] users = objectMapper.readValue(dataFile, User[].class);
        return new ResponseEntity<>(Arrays.asList(users), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws IOException {
        User[] users = objectMapper.readValue(dataFile, User[].class);
        for (User user : users) {
            if (user.getId()==id) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) throws IOException {
        User[] users = objectMapper.readValue(dataFile, User[].class);
        for (User existingUser : users) {
            if (existingUser.getId()==(user.getId())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        User[] newUsers = Arrays.copyOf(users, users.length + 1);
        newUsers[newUsers.length - 1] = user;
        objectMapper.writeValue(dataFile, newUsers);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws IOException {
    	User[] users = objectMapper.readValue(dataFile, User[].class);
    	for (int i = 0; i < users.length; i++) {
    	if (users[i].getId()== id) {
    	users[i] = user;
    	objectMapper.writeValue(dataFile, Arrays.asList(users));
    	return new ResponseEntity<>(user, HttpStatus.OK);
    	}
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws IOException {
        User[] users = objectMapper.readValue(dataFile, User[].class);
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId()==id) {
                users[i] = null;
                User[] newUsers = Arrays.stream(users)
                                         .filter(Objects::nonNull)
                                         .toArray(User[]::new);
                objectMapper.writeValue(dataFile, newUsers);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
