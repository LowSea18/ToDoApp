package com.ToDo.todoApp.Repositories;

import com.ToDo.todoApp.model.Entity.GroupTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupTasks,Long> {
}
