package com.qa.Api.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.qa.Api.dto.TaskDTO;

import com.qa.Api.exception.TaskNotFoundException;

import com.qa.Api.persistence.domain.Task;
import com.qa.Api.persistence.repository.TaskRepository;
import com.qa.Api.utils.ApiBeanUtils;

@Service
public class TaskService {
	
	private TaskRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TaskService(TaskRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}
	
	private Task mapFromDTO(TaskDTO taskDTO) {
		return this.mapper.map(taskDTO, Task.class);
	}
	
	
	public TaskDTO createTask(Task task) {
		Task saved = this.repo.save(task);
		return this.mapToDTO(saved);
	}
	
	public List<TaskDTO> readAllTasks(){
		return this.repo.findAll()
				.stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
	}
	
	public TaskDTO read(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(TaskNotFoundException :: new));
		
	}
	
	public TaskDTO update(TaskDTO taskDTO, Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow(TaskNotFoundException :: new);
		ApiBeanUtils.mergeNotNull(taskDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
		
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	

}
