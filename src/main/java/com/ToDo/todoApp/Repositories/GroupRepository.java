package com.ToDo.todoApp.Repositories;

import com.ToDo.todoApp.model.Entity.GroupTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupTasks,Long> {
    Optional<GroupTasks> findByName(String name);
}
