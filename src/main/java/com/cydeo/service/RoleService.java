package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    // bring roles (List of roles DTO) to UI
    List<RoleDTO> listAllRoles();
    RoleDTO findAllBy(Long id);
}
