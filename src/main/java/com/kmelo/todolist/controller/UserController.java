package com.kmelo.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kmelo.todolist.model.entities.UserEntity;
import com.kmelo.todolist.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/")
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> create(@RequestBody UserEntity user) throws Exception {

        if(userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User alredy exists");
        }
        else {
            String password = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(password);

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created -> " + user.getUsername());
        }
    }

}