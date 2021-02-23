package com.ToDo.todoApp.controllers;


import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Repositories.TaskRepository;
import com.ToDo.todoApp.Services.TaskService;
import com.ToDo.todoApp.mappers.GroupMapping;
import com.ToDo.todoApp.mappers.TasksMapping;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestTaskControllerE2ETests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
     TaskRepository taskRepository;
    @Autowired
     GroupRepository groupRepository;
    @Autowired
    GroupMapping groupMapping;
    @Autowired
    TasksMapping tasksMapping;
    @Autowired
    TaskService taskService;

    private final String server = "http://localhost:";



    public void setUp(){
        GroupDtoCreateGroup createGroup = new GroupDtoCreateGroup();
        createGroup.setName("Trip");
        groupRepository.save(groupMapping.mapCreateToGroup(createGroup));

        TaskDtoCreateTask createTask = new TaskDtoCreateTask();
        TaskDtoCreateTask createTask1 = new TaskDtoCreateTask();
        TaskDtoCreateTask createTask2 = new TaskDtoCreateTask();
        createTask.setDescription("shopping");
        createTask1.setDescription("car repair");
        createTask2.setDescription("buying drugs");
        createTask.setDeadline(LocalDate.of(2222,1,1));
        createTask1.setDeadline(LocalDate.of(2232,1,1));
        createTask2.setDeadline(LocalDate.of(2212,1,1));
        createTask.setGroupId(1L);
        createTask1.setGroupId(1L);
        createTask2.setGroupId(1L);
        taskService.addNewTask(createTask);
        taskService.addNewTask(createTask1);
        taskService.addNewTask(createTask2);
    }



    @Test
     void should_returnAllTasks(){
        setUp();

        TaskDtoShowAllAndShowById[] result = restTemplate.getForObject(server + port + "/tasks", TaskDtoShowAllAndShowById[].class);

        assertThat(result).hasSize(3);
    }

    @Test
    void should_returnTaskByid(){
        setUp();
        TaskDtoShowAllAndShowById[] result = restTemplate.getForObject(server + port + "/tasks", TaskDtoShowAllAndShowById[].class);

        assertThat(result).hasSize(0);
    }

     /*@Test
    void should_group_has_theLatestDeadLineOfTasks(){

        assertEquals(groupRepository.findByName("Trip").get().getDeadline(),LocalDate.of(2232,1,1));
    }

    @Test
    void should_task_has_deafoultFlaseValueOnDone(){

        assertFalse(taskRepository.findById(2L).get().isDone());
    }

    @Test
    void should_group_has_deafoultFlaseValueOnDone(){

        assertFalse(groupRepository.findByName("Trip").get().isDone());
    }

    @Test
    void should_DoneTask(){

        taskService.setDoneTask(1L);
        assertTrue(taskRepository.findById(1L).get().isDone());
    }

    @Test
    void should_group_has_done_if_all_Tasks_isDone(){
        assertFalse(taskRepository.findById(1L).get().isDone());
    }



     */

}
