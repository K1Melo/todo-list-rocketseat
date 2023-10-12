package com.kmelo.todolist.controller;

import com.kmelo.todolist.model.entities.TaskEntity;
import com.kmelo.todolist.model.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<TaskEntity> listTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public TaskEntity create(@RequestBody TaskEntity task) {
        return taskRepository.save(task);
    }
}
