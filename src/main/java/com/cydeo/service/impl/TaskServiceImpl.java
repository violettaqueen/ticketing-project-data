package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TaskDTO> listAllTasks() {

        List<Task> taskList = taskRepository.
    }

    @Override
    public void save(TaskDTO task) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(TaskDTO dto) {

    }

    @Override
    public TaskDTO findById(Long id) {
        return null;
    }
}
