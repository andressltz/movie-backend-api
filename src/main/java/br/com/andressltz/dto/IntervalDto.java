package br.com.andressltz.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalDto {

	private List<WinnerDto> min;

	private List<WinnerDto> max;

}
