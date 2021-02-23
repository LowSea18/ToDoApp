package com.ToDo.todoApp.service;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Services.GroupService;
import com.ToDo.todoApp.exception.AlreadyExistException;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.Task;
import liquibase.pro.packaged.G;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TestGroupService {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    GroupRepository groupRepository;



    @Test
    void should_notFoundGroupToShow_throw_NotFoundEx() {
        var mockGroupRepo = mock(GroupRepository.class);
        when(mockGroupRepo.findById(anyLong())).thenReturn(Optional.empty());
        var toTest = new GroupService(mockGroupRepo,null);
        assertThrows(NotFoundException.class, () -> {
            toTest.showGroupById(2L);
        });
    }
    @Test
    void should_notCreateGroup_GroupAlreadyExist_throw_AlreadyExistEx(){
        var mockGroupRepo = mock(GroupRepository.class);
        GroupTasks groupTasks = new GroupTasks();
        when(mockGroupRepo.findByName(anyString())).thenReturn(Optional.of(groupTasks));
        var toTest = new GroupService(mockGroupRepo,null);
        GroupDtoCreateGroup createGroup = new GroupDtoCreateGroup();
        createGroup.setName("lala");
        assertThrows(AlreadyExistException.class, () -> {
            toTest.createGroup(createGroup);
        });
    }
    @Test
    void should_notDeleteGroup_GroupNotExist_throw_NotFoundEx(){
        var mockGroupRepo = mock(GroupRepository.class);
        when(mockGroupRepo.findById(anyLong())).thenReturn(Optional.empty());
        var toTest = new GroupService(mockGroupRepo,null);
        assertThrows(NotFoundException.class, () -> {
            toTest.deleteGroup(anyLong());
        });
    }
    @Test
    void should_notDeleteGroup_GroupHaveTasks_throw_AlreadyEx(){
        var mockGroupRepo = mock(GroupRepository.class);
        GroupTasks groupTasks = new GroupTasks();
        Task task = new Task();
        groupTasks.setTasksInGroup(List.of(task));
        when(mockGroupRepo.findById(anyLong())).thenReturn(Optional.of(groupTasks));
        var toTest = new GroupService(mockGroupRepo,null);
        assertThrows(AlreadyExistException.class, () -> {
            toTest.deleteGroup(anyLong());
        });

    }


}