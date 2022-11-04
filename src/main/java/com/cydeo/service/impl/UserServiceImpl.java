package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        return userList.stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserName(username);
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
        User user1 = userRepository.findByUserName(user.getUserName()); //has
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
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);
    }
}
