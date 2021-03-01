package com.ToDo.todoApp.Repositories;

import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByDescription(String description);

}
