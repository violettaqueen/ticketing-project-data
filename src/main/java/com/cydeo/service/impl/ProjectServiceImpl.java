package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    @Override
    public ProjectDTO getByProjectCode(String code) {

        Project project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        //go to repo, create a list, convert every object through stream and add it back to list
        List<Project> projectList = projectRepository.findAll(Sort.by("projectCode"));

        return projectList.stream()
                .map(projectMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
     // go to
    //projectRepository.save(projectMapper.convertToEntity(dto));
        Project project = projectMapper.convertToEntity(dto);
        project.setProjectStatus(Status.OPEN);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {

        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String code) {
      /**///logic: 1. get user, set isDeleted to true, save user
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

    }
}
