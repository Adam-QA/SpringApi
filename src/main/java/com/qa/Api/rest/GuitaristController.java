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

import com.qa.Api.Service.GuitaristService;
import com.qa.Api.dto.GuitaristDTO;
import com.qa.Api.persistence.domain.Guitarist;

@RestController
@CrossOrigin
@RequestMapping("/guitarist")
public class GuitaristController {
	private GuitaristService service;
	
	
	@Autowired
	public GuitaristController(GuitaristService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<GuitaristDTO> create(@RequestBody Guitarist guitarist){
//		GuitaristDTO created = this.service.createGuitarist(guitaristDTO);
		return new ResponseEntity<>(this.service.createGuitarist(guitarist), HttpStatus.CREATED);	
		
	}
	
	@GetMapping("/readAll")
	public ResponseEntity<List<GuitaristDTO>> getAllGuitarists(){
		return ResponseEntity.ok(this.service.readAllGuitarists());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GuitaristDTO> getGuitaristById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<GuitaristDTO> updateGuitaristById(@PathVariable Long id, @RequestBody GuitaristDTO guitaristDTO){
		GuitaristDTO updated = this.service.update(guitaristDTO, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<GuitaristDTO> deleteGuitaristById(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				
	}

}
