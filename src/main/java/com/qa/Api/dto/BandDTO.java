package com.qa.Api.dto;

import java.util.ArrayList;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import com.qa.Api.persistence.domain.Guitarist;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BandDTO {
	private Long id;
	private String name;
	private List<GuitaristDTO> guitarists;
}
