package com.qa.Api.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Guitarist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "guitarist_name", unique = true)
	@NotNull
	@Size(min = 1, max = 120)
	private String name;
	
	@Column(name = "Strings")
	@Min(4)
	@Max(12)
	private int noOfStrings;
	
	@Column(name = "type")
	@NotNull
	@Size(min = 1, max = 120)
	private String type;
	
	
	@ManyToOne
	private Band band;

	public Guitarist(@NotNull @Size(min = 1, max = 120) String name, @Min(4) @Max(12) Integer noOfStrings,
			@NotNull @Size(min = 1, max = 120) String type) {
		super();
		this.name = name;
		this.noOfStrings = noOfStrings;
		this.type = type;
	}
	
	
	
	

}
