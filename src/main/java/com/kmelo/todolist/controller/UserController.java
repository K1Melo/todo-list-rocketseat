package com.kmelo.todolist.controller;

import com.kmelo.todolist.model.entities.UserEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @PostMapping(path = "/")
    public void create(@RequestBody UserEntity user) {
        System.out.println(user.getName());
    }

}
