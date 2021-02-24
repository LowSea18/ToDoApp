package com.ToDo.todoApp.controllers;

import com.ToDo.todoApp.Controllers.TaskControllers.TaskController;
import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Repositories.TaskRepository;
import com.ToDo.todoApp.Services.GroupService;
import com.ToDo.todoApp.Services.TaskService;
import com.ToDo.todoApp.mappers.GroupMapping;
import com.ToDo.todoApp.mappers.TasksMapping;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import liquibase.pro.packaged.A;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestTaskControllerIntegrationTests {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMapping groupMapping;
    @Autowired
    private TasksMapping tasksMapping;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TaskService taskService;

    @BeforeEach
    public void setUp(){
        GroupDtoCreateGroup createGroup = new GroupDtoCreateGroup();
        createGroup.setName("Trip");
        groupService.createGroup(createGroup);

        TaskDtoCreateTask task = new TaskDtoCreateTask();
        TaskDtoCreateTask task1 = new TaskDtoCreateTask();
        TaskDtoCreateTask task2 = new TaskDtoCreateTask();
        task.setGroupId(groupRepository.findByName("Trip").get().getId());
        task1.setGroupId(groupRepository.findByName("Trip").get().getId());
        task2.setGroupId(groupRepository.findByName("Trip").get().getId());
        task.setDeadline(LocalDate.of(2222, 2, 1));
        task1.setDeadline(LocalDate.of(2252, 2, 1));
        task2.setDeadline(LocalDate.of(2232, 2, 1));
        task.setDescription("shopping");
        task1.setDescription("car repair");
        task2.setDescription("buying medicine");
        taskService.addNewTask(task);
        taskService.addNewTask(task1);
        taskService.addNewTask(task2);
    }

    @Test
    void returnTaskById() throws Exception {

        Long id = taskRepository.findById(taskRepository.findByDescription("shopping").get().getId()).get().getId();
        //except
      mockMvc.perform(get("/tasks/" + id)).andExpect(MockMvcResultMatchers.status().isOk());



    }
    @Test
    void should_group_has_latest_deadline_ofTasks(){
        TaskDtoCreateTask task = new TaskDtoCreateTask();
        task.setGroupId(groupRepository.findByName("Trip").get().getId());
        task.setDescription("Invide Friends");
        task.setDeadline(LocalDate.of(3333,1,1));
        taskService.addNewTask(task);

        Assertions.assertEquals(groupRepository.findByName("Trip").get().getDeadline(),LocalDate.of(3333,1,1) );
    }
    @Test
    void should_group_has_deafault_value_false_onDone(){
        Assertions.assertFalse(groupRepository.findByName("Trip").get().isDone());
    }
    @Test
    void should_group_beDone_if_allTask_areDone(){
        taskService.setDoneTask(taskRepository.findByDescription("shopping").get().getId());
        taskService.setDoneTask(taskRepository.findByDescription("car repair").get().getId());
        taskService.setDoneTask(taskRepository.findByDescription("buying medicine").get().getId());

        Assertions.assertTrue(groupRepository.findByName("Trip").get().isDone());
    }

    @Test
    void should_group_beNotDone_if_userAdd_newTask_intoDoneGroup(){
        taskService.setDoneTask(taskRepository.findByDescription("shopping").get().getId());
        taskService.setDoneTask(taskRepository.findByDescription("car repair").get().getId());
        taskService.setDoneTask(taskRepository.findByDescription("buying medicine").get().getId());
        TaskDtoCreateTask task = new TaskDtoCreateTask();
        task.setDescription("earn some money");
        task.setDeadline(LocalDate.of(4444,1,1));
        task.setGroupId(groupRepository.findByName("Trip").get().getId());
        taskService.addNewTask(task);

        Assertions.assertFalse(groupRepository.findByName("Trip").get().isDone());
    }

    @AfterEach
    public void clearDb(){
        taskRepository.deleteAll();
        groupRepository.deleteAll();
    }


}


