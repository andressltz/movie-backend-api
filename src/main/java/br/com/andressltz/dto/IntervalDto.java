package br.com.andressltz.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IntervalDto {

	private List<WinnerDto> min;

	private List<WinnerDto> max;

}
