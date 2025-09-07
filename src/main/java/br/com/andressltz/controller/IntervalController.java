package br.com.andressltz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andressltz.dto.IntervalDto;
import br.com.andressltz.service.MovieService;

@RestController
@RequestMapping("/intervals")
public class IntervalController {

	@Autowired
	private MovieService movieService;

	@GetMapping()
	public IntervalDto getIntervals() {
		return movieService.getIntervalWin();
	}

}
