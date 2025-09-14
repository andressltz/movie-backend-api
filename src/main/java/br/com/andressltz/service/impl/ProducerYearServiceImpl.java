package br.com.andressltz.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import br.com.andressltz.dto.ParticipationDto;
import br.com.andressltz.model.ProducerYear;
import br.com.andressltz.repository.ProducerYearRepository;
import br.com.andressltz.service.ProducerYearService;

@Component
@AllArgsConstructor
public class ProducerYearServiceImpl implements ProducerYearService {

	private final ProducerYearRepository repository;

	@Override
	public ProducerYear save(ProducerYear producer) {
		return repository.save(producer);
	}

	@Override
	public List<ParticipationDto> findProducersAndYearByWinnerIsTrue() {
		return repository.findProducersAndYearByWinnerIsTrue();
	}

}
