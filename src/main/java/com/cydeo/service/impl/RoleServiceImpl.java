package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        // sql impl
        List<Role> roleList = roleRepository.findAll();
        //looking for DTO, we need to convert Entity to DTO
        return roleList;
    }

    @Override
    public RoleDTO findAllBy(Long id) {
        return null;
    }
}
