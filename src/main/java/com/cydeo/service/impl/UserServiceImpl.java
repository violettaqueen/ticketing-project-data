package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        return userMapper.convertToDTO(user);

    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);

    }

    @Override
    public UserDTO update(UserDTO user) {

        //find current user, before updating it because view doesn't know about the id
        //and will try to create a new User, and we need a specific user
        User user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(), false); //has
        // Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(user);
        //set id to the converted object
        convertedUser.setId(user1.getId());
        //save the updated user in the DB
        userRepository.save(convertedUser);

        return  findByUserName(user.getUserName());
    }

    @Override
    public void delete(String username) {
        //logic: 1. get user, set isDeleted to true, save user
        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        user.setIsDeleted(true);
        userRepository.save(user);

        if(checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        // go to DB
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role, false);
        return users.stream()
                .map(userMapper::convertToDTO) //because I convert each of them
                .collect(Collectors.toList());
    }
    private boolean checkIfUserCanBeDeleted(User user){

        // depend on user role
        switch (user.getRole().getDescription()){

            case "Manager":
                List<ProjectDTO> projectDTOList =
                        projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDTO(user));
                return projectDTOList.size() == 0;

            case "Employee":
                List<TaskDTO> taskDTOList =
                        taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDTO(user));
                return taskDTOList.size() == 0;
            default:
                return true;
        }
    }
}
