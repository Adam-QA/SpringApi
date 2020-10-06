package com.qa.Api.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.qa.Api.dto.TaskListDTO;

import com.qa.Api.exception.TaskListNotFoundException;

import com.qa.Api.persistence.domain.TaskList;

import com.qa.Api.persistence.repository.TaskListRepository;
import com.qa.Api.utils.ApiBeanUtils;


@Service
public class TaskListService {
private TaskListRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TaskListService(TaskListRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TaskListDTO mapToDTO(TaskList tasklist) {
		return this.mapper.map(tasklist, TaskListDTO.class);
	}
	
	private TaskList mapFromDTO(TaskListDTO tasklistDTO) {
		return this.mapper.map(tasklistDTO, TaskList.class);
	}
	
	
	public TaskListDTO createTaskList(TaskList tasklist) {
//		Band toSave = this.mapFromDTO(bandDTO);
		TaskList saved = this.repo.save(tasklist);
		return this.mapToDTO(saved);
	}
	
	public List<TaskListDTO> readAllTaskLists() {    
		List<TaskList> found = this.repo.findAll();   
		List<TaskListDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());   
		return streamed;
	}
	
	public TaskListDTO read(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(TaskListNotFoundException :: new));
		
	}
	
	public TaskListDTO update(TaskListDTO tasklistDTO, Long id) {
		TaskList toUpdate = this.repo.findById(id).orElseThrow(TaskListNotFoundException :: new);
		ApiBeanUtils.mergeNotNull(tasklistDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
		
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}

}
