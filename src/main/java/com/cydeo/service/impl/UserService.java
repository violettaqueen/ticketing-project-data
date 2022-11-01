package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username); //we need this method in update method

    void save(UserDTO user);
    void deleteByUserName(String username);

}
