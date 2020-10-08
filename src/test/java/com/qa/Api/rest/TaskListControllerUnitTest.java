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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.Api.Service.TaskListService;
import com.qa.Api.dto.TaskDTO;
import com.qa.Api.dto.TaskListDTO;
import com.qa.Api.persistence.domain.TaskList;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskListControllerUnitTest {
	@Autowired
	private TaskListController controller;
	
	@MockBean
	private TaskListService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private TaskListDTO mapToDTO(TaskList tasklist) {
		return this.modelMapper.map(tasklist, TaskListDTO.class);
	}
	
	private List<TaskList> taskList;
	private TaskListDTO taskListDTO;
	private TaskList taskListTest;
	private TaskList taskListTestWithId;
	private List<TaskDTO> tasks;
	
	
	private final String name = "Do something";
	private final Long id = 1L;
	
	@BeforeEach
	void init() {
		this.taskList = new ArrayList<>();
		this.taskListTest = new TaskList(name);
		this.taskListTestWithId = new TaskList(taskListTest.getName());
		this.taskListTestWithId.setId(id);
		this.taskList.add(taskListTestWithId);
		this.taskListDTO = this.mapToDTO(taskListTestWithId);
		
	}
	
	@Test
    void createTest() {
    	when(this.service.createTaskList(taskListTest)).thenReturn(this.taskListDTO);
    	TaskListDTO testCreated = this.taskListDTO;
    	assertThat(new ResponseEntity<TaskListDTO>(testCreated, HttpStatus.CREATED)).isEqualTo(this.controller.create(taskListTest));
    	verify(this.service, times(1)).createTaskList(this.taskListTest);
    }
	
	@Test
    void readTest() {
        when(this.service.read(this.id)).thenReturn(this.taskListDTO);

        TaskListDTO testReadSingle = this.taskListDTO;
        assertThat(new ResponseEntity<TaskListDTO>(testReadSingle, HttpStatus.OK))
                .isEqualTo(this.controller.getTaskListById(this.id));

        verify(this.service, times(1)).read(this.id);
    }
	
	@Test
    void readAllTest() {
        when(this.service.readAllTaskLists())
                .thenReturn(this.taskList.stream().map(this::mapToDTO).collect(Collectors.toList()));
        assertThat(this.controller.getAllTaskLists().getBody().isEmpty()).isFalse();

        verify(this.service, times(1)).readAllTaskLists();
    }
	
	
	@Test
    void updateTest() {
		this.tasks = new ArrayList<>();
        
        TaskListDTO newTaskList = new TaskListDTO(null, "Rock Legends", tasks);
        TaskListDTO newTaskListWithId = new TaskListDTO(this.id, newTaskList.getName(), newTaskList.getTasks());
        when(this.service.update(newTaskList, this.id)).thenReturn(newTaskListWithId);
        assertThat(new ResponseEntity<TaskListDTO>(newTaskListWithId, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.updateTaskListById(this.id, newTaskList));

        verify(this.service, times(1)).update(newTaskList, this.id);
    }
	
	@Test
    void deleteTest() {
        this.controller.deleteTaskListById(id); 

        verify(this.service, times(1)).delete(id);
    }

}
