package com.ToDo.todoApp.controllers;

import com.ToDo.todoApp.Controllers.TaskControllers.TaskController;
import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Repositories.TaskRepository;
import com.ToDo.todoApp.mappers.GroupMapping;
import com.ToDo.todoApp.mappers.TasksMapping;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import liquibase.pro.packaged.A;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestTaskControllerIntegrationTests {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMapping groupMapping;
    @Autowired
    TasksMapping tasksMapping;
    @Autowired
    MockMvc mockMvc;

    @Test
    void returnTaskById() throws Exception {

        //given
        GroupDtoCreateGroup createGroup = new GroupDtoCreateGroup();
        createGroup.setName("Wycieczka");
        groupRepository.save(groupMapping.mapCreateToGroup(createGroup));

        TaskDtoCreateTask task = new TaskDtoCreateTask();
        TaskDtoCreateTask task1 = new TaskDtoCreateTask();
        task.setGroupId(1L);
        task1.setGroupId(1L);
        task.setDeadline(LocalDate.of(2222, 2, 1));
        task1.setDeadline(LocalDate.of(2252, 2, 1));
        task.setDescription("Aj");
        task1.setDescription("Oj");
        taskRepository.save(tasksMapping.mapTaskDtoCreateTaskToTask(task));
        taskRepository.save(tasksMapping.mapTaskDtoCreateTaskToTask(task1));

        Long id = taskRepository.findById(1L).get().getId();
        //except
        mockMvc.perform(get("/tasks/" + id)).andExpect(MockMvcResultMatchers.status().isOk());

    }

}


