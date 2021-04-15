package com.pagination.entities;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "tutorial")
public class Tutorial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private Boolean published;
}
