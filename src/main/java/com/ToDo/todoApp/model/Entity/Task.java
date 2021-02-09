package com.ToDo.todoApp.model.Entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "tasks")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @NotNull
    private LocalDate deadline;
    @NotNull
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupTasks group;
}
