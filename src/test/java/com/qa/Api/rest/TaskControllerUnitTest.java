package com.qa.Api.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.qa.Api.dto.TaskDTO;

import com.qa.Api.persistence.domain.Task;
import com.qa.Api.rest.TaskController;
import com.qa.Api.Service.TaskService;

@SpringBootTest
public class TaskControllerUnitTest {
	@Autowired
	private TaskController controller;
	
	@MockBean
	private TaskService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private TaskDTO mapToDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
	
	private List<Task> ListOfTasks;
	private Task testTask;
	private Task testTaskWithId;
	private TaskDTO taskDTO;
	
	private final String toDo = "Need to do the gardening today!";
    private final Long id = 1L;
    
    
    @BeforeEach
    void init() {
    	this.ListOfTasks = new ArrayList<>();
    	this.testTask = new Task(toDo);
    	this.testTaskWithId = new Task(testTask.getToDo());
    	this.testTaskWithId.setId(id);
    	this.ListOfTasks.add(testTaskWithId);
    	this.taskDTO = this.mapToDTO(testTaskWithId);
    	
    }
    
    @Test
    void createTest() {
    	when(this.service.createTask(testTask)).thenReturn(this.taskDTO);
    	TaskDTO testCreated = this.taskDTO;
    	assertThat(new ResponseEntity<TaskDTO>(testCreated, HttpStatus.CREATED)).isEqualTo(this.controller.create(testTask));
    	verify(this.service, times(1)).createTask(this.testTask);
    }
    
    
    @Test
    void readTest() {
        when(this.service.read(this.id)).thenReturn(this.taskDTO);

        TaskDTO testReadOne = this.taskDTO;
        assertThat(new ResponseEntity<TaskDTO>(testReadOne, HttpStatus.OK))
                .isEqualTo(this.controller.getTaskById(this.id));

        verify(this.service, times(1)).read(this.id);
    }

    
    @Test
    void readAllTest() {
        when(this.service.readAllTasks())
                .thenReturn(this.ListOfTasks.stream().map(this::mapToDTO).collect(Collectors.toList()));
        assertThat(this.controller.getAllTasks().getBody().isEmpty()).isFalse();

        verify(this.service, times(1)).readAllTasks();
    }

    
    @Test
    void updateTest() {
        
        TaskDTO newTask = new TaskDTO(null, "Go shopping");
        TaskDTO newTaskWithId = new TaskDTO(this.id, newTask.getToDo());
        when(this.service.update(newTask, this.id)).thenReturn(newTaskWithId);

        assertThat(new ResponseEntity<TaskDTO>(newTaskWithId, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.updateTaskById(this.id, newTask));

        verify(this.service, times(1)).update(newTask, this.id);
    }

    
    @Test
    void deleteTest() {
        this.controller.deleteTaskById(id); 

        verify(this.service, times(1)).delete(id);
    }

}
