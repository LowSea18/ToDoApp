package com.ToDo.todoApp.model.Dtos.TaskDtos;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDtoCreateTask {
    @NotNull
    private String description;
    @NotNull
    private boolean done;
    @NotNull
    private LocalDate deadline;
}
