package com.qa.Api.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.qa.Api.Service.TaskService;
import com.qa.Api.dto.TaskDTO;
import com.qa.Api.persistence.domain.Task;
import com.qa.Api.persistence.repository.TaskRepository;


@SpringBootTest
class TaskServiceUnitTest {

    @Autowired
    private TaskService service;


    @MockBean
    private TaskRepository repo;

   
    @MockBean
    private ModelMapper modelMapper;



    private List<Task> taskList;
    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;

 
    final Long id = 1L;
    final String testToDo = "Do this";
    

    @BeforeEach
    void init() {
        this.taskList = new ArrayList<>();
        this.testTask = new Task(testToDo);
        this.taskList.add(testTask);
        this.testTaskWithId = new Task(testTask.getToDo());
        this.testTaskWithId.setId(id);
        this.taskDTO = modelMapper.map(testTaskWithId, TaskDTO.class);
    }

    @Test
    void createTest() {
        when(this.repo.save(this.testTask)).thenReturn(this.testTaskWithId);
        when(this.modelMapper.map(this.testTaskWithId, TaskDTO.class)).thenReturn(this.taskDTO);
        TaskDTO expected = this.taskDTO;
        TaskDTO actual = this.service.createTask(this.testTask);
        assertThat(expected).isEqualTo(actual);

        verify(this.repo, times(1)).save(this.testTask);
    }

    @Test
    void readTest() {

        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTaskWithId));

        when(this.modelMapper.map(testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.taskDTO).isEqualTo(this.service.read(this.id));

        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    void readAllTest() {
        // findAll() returns a list, which is handy, since we've got one spun up :D
        when(this.repo.findAll()).thenReturn(this.taskList);

        when(this.modelMapper.map(this.testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.service.readAllTasks().isEmpty()).isFalse();

        verify(this.repo, times(1)).findAll();
    }

    @Test
    void updateTest() {
        Task task = new Task("Do this instead");
        task.setId(this.id);

        TaskDTO taskDTO = new TaskDTO(null, "Do this instead");

        Task updatedTask = new Task(taskDTO.getToDo());
        updatedTask.setId(this.id);

        TaskDTO updatedTaskDTO = new TaskDTO(this.id, updatedTask.getToDo());

        when(this.repo.findById(this.id)).thenReturn(Optional.of(task));

        when(this.repo.save(task)).thenReturn(updatedTask);

        when(this.modelMapper.map(updatedTask, TaskDTO.class)).thenReturn(updatedTaskDTO);

        assertThat(updatedTaskDTO).isEqualTo(this.service.update(taskDTO, this.id));


        verify(this.repo, times(1)).findById(1L);
        verify(this.repo, times(1)).save(updatedTask);
    }

    @Test
    void deleteTest() {
    
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

       
        verify(this.repo, times(1)).deleteById(id);

      
        verify(this.repo, times(2)).existsById(id);
    }

}
