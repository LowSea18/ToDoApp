package com.ToDo.todoApp.Repositories;

import com.ToDo.todoApp.model.Entity.Task;
import com.ToDo.todoApp.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
