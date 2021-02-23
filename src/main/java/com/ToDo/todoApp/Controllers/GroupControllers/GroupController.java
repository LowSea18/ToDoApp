package com.ToDo.todoApp.Controllers.GroupControllers;

import com.ToDo.todoApp.Services.GroupService;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

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



}
