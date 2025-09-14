package br.com.andressltz.service;

import java.util.List;

import br.com.andressltz.dto.ParticipationDto;
import br.com.andressltz.model.ProducerYear;

public interface ProducerYearService {

	ProducerYear save(ProducerYear producer);

	List<ParticipationDto> findProducersAndYearByWinnerIsTrue();

}
