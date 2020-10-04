package com.qa.Api.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.Api.dto.BandDTO;

import com.qa.Api.exception.BandNotFoundException;

import com.qa.Api.persistence.domain.Band;

import com.qa.Api.persistence.repository.BandRepository;

import com.qa.Api.utils.ApiBeanUtils;


@Service
public class BandService {
private BandRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public BandService(BandRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private BandDTO mapToDTO(Band band) {
		return this.mapper.map(band, BandDTO.class);
	}
	
	private Band mapFromDTO(BandDTO bandDTO) {
		return this.mapper.map(bandDTO, Band.class);
	}
	
	
	public BandDTO createBand(Band band) {
//		Band toSave = this.mapFromDTO(bandDTO);
		Band saved = this.repo.save(band);
		return this.mapToDTO(saved);
	}
	
	public List<BandDTO> readAllBands() {    
		List<Band> found = this.repo.findAll();   
		List<BandDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());   
		return streamed;
	}
	
	public BandDTO read(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(BandNotFoundException :: new));
		
	}
	
	public BandDTO update(BandDTO bandDTO, Long id) {
		Band toUpdate = this.repo.findById(id).orElseThrow(BandNotFoundException :: new);
		ApiBeanUtils.mergeNotNull(bandDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
		
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}

}
