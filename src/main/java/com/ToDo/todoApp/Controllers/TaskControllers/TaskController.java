package com.ToDo.todoApp.Controllers.TaskControllers;

import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import com.ToDo.todoApp.Services.TaskService;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoUpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskDtoShowAllAndShowById> showAllTasks(){
        return taskService.showAllTasks();
    }
    @GetMapping("/tasks/{id}")
    public TaskDtoShowAllAndShowById showTaskById(@PathVariable (name = "id") Long id){
        return  taskService.showTaskById(id);
    }
    @PostMapping("/tasks")
    public void addTask(@RequestBody TaskDtoCreateTask createTask){
        taskService.addNewTask(createTask);
    }
    @PutMapping("/tasks/{id}")
    public void updateTask(@PathVariable (name = "id") Long id, @RequestBody TaskDtoUpdateTask updateTask){
        taskService.updateTask(updateTask,id);
    }
    @PostMapping("/task/{task_id}/group/{group_id}")
    public void addTaskToGroup (@PathVariable (name = "task_id") Long taskId, @PathVariable (name = "group_id") Long groupId){
        taskService.addTaskToGroup(taskId,groupId);
    }

}
