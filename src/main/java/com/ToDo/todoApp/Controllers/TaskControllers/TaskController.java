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
    @GetMapping("/tasks/search/done")
    public List<TaskDtoShowAllAndShowById> showDoneTasks(@RequestParam(defaultValue = "true", name = "state") boolean state) {
        return taskService.showDoneTasks(state);
    }

    @PostMapping("/tasks")
    public void addTask(@RequestBody TaskDtoCreateTask createTask){
        taskService.addNewTask(createTask);
    }
    @PutMapping("/tasks/{id}")
    public void updateTask(@PathVariable (name = "id") Long id, @RequestBody TaskDtoUpdateTask updateTask){
        taskService.updateTask(updateTask,id);
    }
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable (name = "id") Long id){
        taskService.deleteTask(id);
    }

    @PostMapping("/tasks/done/{id}")
    public void setDoneTask(@PathVariable(name = "id") Long id){
        taskService.setDoneTask(id);
    }

    //todo jak sie robi taska to sprawdzenie czy w jego grupie wszsytkie sa zrobione jak tam to zmieniasz grouptask is done

}
