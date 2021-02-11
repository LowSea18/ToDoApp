package com.ToDo.todoApp.service;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Repositories.TaskRepository;
import com.ToDo.todoApp.Services.TaskService;
import com.ToDo.todoApp.exception.AlreadyExistException;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.exception.WrongDateException;
import com.ToDo.todoApp.mappers.TasksMapping;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoUpdateTask;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import liquibase.pro.packaged.G;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestTaskService {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private TasksMapping tasksMapping;
    @Mock
    private TaskDtoCreateTask createTask;
    @Mock
    private TaskDtoUpdateTask updateTask;
    @InjectMocks
    private TaskService taskService;

    @Test
    void get_task_by_id_should_throw_ex(){
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            taskService.showTaskById(2L);
        });
    }
    @Test
    void wrong_date_throw_ex()
    {
        TaskDtoCreateTask createTask = new TaskDtoCreateTask();
        createTask.setDeadline(LocalDate.of(1999,1,1));
        assertThrows(WrongDateException.class, () -> {
            taskService.addNewTask(createTask);
        });
    }
    @Test
    void wrong_date_in_updateTask(){
        TaskDtoUpdateTask updateTask = new TaskDtoUpdateTask();
        updateTask.setDeadline(LocalDate.now().minusDays(2));
        assertThrows(WrongDateException.class, () -> {
            taskService.updateTask(updateTask,100L);
        });
    }
    @Test
    void canNot_updateTask_Task_DoesNot_Exist(){
        TaskDtoUpdateTask updateTask = new TaskDtoUpdateTask();
        updateTask.setDeadline(LocalDate.now().plusDays(2));
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            taskService.updateTask(updateTask,anyLong());
        });
    }
    @Test
    void canNot_DeleteTask_Task_DoesNot_Exist(){
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            taskService.deleteTask(anyLong());
        });
    }
    @Test
    void canNot_SetDoneToTask_Task_DoesNot_Exist(){
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            taskService.setDoneTask(anyLong());
        });
    }
    @Test
    void canNot_SetDoneToTask_Task_DoesIsAlreadyDone(){
        Task task = new Task();
        task.setId(1L);
        task.setDone(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertThrows(AlreadyExistException.class, () -> {
            taskService.setDoneTask(1L);
        });
    }
   /* @Test
    void check_group_date(){
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        GroupTasks group = new GroupTasks();
        task1.setDeadline(LocalDate.of(2222,1,2));
        task2.setDeadline(LocalDate.of(2223,1,2));
        task3.setDeadline(LocalDate.of(2224,1,2));
        task1.setGroup(group);
        task2.setGroup(group);
        task3.setGroup(group);
        List<Task> tasks= new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        group.setTasksInGroup(tasks);

        assertEquals(task3.getDeadline(),group.getDeadline());
    }*/

}
