package com.ToDo.todoApp.Services;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.exception.WrongDateException;
import com.ToDo.todoApp.mappers.TasksMapping;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoUpdateTask;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import com.ToDo.todoApp.Repositories.TaskRepository;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TasksMapping tasksMapping;
    @Autowired
    private GroupRepository groupRepository;

    public List<TaskDtoShowAllAndShowById> showAllTasks(){
        List<TaskDtoShowAllAndShowById> taskDtoShowAllAndShowByIds = new ArrayList<>();
        taskRepository.findAll().forEach(t -> taskDtoShowAllAndShowByIds.add(tasksMapping.mapTaskToTaskDtoShowAllAndShowById(t)));
        return taskDtoShowAllAndShowByIds;
    }
    public TaskDtoShowAllAndShowById showTaskById(Long id){
        Task task =taskRepository.findById(id).orElseThrow(()->new NotFoundException("Task does not exist id:"+id));
        return tasksMapping.mapTaskToTaskDtoShowAllAndShowById(task);
    }
    public void addNewTask(TaskDtoCreateTask createTask){
        if (createTask.getDeadline().isBefore(LocalDate.now())){
            throw new WrongDateException("Wrong date!");
        }else
            taskRepository.save(tasksMapping.mapTaskDtoCreateTaskToTask(createTask));
    }
    public void updateTask(TaskDtoUpdateTask updateTask, Long id){
        if(updateTask.getDeadline().isBefore(LocalDate.now())){
            throw new WrongDateException("Wrong date!");
        }else {
            Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Can not update Task, Task does not exist"));
            task.setDeadline(updateTask.getDeadline());
            task.setDescription(updateTask.getDescription());
            task.setDone(updateTask.isDone());
            taskRepository.save(task);
        }
    }
    public void deleteTask(Long id){
       Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task does not exist, id" +id));
        taskRepository.delete(task);
    }

    public void addTaskToGroup(Long taskId,Long groupId){
       Task task = taskRepository.findById(taskId).orElseThrow(()-> new NotFoundException("Task does not exist, id:" + taskId));
      GroupTasks groupTasks = groupRepository.findById(groupId).orElseThrow(()-> new NotFoundException("Group does not exist, id:" + groupId));
      task.setGroup(groupTasks);
      taskRepository.save(task);
    }

}