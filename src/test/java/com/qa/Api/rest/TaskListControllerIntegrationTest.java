package com.qa.Api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Api.dto.TaskListDTO;
import com.qa.Api.persistence.domain.TaskList;
import com.qa.Api.persistence.repository.TaskListRepository;



@SpringBootTest
@AutoConfigureMockMvc
public class TaskListControllerIntegrationTest {
	@Autowired
	private MockMvc mock;
	@Autowired
	private TaskListRepository repo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long id;
	private TaskList taskListTest;
	private TaskList taskListTestWithId;
	
	private TaskListDTO mapToDTO(TaskList tasklist) {
		return this.modelMapper.map(tasklist, TaskListDTO.class);
	}
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.taskListTest = new TaskList("Monday");
		this.taskListTestWithId = this.repo.save(this.taskListTest);
		this.id = this.taskListTestWithId.getId();
	}
	
	@Test
    void testCreate() throws Exception {
        this.mock
            .perform(request(HttpMethod.POST, "/tasklist/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(taskListTest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(this.objectMapper.writeValueAsString(taskListTestWithId)));
    }
	@Test
    void testRead() throws Exception {
        this.mock
            .perform(request(HttpMethod.GET, "/tasklist/read/" + this.id)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(this.objectMapper.writeValueAsString(this.taskListTest)));
    }
	
	@Test
    void testReadAll() throws Exception {
        List<TaskList> taskList = new ArrayList<>();
        taskList.add(this.taskListTestWithId);

        String content = this.mock
            .perform(request(HttpMethod.GET, "/tasklist/readAll")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        
        assertEquals(this.objectMapper.writeValueAsString(taskList), content);
    }
	
    @Test
    void testUpdate() throws Exception {
        TaskList newTaskList = new TaskList("Do something");
        TaskList updatedTaskList = new TaskList(newTaskList.getName());
        updatedTaskList.setId(this.id);

        String result = this.mock
            .perform(request(HttpMethod.PUT, "/tasklist/update/" + this.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(newTaskList)))
            .andExpect(status().isAccepted())
            .andReturn().getResponse().getContentAsString();
        
        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedTaskList)), result);
    }
    
    @Test
    void testDelete() throws Exception {
        this.mock
            .perform(request(HttpMethod.DELETE, "/tasklist/delete/" + this.id))
            .andExpect(status().isNoContent());
    }

}
