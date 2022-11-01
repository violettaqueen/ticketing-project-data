package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {   //SOLID PRINCIPLES

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

    }
   public Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto,Role.class);
    }
    public RoleDTO convertToDTO(Role entity){
        return modelMapper.map(entity,RoleDTO.class);
    }
}
