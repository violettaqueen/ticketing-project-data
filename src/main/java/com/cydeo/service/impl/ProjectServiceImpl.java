package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
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
        // added new line part 5
        project.setProjectCode(project.getProjectCode() + "-" + project.getId()); //SP00 - 1
        projectRepository.save(project);
        taskService.deleteByProject(projectMapper.convertToDTO(project));
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDTO(project));

    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        //get all project from DB assigned to user logged in from a system
        UserDTO currentUserDto = userService.findByUserName("harold@manager.com");
        //convert to entity
        User user = userMapper.convertToEntity(currentUserDto);

        List<Project> list = projectRepository.findAllByAssignedManager(user);

        return list.stream().map(project -> {

                    ProjectDTO obj = projectMapper.convertToDTO(project);

                    obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
                    obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));

                    return obj;
                }

                ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager) {

        //get from DB all projects that matches with these two conditions
        List<Project> projects =
                projectRepository.findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE, userMapper.convertToEntity(assignedManager));

        return projects.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }
}
