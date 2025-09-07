package br.com.andressltz.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name = "tb_movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "mv_year")
	private String year;

	@Column(name = "title")
	private String title;

	@Column(name = "studios")
	private String studios;

	@Column(name = "producers")
	private String producers;

	@Column(name = "winner")
	private Boolean winner;

}
