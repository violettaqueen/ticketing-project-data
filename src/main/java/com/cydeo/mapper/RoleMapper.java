package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.service.RoleService;
import org.modelmapper.ModelMapper;


public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper, RoleService roleService) {
        this.modelMapper = modelMapper;

    }
    Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto,Role.class);
    }
    RoleDTO convertToDTO(Role entity){
        return modelMapper.map(entity,RoleDTO.class);
    }
}
