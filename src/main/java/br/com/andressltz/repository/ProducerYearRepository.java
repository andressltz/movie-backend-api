package br.com.andressltz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.andressltz.dto.ParticipationDto;
import br.com.andressltz.model.ProducerYear;

public interface ProducerYearRepository extends JpaRepository<ProducerYear, Long> {

	@Query("SELECT p.name AS producer, p.year FROM ProducerYear p WHERE p.winner = true")
	List<ParticipationDto> findProducersAndYearByWinnerIsTrue();

}
