package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();  //DTO object in Service

    UserDTO findByUserName(String username); //we need this method to update user

    void save(UserDTO user);
    void deleteByUserName(String username);
    UserDTO update (UserDTO user);

}
