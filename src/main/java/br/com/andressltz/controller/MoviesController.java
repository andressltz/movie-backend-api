package br.com.andressltz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andressltz.dto.IntervalDto;
import br.com.andressltz.model.Movie;
import br.com.andressltz.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MoviesController {

	@Autowired
	private MovieService movieService;

	@GetMapping("/")
	public IntervalDto getIntervalWinPath() {
		return movieService.getIntervalWin();
	}

	@GetMapping()
	public IntervalDto getIntervalWin() {
		return movieService.getIntervalWin();
	}

	@GetMapping("/winners")
	public List<Movie> getWinners() {
		return movieService.findByWinner();
	}


}
