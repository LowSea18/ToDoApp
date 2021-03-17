package com.ToDo.todoApp.model.Dtos.UserDtos;

import com.ToDo.todoApp.model.Roles.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoCreate {
    private String username;
    private String password;
    private Roles role;
}
