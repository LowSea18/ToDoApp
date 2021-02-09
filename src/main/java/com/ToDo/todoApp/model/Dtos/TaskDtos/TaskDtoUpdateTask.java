package com.ToDo.todoApp.model.Dtos.TaskDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TaskDtoUpdateTask {
    private String description;
    private boolean done;
    private LocalDate deadline;
}
