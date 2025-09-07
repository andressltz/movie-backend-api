package br.com.andressltz.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import br.com.andressltz.dto.IntervalDto;
import br.com.andressltz.dto.ParticipationDto;
import br.com.andressltz.dto.WinnerDto;
import br.com.andressltz.model.Movie;
import br.com.andressltz.repository.MovieRepository;

@Component
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

	private static final int QTD_WINNERS = 2;

	private final MovieRepository movieRepository;

	@Override
	public String getMovies() {
		return movieRepository.findAll().toString();
	}

	@Override
	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> findByWinner() {
		return movieRepository.findMovieByWinnerIs(true);
	}

	@Override
	public IntervalDto getIntervalWin() {
		//		Map<String, List<Integer>> producerList = filterProducers(movieRepository.findProducersAndYearByWinnerIsTrue());
		Map<String, List<Integer>> producerList = filterProducers(movieRepository.findProducersAndYear());
		IntervalDto intervalDto = new IntervalDto(null, null);
		ArrayList<WinnerDto> winnersMinInterval = new ArrayList<>();
		ArrayList<WinnerDto> winnersMaxInterval = new ArrayList<>();

		for (Map.Entry<String, List<Integer>> producer : producerList.entrySet()) {
			Integer minInterval = null;
			Integer yearMinIntervalPrevious = null;
			Integer yearMinIntervalFollowing = null;
			Integer maxInterval = null;
			Integer yearMaxIntervalPrevious = null;
			Integer yearMaxIntervalFollowing = null;
			Integer previousWin = null;

			int interval;

			producer.getValue().sort(Integer::compareTo);
			for (Integer year : producer.getValue()) {
				if (previousWin == null) {
					previousWin = year;
					continue;
				}

				interval = year - previousWin;

				if (minInterval == null || interval < minInterval) {
					minInterval = interval;
					yearMinIntervalPrevious = previousWin;
					yearMinIntervalFollowing = year;
				}
				if (maxInterval == null || interval > maxInterval) {
					maxInterval = interval;
					yearMaxIntervalPrevious = previousWin;
					yearMaxIntervalFollowing = year;
				}
			}

			winnersMinInterval.add(populateWinner(producer.getKey(), yearMinIntervalPrevious, yearMinIntervalFollowing, minInterval));
			winnersMaxInterval.add(populateWinner(producer.getKey(), yearMaxIntervalPrevious, yearMaxIntervalFollowing, maxInterval));
		}

		winnersMinInterval.sort(Comparator.comparing(WinnerDto::getInterval));
		List<WinnerDto> winnersMinLimited = getFirstWinners(winnersMinInterval);
		winnersMaxInterval.sort(Comparator.comparing(WinnerDto::getInterval).reversed());
		List<WinnerDto> winnersMaxLimited = getFirstWinners(winnersMaxInterval);

//		List<WinnerDto> winnersMaxLimited = limitWinners(winnersMaxInterval);

		intervalDto.setMin(winnersMinLimited);
		intervalDto.setMax(winnersMaxLimited);
		return intervalDto;
	}

	private WinnerDto populateWinner(String producer, Integer yearMinIntervalPrevious, Integer yearMinIntervalFollowing, Integer minInterval) {
		return WinnerDto.builder()
				.producer(producer).previousWin(yearMinIntervalPrevious).followingWin(yearMinIntervalFollowing).interval(minInterval)
				.build();
	}

	private Map<String, List<Integer>> filterProducers(List<ParticipationDto> participationList) {
		Map<String, List<Integer>> producerList = new HashMap<>();
		if (participationList != null && !participationList.isEmpty()) {
			producerList = participationList.stream()
					.collect(Collectors.groupingBy(ParticipationDto::getProducer, Collectors.mapping(ParticipationDto::getYear, Collectors.toList())))
					.entrySet().stream()
					.filter(entry -> entry.getValue().size() > 1)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		return producerList;
	}

	private List<WinnerDto> getFirstWinners(ArrayList<WinnerDto> winnersList) {
		int interval = winnersList.stream().findFirst().get().getInterval();
		return winnersList.stream().filter(winner -> winner.getInterval() == interval)
				.sorted(Comparator.comparing(WinnerDto::getPreviousWin))
				.collect(Collectors.toList());
	}

	private List<WinnerDto> limitWinners(ArrayList<WinnerDto> winnersList) {
		winnersList.sort(Comparator.comparing(WinnerDto::getInterval));
		List<WinnerDto> winnersLimited = winnersList.subList(0, QTD_WINNERS);
		winnersLimited.sort(Comparator.comparing(WinnerDto::getPreviousWin));
		return winnersLimited;
	}

}
