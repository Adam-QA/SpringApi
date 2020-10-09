package com.qa.Api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.Api.Service.TaskListService;
import com.qa.Api.dto.TaskDTO;
import com.qa.Api.dto.TaskListDTO;
import com.qa.Api.persistence.domain.TaskList;
import com.qa.Api.persistence.repository.TaskListRepository;


@SpringBootTest
public class TaskListServiceUnitTest {
	@Autowired
	private TaskListService service;
	
	@MockBean
	private TaskListRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private List<TaskList> taskList;
	private TaskList taskListTest;
	private TaskList taskListTestWithId;
	private TaskListDTO taskListDTO;
	private List<TaskDTO> tasks;
	
	private Long id;
	
	
	@BeforeEach
	void init() {
		this.taskList = new ArrayList<>();
		this.taskListTest = new TaskList("Welcome");
		this.taskList.add(taskListTest);
		this.taskListTestWithId = new TaskList(taskListTest.getName());
		this.taskListTestWithId.setId(this.id);
		this.taskListDTO = modelMapper.map(taskListTestWithId, TaskListDTO.class);
	}
	
	@Test
    void createTest() {
        when(this.repo.save(this.taskListTest)).thenReturn(this.taskListTestWithId);
        when(this.modelMapper.map(this.taskListTestWithId, TaskListDTO.class)).thenReturn(this.taskListDTO);
        TaskListDTO expected = this.taskListDTO;
        TaskListDTO actual = this.service.createTaskList(this.taskListTest);
        assertThat(expected).isEqualTo(actual);      
        verify(this.repo, times(1)).save(this.taskListTest);
    }
	
	@Test
	void readTest() {	        
	    when(this.repo.findById(this.id)).thenReturn(Optional.of(this.taskListTestWithId));
	
	    when(this.modelMapper.map(taskListTestWithId, TaskListDTO.class)).thenReturn(taskListDTO);
	
	    assertThat(this.taskListDTO).isEqualTo(this.service.read(this.id));
	
	    verify(this.repo, times(1)).findById(this.id);
	}
	
	@Test
    void readAllTest() {
 
        when(this.repo.findAll()).thenReturn(this.taskList);

        when(this.modelMapper.map(this.taskListTestWithId, TaskListDTO.class)).thenReturn(taskListDTO);

        assertThat(this.service.readAllTaskLists().isEmpty()).isFalse();

        verify(this.repo, times(1)).findAll();
    }
	
	@Test
    void updateTest() {
		List<TaskDTO> tasks = new ArrayList<>();
        TaskList tasklist = new TaskList("DO this");
        tasklist.setId(this.id);

        TaskListDTO taskListDTO = new TaskListDTO(null, "DO this", tasks);

        TaskList updatedTaskList = new TaskList(taskListDTO.getName());
        updatedTaskList.setId(this.id);

        TaskListDTO updatedTaskListDTO = new TaskListDTO(this.id, updatedTaskList.getName(), tasks);
              
        when(this.repo.findById(this.id)).thenReturn(Optional.of(tasklist));

        
        when(this.repo.save(tasklist)).thenReturn(updatedTaskList);

        when(this.modelMapper.map(updatedTaskList, TaskListDTO.class)).thenReturn(updatedTaskListDTO);

        assertThat(updatedTaskListDTO).isEqualTo(this.service.update(taskListDTO, this.id));

        verify(this.repo, times(1)).findById(1L);
        verify(this.repo, times(1)).save(updatedTaskList);
    }
	
	
	@Test
    void deleteTest() {
        
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        
        verify(this.repo, atLeastOnce()).deleteById(id);

        
        verify(this.repo, atLeastOnce()).existsById(id);
    }

}
