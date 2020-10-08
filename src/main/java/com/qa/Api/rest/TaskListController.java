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


import com.qa.Api.Service.TaskListService;

import com.qa.Api.dto.TaskListDTO;

import com.qa.Api.persistence.domain.TaskList;


@RestController
@CrossOrigin
@RequestMapping("/tasklist")
public class TaskListController {
	private TaskListService service;
	
	
	@Autowired
	public TaskListController(TaskListService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<TaskListDTO> create(@RequestBody TaskList tasklist){
		return new ResponseEntity<>(this.service.createTaskList(tasklist), HttpStatus.CREATED);	
		
	}
	
	@GetMapping("/readAll")
	public ResponseEntity<List<TaskListDTO>> getAllTaskLists(){
		return ResponseEntity.ok(this.service.readAllTaskLists());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<TaskListDTO> getTaskListById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<TaskListDTO> updateTaskListById(@PathVariable Long id, @RequestBody TaskListDTO tasklistDTO){
		TaskListDTO updated = this.service.update(tasklistDTO, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskListDTO> deleteTaskListById(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				
	}

}
