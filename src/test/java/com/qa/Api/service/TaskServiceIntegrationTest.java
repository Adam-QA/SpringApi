package com.qa.Api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.Api.Service.TaskService;
import com.qa.Api.dto.TaskDTO;
import com.qa.Api.persistence.domain.Task;
import com.qa.Api.persistence.repository.TaskRepository;


@SpringBootTest
public class TaskServiceIntegrationTest {
	 @Autowired
	    private TaskService service;

	    @Autowired
	    private TaskRepository repo;

	    @Autowired
	    private ModelMapper modelMapper;

	    private TaskDTO mapToDTO(Task task) {
	        return this.modelMapper.map(task, TaskDTO.class);
	    }

	    // there's no objectMapper this time
	    // because we don't need to convert any returned objects to JSON
	    // that's a controller job, and we're not testing the controller! :D

	    private Task testTask;
	    private Task testTaskWithId;
	    private TaskDTO testTaskDTO;

	    private Long id;
	    private final String toDo = "Not much";
	    
	    @BeforeEach
	    void init() {
	        this.repo.deleteAll();
	        this.testTask = new Task(toDo);
	        this.testTaskWithId = this.repo.save(this.testTask);
	        this.testTaskDTO = this.mapToDTO(testTaskWithId);
	        this.id = this.testTaskWithId.getId();
	    }

	    @Test
	    void testCreate() {
	        assertThat(this.testTaskDTO)
	            .isEqualTo(this.service.createTask(testTask));
	    }

	    @Test
	    void testRead() {
	        assertThat(this.testTaskDTO)
	                .isEqualTo(this.service.read(this.id));
	    }

	    @Test
	    void testReadAll() {
	        // check this one out with a breakpoint and running it in debug mode
	        // so you can see the stream happening
	        assertThat(this.service.readAllTasks())
	                .isEqualTo(Stream.of(this.testTaskDTO)
	                        .collect(Collectors.toList()));
	    }

	    @Test
	    void testUpdate() {
	        TaskDTO newTask = new TaskDTO(null, "Nothing");
	        TaskDTO updatedTask =
	                new TaskDTO(this.id, newTask.getToDo());

	        assertThat(updatedTask)
	            .isEqualTo(this.service.update(newTask, this.id));
	    }

	    @Test
	    void testDelete() {
	        assertThat(this.service.delete(this.id)).isTrue();
	    }

}
