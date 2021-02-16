package com.ToDo.todoApp.model.Dtos.GroupDtos;

import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoTaskInGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private LocalDate deadline;
    private boolean done;
    private List<TaskDtoTaskInGroup> tasksInGroup;
}
