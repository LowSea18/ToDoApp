package com.ToDo.todoApp.model.Dtos.UserDtos;

import com.ToDo.todoApp.model.Roles.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoShow {
    private String username;
    private Roles role;
}
