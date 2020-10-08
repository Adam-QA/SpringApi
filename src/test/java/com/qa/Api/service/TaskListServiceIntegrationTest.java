package com.qa.Api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.Api.Service.TaskListService;
import com.qa.Api.dto.TaskDTO;
import com.qa.Api.dto.TaskListDTO;
import com.qa.Api.persistence.domain.Task;
import com.qa.Api.persistence.domain.TaskList;
import com.qa.Api.persistence.repository.TaskListRepository;


@SpringBootTest
public class TaskListServiceIntegrationTest {
	@Autowired
	private TaskListService service;
	
	@Autowired
	private TaskListRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	
	private TaskListDTO mapTaskListToDTO(TaskList tasklist) {
		return this.modelMapper.map(tasklist, TaskListDTO.class);
	}
	
	private TaskDTO mapTaskToDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
	
	
	
	private TaskList taskListTest;
	private TaskList taskListTestWithId;
	private TaskListDTO taskListTestDTO;
	private List<TaskDTO> tasks;
	
	private Long id;
	private final String name = "Monday";
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.taskListTest = new TaskList(name);
		this.taskListTestWithId = this.repo.save(this.taskListTest);
		this.taskListTestDTO = this.mapTaskListToDTO(taskListTestWithId);
		this.id = this.taskListTestWithId.getId();
		
	}
	
	@Test
	void testCreate() {
		assertThat(this.taskListTestDTO).isEqualTo(this.service.createTaskList(taskListTest));
	}
	
	@Test
	void testRead() {

		assertThat(this.taskListTestDTO).isEqualTo(this.service.read(this.id));
	}
	
	@Test
	void testReadAll() {
		assertThat(Stream.of(this.mapTaskListToDTO(this.taskListTestWithId)).collect(Collectors.toList())).isEqualTo(this.service.readAllTaskLists());
		
	}
	
	@Test
    void testUpdate() {
		this.tasks = new ArrayList<>();
		tasks.add(this.mapTaskToDTO(new Task("Yo")));
		
		
        TaskListDTO newTaskList = new TaskListDTO(null, "Bullet", tasks);
        TaskListDTO updatedTaskList =
                new TaskListDTO(this.id, newTaskList.getName(), tasks);
        

        assertThat(updatedTaskList)
            .isEqualTo(this.service.update(newTaskList, this.id));
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.id)).isTrue();
    }

}
