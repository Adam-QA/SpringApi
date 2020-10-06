//package com.qa.Api.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.qa.Api.dto.GuitaristDTO;
//import com.qa.Api.dto.TaskDTO;
//import com.qa.Api.persistence.domain.Guitarist;
//import com.qa.Api.persistence.domain.Task;
//import com.qa.Api.rest.TaskController;
//import com.qa.Api.Service.TaskService;
//
//@SpringBootTest
//public class TaskControllerUnitTest {
//	@Autowired
//	private TaskController controller;
//	
//	@MockBean
//	private TaskService service;
//	
//	@Autowired
//	private ModelMapper modelMapper;
//	
//	private TaskDTO mapToDTO(Task task) {
//		return this.modelMapper.map(task, TaskDTO.class);
//	}
//	
//	private List<Task> guitaristList;
//	private Guitarist testGuitarist;
//	private Guitarist testGuitaristWithId;
//	private GuitaristDTO guitaristDTO;
//	
//	private final String name = "John Darnielle";
//    private final Integer strings = 6;
//    private final String type = "Ibanez Talman";
//    private final Long id = 1L;
//    
//    
//    @BeforeEach
//    void init() {
//    	this.guitaristList = new ArrayList<>();
//    	this.testGuitarist = new Guitarist(name, strings, type);
//    	this.testGuitaristWithId = new Guitarist(testGuitarist.getName(), testGuitarist.getNoOfStrings(), testGuitarist.getType());
//    	this.testGuitaristWithId.setId(id);
//    	this.guitaristList.add(testGuitaristWithId);
//    	this.guitaristDTO = this.mapToDTO(testGuitaristWithId);
//    	
//    }
//    
//    @Test
//    void createTest() {
//    	when(this.service.createGuitarist(testGuitarist)).thenReturn(this.guitaristDTO);
//    	GuitaristDTO testCreated = this.guitaristDTO;
//    	assertThat(new ResponseEntity<GuitaristDTO>(testCreated, HttpStatus.CREATED)).isEqualTo(this.controller.create(testGuitarist));
//    	verify(this.service, times(1)).createGuitarist(this.testGuitarist);
//    }
//    
//    
//    @Test
//    void readTest() {
//        when(this.service.read(this.id)).thenReturn(this.guitaristDTO);
//
//        GuitaristDTO testReadOne = this.guitaristDTO;
//        assertThat(new ResponseEntity<GuitaristDTO>(testReadOne, HttpStatus.OK))
//                .isEqualTo(this.controller.getGuitaristById(this.id));
//
//        verify(this.service, times(1)).read(this.id);
//    }
//
//    
//    @Test
//    void readAllTest() {
//        when(this.service.readAllGuitarists())
//                .thenReturn(this.guitaristList.stream().map(this::mapToDTO).collect(Collectors.toList()));
//        assertThat(this.controller.getAllGuitarists().getBody().isEmpty()).isFalse();
//
//        verify(this.service, times(1)).readAllGuitarists();
//    }
//
//    
//    @Test
//    void updateTest() {
//        
//        GuitaristDTO newGuitarist = new GuitaristDTO(null, "Peter Peter Hughes", 4, "Fender American");
//        GuitaristDTO newGuitaristWithId = new GuitaristDTO(this.id, newGuitarist.getName(), newGuitarist.getNoOfStrings(),
//                newGuitarist.getType());
//        when(this.service.update(newGuitarist, this.id)).thenReturn(newGuitaristWithId);
//
//        assertThat(new ResponseEntity<GuitaristDTO>(newGuitaristWithId, HttpStatus.ACCEPTED))
//                .isEqualTo(this.controller.updateGuitaristById(this.id, newGuitarist));
//
//        verify(this.service, times(1)).update(newGuitarist, this.id);
//    }
//
//    
//    @Test
//    void deleteTest() {
//        this.controller.deleteGuitaristById(id); 
//
//        verify(this.service, times(1)).delete(id);
//    }
//
//}
