package com.ToDo.todoApp.Controllers.GroupControllers;

import com.ToDo.todoApp.Services.GroupService;
import com.ToDo.todoApp.Services.TaskService;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Dtos.TaskDtos.TaskDtoShowAllAndShowById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/groups")
    public List<GroupDto> showAllGroups(){
        return groupService.showAllGroups();
    }
    @GetMapping("/groups/{id}")
    public GroupDto showGroupById(@PathVariable (name = "id") Long id){
        return groupService.showGroupById(id);
    }
    @PostMapping("/groups")
    public void createGroup(@RequestBody GroupDtoCreateGroup createGroup){
        groupService.createGroup(createGroup);
    }
    @DeleteMapping("/groups/{id}")
    public void deleteGroup(@PathVariable(name = "id") Long id){ groupService.deleteGroup(id); }
    @GetMapping("/groups/{id}/tasks")
    public List<TaskDtoShowAllAndShowById> showTasksByGroup(@PathVariable(name = "groupID") Long groupId){
        return taskService.showTaskByGroup(groupId);
    }


}
