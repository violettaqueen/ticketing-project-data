package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAll(Sort.by("firstName"));

        return null;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return null;
    }

    @Override
    public void save(UserDTO user) {

    }

    @Override
    public void deleteByUserName(String username) {

    }
}
