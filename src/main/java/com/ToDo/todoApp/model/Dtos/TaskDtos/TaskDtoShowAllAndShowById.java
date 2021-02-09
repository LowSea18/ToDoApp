package com.ToDo.todoApp.model.Dtos.TaskDtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDtoShowAllAndShowById {
    private Long id;
    private String description;
    private boolean done;
    private LocalDate deadline;
    private Long groupId;
}
