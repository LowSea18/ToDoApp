package com.ToDo.todoApp.service;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.Services.GroupService;
import com.ToDo.todoApp.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestGroupService {
    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void canNot_show_group_groupDoesNotExist(){
        when(groupRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            groupService.showGroupById(2L);
        });
    }
}
