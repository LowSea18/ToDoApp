package com.ToDo.todoApp.Services;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.exception.AlreadyExistException;
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
import org.springframework.cache.support.NullValue;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TasksMapping tasksMapping;
    @Autowired
    private GroupRepository groupRepository;

    public TaskService(TaskRepository taskRepository, TasksMapping tasksMapping, GroupRepository groupRepository){
        this.taskRepository =taskRepository;
        this.tasksMapping=tasksMapping;
        this.groupRepository=groupRepository;
    }

    public List<TaskDtoShowAllAndShowById> showAllTasks(){
        List<TaskDtoShowAllAndShowById> taskDtoShowAllAndShowByIds = new ArrayList<>();
        taskRepository.findAll().forEach(t -> taskDtoShowAllAndShowByIds.add(tasksMapping.mapTaskToTaskDtoShowAllAndShowById(t)));
        return taskDtoShowAllAndShowByIds;
    }
    public TaskDtoShowAllAndShowById showTaskById(Long id){
        Task task =taskRepository.findById(id).orElseThrow(()->new NotFoundException("Task does not exist id:"+id));
        return tasksMapping.mapTaskToTaskDtoShowAllAndShowById(task);
    }

    public List<TaskDtoShowAllAndShowById> showDoneTasks(boolean state){
        if(state){
        List<Task> doneTasks = taskRepository.findAll().stream().filter(Task::isDone).collect(Collectors.toList());
            return tasksMapping.mapListOfTaskToListOfTaskDtoShowAllAndShowById(doneTasks);
        }else{
        List<Task> doneTasks = taskRepository.findAll().stream().filter(task -> !task.isDone()).collect(Collectors.toList());
            return tasksMapping.mapListOfTaskToListOfTaskDtoShowAllAndShowById(doneTasks);
        }
    }
    public void addNewTask(TaskDtoCreateTask createTask){
        if (createTask.getDeadline().isBefore(LocalDate.now())){
            throw new WrongDateException("Wrong date!");
        }else {

            taskRepository.save(tasksMapping.mapTaskDtoCreateTaskToTask(createTask));
            GroupTasks groupTasks = groupRepository.findById(createTask.getGroupId()).orElseThrow();
            groupTasks.setDone(false);
            if (groupTasks.getDeadline()==null) {
                groupTasks.setDeadline(createTask.getDeadline());
            } else {
                groupTasks.getTasksInGroup().stream().map(Task::getDeadline).max(LocalDate::compareTo).ifPresent(groupTasks::setDeadline);

            }
            groupRepository.save(groupTasks);
        }

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
            GroupTasks groupTasks = task.getGroup();
            List<Task> tasks = groupTasks.getTasksInGroup().stream().filter(t -> !task.isDone()).collect(Collectors.toList());
            if(tasks.isEmpty()){
                groupTasks.setDone(true);
                groupRepository.save(groupTasks);
            }else
                groupTasks.setDone(false);
                groupRepository.save(groupTasks);
        }
    }
    public void deleteTask(Long id){
       Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task does not exist, id" +id));
        GroupTasks groupTasks = groupRepository.findById(task.getGroup().getId()).orElseThrow();
       taskRepository.delete(task);
       if(groupTasks.getTasksInGroup().isEmpty()){
            groupTasks.setDeadline(null);
        }else{
           groupTasks.getTasksInGroup().stream().map(Task::getDeadline).max(LocalDate::compareTo).ifPresent(groupTasks::setDeadline);
       }
       groupRepository.save(groupTasks);
    }

    public void setDoneTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task does not exist"));
        if(task.isDone()){
            throw new AlreadyExistException("Task is already done!");
        }else{
            task.setDone(true);
            taskRepository.save(task);
            GroupTasks groupTasks = task.getGroup();
            List<Task> tasks = groupTasks.getTasksInGroup().stream().filter(t -> !task.isDone()).collect(Collectors.toList());
            if(tasks.isEmpty()){
                groupTasks.setDone(true);
                groupRepository.save(groupTasks);
            }

        }
    }
    public List<TaskDtoShowAllAndShowById> showTaskByGroup(Long groupId){
        List<TaskDtoShowAllAndShowById> taskDtoShowAllAndShowByIdss = new ArrayList<>();
        taskDtoShowAllAndShowByIdss = tasksMapping.mapListOfTaskToListOfTaskDtoShowAllAndShowById(taskRepository.findAll().stream().filter(t-> t.getGroup().getId().equals(groupId)).collect(Collectors.toList()));
        return taskDtoShowAllAndShowByIdss;
    }

    public List<TaskDtoShowAllAndShowById> showByDeadLine(){
        List<TaskDtoShowAllAndShowById> list = new ArrayList<>();
        list= tasksMapping.mapListOfTaskToListOfTaskDtoShowAllAndShowById(taskRepository.findAll().stream().filter(task -> !task.isDone()).sorted(Comparator.comparing(Task::getDeadline)).collect(Collectors.toList()));
        return list;
    }



}
