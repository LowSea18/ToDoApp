package com.ToDo.todoApp.mappers;

import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class GroupMapping {
    @Autowired
   private TasksMapping tasksMapping;

    public GroupDto mapGroupToGroupDto (GroupTasks groupTasks){
        GroupDto groupDto= new GroupDto();
        groupDto.setId(groupTasks.getId());
        groupDto.setName(groupTasks.getName());
        groupDto.setDone(groupTasks.isDone());
        groupDto.setDeadline(groupTasks.getDeadline());
        groupDto.setTasksInGroup(tasksMapping.mapListOfTaskToListOFTasDtoTaskInGroup(groupTasks.getTasksInGroup()));
        return groupDto;
    }

    public GroupTasks mapCreateToGroup(GroupDtoCreateGroup createGroup){
        GroupTasks groupTasks = new GroupTasks();
        groupTasks.setName(createGroup.getName());
        return groupTasks;
    }

}
