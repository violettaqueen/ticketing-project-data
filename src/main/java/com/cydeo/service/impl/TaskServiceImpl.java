package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserService userService, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Override
    public List<TaskDTO> listAllTasks() {

        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {

        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);

    }

    @Override
    public void delete(Long id) {

        Optional<Task> foundTask = taskRepository.findById(id);
        if(foundTask.isPresent()){
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }
    }

    @Override
    public void update(TaskDTO dto) {

        Optional<Task> task = taskRepository.findById(dto.getId());

        Task convertedTask = taskMapper.convertToEntity(dto); // because I want to save a new info

        if (task.isPresent()){
            convertedTask.setTaskStatus(task.get().getTaskStatus());  //use the existing status
            convertedTask.setTaskStatus(dto.getTaskStatus() == null ? task.get().getTaskStatus() : dto.getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }

    @Override
    public TaskDTO findById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        return task.map(taskMapper::convertToDto).orElse(null);
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {

        //convert dto to entity
        Project project = projectMapper.convertToEntity(projectDTO);
        //go to Project DB, get a list of tasks
        List<Task> tasks = taskRepository.findAllByProject(project);
        //delete each task by its' id
        tasks.forEach(task -> delete(task.getId()));

    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        // convert dto to entity
        Project project = projectMapper.convertToEntity(projectDTO);
        // find all task by project
        List<Task> tasks = taskRepository.findAllByProject(project);
        tasks.stream().map(taskMapper::convertToDto).forEach(taskDTO -> {taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);

        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {

        UserDTO loggedInUser = userService.findByUserName("john@employee.com");

        List<Task> tasks =
                taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));

        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {

        UserDTO loggedInUser = userService.findByUserName("john@employee.com");

        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));

        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO userDTO) {

        List<Task> tasks =
                taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE, userMapper.convertToEntity(userDTO));

        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}