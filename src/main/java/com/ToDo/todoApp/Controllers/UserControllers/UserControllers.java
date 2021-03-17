package com.ToDo.todoApp.Controllers.UserControllers;

import com.ToDo.todoApp.Services.UserService;
import com.ToDo.todoApp.model.Dtos.UserDtos.UserDtoCreate;
import com.ToDo.todoApp.model.Dtos.UserDtos.UserDtoShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllers {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<UserDtoShow> showAllUsers(){
        return userService.showAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDtoShow showAllUsersById(@PathVariable Long id){
        return userService.showUserById(id);
    }

    @PostMapping("/users")
    public void registryUser(@RequestBody UserDtoCreate userDtoCreate){
        userService.registryUser(userDtoCreate);
    }

}
