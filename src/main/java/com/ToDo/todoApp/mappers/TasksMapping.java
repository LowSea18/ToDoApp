package com.ToDo.todoApp.mappers;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoTaskInGroup;
import com.ToDo.todoApp.model.Entity.Task;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoCreateTask;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TasksMapping {
    @Autowired
    private GroupRepository groupRepository;
    public TasksMapping(GroupRepository groupRepository){
        this.groupRepository=groupRepository;
    }

    public TaskDtoShowAllAndShowById mapTaskToTaskDtoShowAllAndShowById(Task task){
        TaskDtoShowAllAndShowById taskDtoShowAllAndShowById = new TaskDtoShowAllAndShowById();
        taskDtoShowAllAndShowById.setId(task.getId());
        taskDtoShowAllAndShowById.setDeadline(task.getDeadline());
        taskDtoShowAllAndShowById.setDone(task.isDone());
        taskDtoShowAllAndShowById.setDescription(task.getDescription());
        taskDtoShowAllAndShowById.setGroupId(task.getGroup().getId());
        return taskDtoShowAllAndShowById;
    }

    public Task mapTaskDtoCreateTaskToTask(TaskDtoCreateTask createTask){
        Task task = new Task();
        task.setDeadline(createTask.getDeadline());
        task.setDescription(createTask.getDescription());
        task.setGroup(groupRepository.findById(createTask.getGroupId()).orElseThrow(() -> new NotFoundException("Group  does not exist")));
        return task;
    }

    public TaskDtoTaskInGroup mapTaskToTaskInGroup(Task task){
        TaskDtoTaskInGroup taskDtoTaskInGroup = new TaskDtoTaskInGroup();
        taskDtoTaskInGroup.setDescription(task.getDescription());
        taskDtoTaskInGroup.setId(task.getId());
        taskDtoTaskInGroup.setDone(task.isDone());
        return taskDtoTaskInGroup;
    }

    public List<TaskDtoTaskInGroup> mapListOfTaskToListOFTasDtoTaskInGroup(List <Task> tasks){
        List<TaskDtoTaskInGroup> taskDtoTaskInGroups = new ArrayList<>();
        tasks.forEach(t -> taskDtoTaskInGroups.add (mapTaskToTaskInGroup(t)));
        return taskDtoTaskInGroups;
    }

    public List<TaskDtoShowAllAndShowById> mapListOfTaskToListOfTaskDtoShowAllAndShowById(List <Task> tasks){
        List<TaskDtoShowAllAndShowById> taskDtoShowAllAndShowByIds= new ArrayList<>();
        tasks.forEach(t->taskDtoShowAllAndShowByIds.add(mapTaskToTaskDtoShowAllAndShowById(t)));
        return taskDtoShowAllAndShowByIds;
    }


}
