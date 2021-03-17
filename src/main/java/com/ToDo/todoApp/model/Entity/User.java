package com.ToDo.todoApp.model.Entity;


import com.ToDo.todoApp.model.Roles.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Task> tasks;
    private Roles role;
}
