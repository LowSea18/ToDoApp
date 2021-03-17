package com.ToDo.todoApp.model.Dtos.TaskDtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDtoCreateTask {
    @NotNull
    private String description;
    @NotNull
    private LocalDate deadline;
    private  Long groupId;
    private Long userId;
}
