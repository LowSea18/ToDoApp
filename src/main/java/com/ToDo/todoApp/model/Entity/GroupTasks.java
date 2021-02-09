package com.ToDo.todoApp.model.Entity;

import com.ToDo.todoApp.model.Entity.Task;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "group_tasks")
@RequiredArgsConstructor
public class GroupTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime deadline;
    @NotNull
    private String name;
    private boolean done;
    @OneToMany(mappedBy = "group")
    private List<Task> tasksInGroup;
}