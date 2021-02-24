package com.ToDo.todoApp.service;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Repositories.TaskRepository;
import com.ToDo.todoApp.Services.GroupService;
import com.ToDo.todoApp.Services.TaskService;
import com.ToDo.todoApp.exception.AlreadyExistException;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.exception.WrongDateException;
import com.ToDo.todoApp.mappers.GroupMapping;
import com.ToDo.todoApp.mappers.TasksMapping;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoUpdateTask;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import liquibase.pro.packaged.G;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TestTaskService {


    @Test
    void should_notFoundTaskById_throw_NotFoundEx() {
        var mockTaskRepo = mock(TaskRepository.class);
        when(mockTaskRepo.findById(anyLong())).thenReturn(Optional.empty());
        TaskService taskService = new TaskService(mockTaskRepo, null, null);
        assertThrows(NotFoundException.class, () -> {
            taskService.showTaskById(anyLong());
        });
    }

    @Test
    void should_notAddTask_with_wrong_date_throw_WrongDateEx() {
        TaskService taskService = new TaskService(null, null, null);
        TaskDtoCreateTask createTask = new TaskDtoCreateTask();
        createTask.setDeadline(LocalDate.of(1999, 1, 1));
        assertThrows(WrongDateException.class, () -> {
            taskService.addNewTask(createTask);
        });
    }

    @Test
    void should_updateTask_with_wrong_date_throw_WrongDateEx() {
        TaskDtoUpdateTask updateTask = new TaskDtoUpdateTask();
        updateTask.setDeadline(LocalDate.now().minusDays(2));
        TaskService taskService = new TaskService();
        assertThrows(WrongDateException.class, () -> {
            taskService.updateTask(updateTask, 3L);
        });
    }

    @Test
    void should_notFoundTaskToUpdate_throw_NotFoundEx() {
        TaskDtoUpdateTask updateTask = new TaskDtoUpdateTask();
        updateTask.setDeadline(LocalDate.now().plusDays(2));
        var mocTaskRepository = mock(TaskRepository.class);
        TaskService taskService = new TaskService(mocTaskRepository, null, null);
        when(mocTaskRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            taskService.updateTask(updateTask, anyLong());
        });
    }

    @Test
    void should_notFoundTaskToDelete_throw_NotFoundEx() {
        var mocTaskRepository = mock(TaskRepository.class);
        when(mocTaskRepository.findById(anyLong())).thenReturn(Optional.empty());
        TaskService taskService = new TaskService(mocTaskRepository, null, null);
        assertThrows(NotFoundException.class, () -> {
            taskService.deleteTask(anyLong());
        });
    }

    @Test
    void should_notFoundTaskToSetDone_throw_NotFoundEx() {
        var mocTaskRepository = mock(TaskRepository.class);
        when(mocTaskRepository.findById(anyLong())).thenReturn(Optional.empty());
        TaskService taskService = new TaskService(mocTaskRepository, null, null);
        assertThrows(NotFoundException.class, () -> {
            taskService.setDoneTask(anyLong());
        });
    }

    @Test
    void should_notSetDoneTOTask_taskAlreadyDone_throw_AlreadyExistEx() throws AlreadyExistException {
        Task task = new Task();
        task.setId(1L);
        task.setDone(true);
        var mocTaskRepository = mock(TaskRepository.class);
        TaskService taskService = new TaskService(mocTaskRepository, null, null);

        when(mocTaskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertThrows(AlreadyExistException.class, () -> {
            taskService.setDoneTask(1L);
        });
    }



}
