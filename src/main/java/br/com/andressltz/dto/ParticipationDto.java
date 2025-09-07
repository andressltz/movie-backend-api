package br.com.andressltz.dto;

import org.apache.logging.log4j.util.Strings;

import lombok.Data;

@Data
public class ParticipationDto {

	public ParticipationDto(String producer, String year) {
		this.producer = producer;
		this.year = Strings.isBlank(year) ? null : Integer.valueOf(year);
	}

	private String producer;
	private Integer year;

}
