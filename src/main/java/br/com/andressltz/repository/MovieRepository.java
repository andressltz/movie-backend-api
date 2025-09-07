package br.com.andressltz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.andressltz.dto.ParticipationDto;
import br.com.andressltz.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findMovieByWinnerIs(Boolean winner);

	@Query("SELECT m.producers AS producer, m.year FROM Movie m WHERE m.winner = true")
	List<ParticipationDto> findProducersAndYearByWinnerIsTrue();

	@Query("SELECT m.producers AS producer, m.year FROM Movie m")
	List<ParticipationDto> findProducersAndYear();

}
