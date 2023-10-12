package com.kmelo.todolist.model.repositories;

import com.kmelo.todolist.model.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
}
