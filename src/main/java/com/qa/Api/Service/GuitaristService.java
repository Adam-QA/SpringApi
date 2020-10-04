package com.qa.Api.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.Api.dto.GuitaristDTO;
import com.qa.Api.exception.GuitaristNotFoundException;
import com.qa.Api.persistence.domain.Guitarist;
import com.qa.Api.persistence.repository.GuitaristRepository;
import com.qa.Api.utils.ApiBeanUtils;

@Service
public class GuitaristService {
	
	private GuitaristRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public GuitaristService(GuitaristRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.mapper.map(guitarist, GuitaristDTO.class);
	}
	
	private Guitarist mapFromDTO(GuitaristDTO guitaristDTO) {
		return this.mapper.map(guitaristDTO, Guitarist.class);
	}
	
	
	public GuitaristDTO createGuitarist(Guitarist guitarist) {
//		Guitarist toSave = this.mapFromDTO(guitaristDTO);
		Guitarist saved = this.repo.save(guitarist);
		return this.mapToDTO(saved);
	}
	
	public List<GuitaristDTO> readAllGuitarists(){
		return this.repo.findAll()
				.stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
	}
	
	public GuitaristDTO read(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(GuitaristNotFoundException :: new));
		
	}
	
	public GuitaristDTO update(GuitaristDTO guitaristDTO, Long id) {
		Guitarist toUpdate = this.repo.findById(id).orElseThrow(GuitaristNotFoundException :: new);
		ApiBeanUtils.mergeNotNull(guitaristDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
		
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	

}
