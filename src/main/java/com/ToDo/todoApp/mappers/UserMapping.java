package com.ToDo.todoApp.mappers;

import com.ToDo.todoApp.model.Dtos.UserDtos.UserDtoCreate;
import com.ToDo.todoApp.model.Dtos.UserDtos.UserDtoShow;
import com.ToDo.todoApp.model.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    public UserDtoShow mapUserToUserDtoShow(User user){
        UserDtoShow userDtoShow = new UserDtoShow();
        userDtoShow.setUsername(user.getUsername());
        userDtoShow.setRole(user.getRole());
        return userDtoShow;
    }

    public User mapUserDtoCreateToUser(UserDtoCreate userDtoCreate) {
        User user = new User();
        user.setPassword(userDtoCreate.getPassword());
        user.setUsername(userDtoCreate.getUsername());
        user.setRole(user.getRole());
        return user;
    }
}
