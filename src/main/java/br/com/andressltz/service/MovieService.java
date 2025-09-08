package br.com.andressltz.service;

import java.util.List;

import br.com.andressltz.dto.IntervalDto;
import br.com.andressltz.model.Movie;

public interface MovieService {

	List<Movie> getMovies();

	Movie save(Movie movie);

	List<Movie> findByWinner();

	IntervalDto getIntervalWin();

}
