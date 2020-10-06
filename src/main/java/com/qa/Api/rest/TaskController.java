package com.qa.Api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.Api.Service.TaskService;

import com.qa.Api.dto.TaskDTO;

import com.qa.Api.persistence.domain.Task;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {
	private TaskService service;
	
	
	@Autowired
	public TaskController(TaskService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<TaskDTO> create(@RequestBody Task task){
		return new ResponseEntity<>(this.service.createTask(task), HttpStatus.CREATED);	
		
	}
	
	@GetMapping("/readAll")
	public ResponseEntity<List<TaskDTO>> getAllTasks(){
		return ResponseEntity.ok(this.service.readAllTasks());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<TaskDTO> updateTaskById(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
		TaskDTO updated = this.service.update(taskDTO, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskDTO> deleteTaskById(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				
	}

}
