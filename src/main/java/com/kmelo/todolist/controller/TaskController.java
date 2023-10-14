package com.kmelo.todolist.controller;

import com.kmelo.todolist.model.entities.TaskEntity;
import com.kmelo.todolist.model.repositories.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/tasks/")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<TaskEntity> listTasks(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");

        return this.taskRepository.findByIdUser(idUser);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TaskEntity task, HttpServletRequest request) {
        task.setIdUser((UUID) request.getAttribute("idUser"));

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data deve ser maior que a atual");
        } else if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio deve ser menor que a final");
        } else {
            taskRepository.save(task);
            return ResponseEntity.status(200).body(task);
        }
    }
}
