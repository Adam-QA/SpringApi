package com.qa.Api.dto;

import java.util.ArrayList;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;





@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class TaskListDTO {
	private Long id;
	private String name;
	private List<TaskDTO> tasks = new ArrayList<>();
}
