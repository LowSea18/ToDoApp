package com.ToDo.todoApp.Services;

import com.ToDo.todoApp.Repositories.GroupRepository;
import com.ToDo.todoApp.exception.AlreadyExistException;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.mappers.GroupMapping;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDto;
import com.ToDo.todoApp.model.Dtos.GroupDtos.GroupDtoCreateGroup;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMapping groupMapping;

    public List<GroupDto> showAllGroups(){
        List<GroupDto> groups = new ArrayList<>();
        groupRepository.findAll().forEach(g -> groups.add(groupMapping.mapGroupToGroupDto(g)) );
        return groups;
    }
    public GroupDto showGroupById(Long id){
        GroupTasks groupTasks =groupRepository.findById(id).orElseThrow(()-> new NotFoundException("Group does not exist, id:" +id));
        return groupMapping.mapGroupToGroupDto(groupTasks);
    }

    public void createGroup (GroupDtoCreateGroup createGroup){
        Optional<GroupTasks> group = groupRepository.findByName(createGroup.getName());
        if(group.isPresent()){
            throw  new AlreadyExistException("group already exist");
        }else
            groupRepository.save(groupMapping.mapCreateToGroup(createGroup));
    }



}
