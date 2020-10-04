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

import com.qa.Api.Service.BandService;

import com.qa.Api.dto.BandDTO;
import com.qa.Api.persistence.domain.Band;


@RestController
@CrossOrigin
@RequestMapping("/band")
public class BandController {
	private BandService service;
	
	
	@Autowired
	public BandController(BandService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<BandDTO> create(@RequestBody Band band){
//		BandDTO created = this.service.createBand(Band);
		return new ResponseEntity<>(this.service.createBand(band), HttpStatus.CREATED);	
		
	}
	
	@GetMapping("/readAll")
	public ResponseEntity<List<BandDTO>> getAllBands(){
		return ResponseEntity.ok(this.service.readAllBands());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<BandDTO> getBandById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<BandDTO> updateBandById(@PathVariable Long id, @RequestBody BandDTO bandDTO){
		BandDTO updated = this.service.update(bandDTO, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BandDTO> deleteBandById(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				
	}

}
