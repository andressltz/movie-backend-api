package br.com.andressltz.dto;

import lombok.Data;

@Data
public class MovieDto {

	private String year;

	private String title;

	private String studios;

	private String producers;

	private Boolean winner;

}
