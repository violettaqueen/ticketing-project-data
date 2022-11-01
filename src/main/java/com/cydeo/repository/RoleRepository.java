package com.cydeo.repository;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<RoleDTO> findByDescription(String description);

}
