package com.ToDo.todoApp.Services;

import com.ToDo.todoApp.Repositories.UserRepository;
import com.ToDo.todoApp.exception.AlreadyExistException;
import com.ToDo.todoApp.exception.NotFoundException;
import com.ToDo.todoApp.mappers.UserMapping;
import com.ToDo.todoApp.model.Dtos.UserDtos.UserDtoCreate;
import com.ToDo.todoApp.model.Dtos.UserDtos.UserDtoShow;
import com.ToDo.todoApp.model.Entity.GroupTasks;
import com.ToDo.todoApp.model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapping userMapping;

    public List<UserDtoShow> showAllUsers(){
        List<UserDtoShow> userDtoShows = new ArrayList<>();
        userRepository.findAll().forEach(t-> userDtoShows.add(userMapping.mapUserToUserDtoShow(t)));
        return userDtoShows;
    }

    public UserDtoShow showUserById(Long id){
        UserDtoShow userDtoShow = new UserDtoShow();
        userDtoShow = userMapping.mapUserToUserDtoShow(userRepository.findById(id).orElseThrow(()->new NotFoundException("User do not exist")));
        return userDtoShow;

    }

    public void registryUser(UserDtoCreate userDtoCreate){
        Optional<User> user = userRepository.findByUsername(userDtoCreate.getUsername());
        if(user.isPresent()){
            throw  new AlreadyExistException("user already exist");
        }else
            userRepository.save(userMapping.mapUserDtoCreateToUser(userDtoCreate));
    }


}
