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
import com.qa.Api.dto.TaskDTO;
import com.qa.Api.persistence.domain.Task;
import com.qa.Api.persistence.repository.TaskRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {
	
    @Autowired
    private MockMvc mock;

    
    @Autowired
    private TaskRepository repo;

    @Autowired
    private ModelMapper modelMapper;

   
    @Autowired
    private ObjectMapper objectMapper;

    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;

    private Long id = 1L;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    @BeforeEach
    void init() {
    	
        this.repo.deleteAll();
        this.testTask = new Task("Do something!");
        this.testTaskWithId = this.repo.save(this.testTask);
        this.taskDTO = this.mapToDTO(testTaskWithId);
        this.id = this.testTaskWithId.getId();
    }

    @Test
    void testCreate() throws Exception {
        this.mock
                .perform(request(HttpMethod.POST, "/task/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.testTask))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.taskDTO)));
    }

    @Test
    void testRead() throws Exception {
        this.mock.perform(request(HttpMethod.GET, "/task/read/" + this.id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.taskDTO)));
    }

    @Test
    void testReadAll() throws Exception {
        List<TaskDTO> ListOfTasks = new ArrayList<>();
        ListOfTasks.add(this.taskDTO);
        String expected = this.objectMapper.writeValueAsString(ListOfTasks);
        

        String actual = this.mock.perform(request(HttpMethod.GET, "/task/readAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @Test
    void testUpdate() throws Exception {
        TaskDTO newTask = new TaskDTO(null, "Have a good day!");
        Task updatedTask = new Task(newTask.getToDo());
        updatedTask.setId(this.id);
        String expected = this.objectMapper.writeValueAsString(this.mapToDTO(updatedTask));

        String actual = this.mock.perform(request(HttpMethod.PUT, "/task/update/" + this.id) 
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(newTask))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()) 
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @Test
    void testDelete() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/task/delete/" + this.id)).andExpect(status().isNoContent());
        
    }

}
