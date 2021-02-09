package com.ToDo.todoApp.mappers;

import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GroupMapping {
    @Autowired
    TasksMapping tasksMapping;

    public GroupDto mapGroupToGroupDto (GroupTasks groupTasks){
        GroupDto groupDto= new GroupDto();
        groupDto.setId(groupTasks.getId());
        groupDto.setName(groupTasks.getName());
        groupTasks.getTasksInGroup().stream().map(Task::getDeadline).max(LocalDate::compareTo).ifPresent(groupDto::setDeadline);
        int a =0;
       // () -> groupTasks.getTasksInGroup().forEach(t -> t.isDone())
        groupDto.setTasksInGroup(tasksMapping.mapListOfTaskToListOFTasDtoTaskInGroup(groupTasks.getTasksInGroup()));
        return groupDto;
    }

}
