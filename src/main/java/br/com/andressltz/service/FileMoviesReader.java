package br.com.andressltz.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.andressltz.dto.MovieDto;
import br.com.andressltz.model.ProducerYear;

@Component
public class FileMoviesReader implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(FileMoviesReader.class);

	@Value("${file.path:src/main/resources/import/}")
	private String path;

	@Value("${file.name:Movielist.csv}")
	private String fileName;

	@Value("${file.splitter:;}")
	private String splitter;

	@Value("${file.header:true}")
	private Boolean hasHeader;

	@Autowired
	private ProducerYearService producerYearService;

	@Override
	public void run(ApplicationArguments args) {
		try (BufferedReader br = new BufferedReader(new FileReader(path + fileName))) {
			String line;
			if (hasHeader) {
				br.readLine();
			}
			while ((line = br.readLine()) != null) {
				String[] movieLine = line.split(splitter);
				saveMovie(convertLine(movieLine));
			}
		} catch (IOException ex) {
			logger.error("Error reading file {}. Message: {}", path + fileName, ex.getMessage());
		}
	}

	private void saveMovie(MovieDto movie) {
		if (movie != null) {
			String[] producers = movie.getProducers().split(", | and ");
			for (String producer : producers) {
				ProducerYear producerYear = populateProducerYear(producer, movie);
				if (producerYear != null) {
					ProducerYear saved = producerYearService.save(producerYear);
					logger.info("Producer {} saved. Movie {}.", saved.getName(), movie.getTitle());
				} else {
					logger.error("Producer {} not saved. Movie {}.", producer, movie.getTitle());
				}
			}
		}
	}

	private MovieDto convertLine(String[] movie) {
		if (movie != null) {
			MovieDto dto = new MovieDto();
			dto.setYear(movie[0]);
			dto.setTitle(movie[1]);
			dto.setStudios(movie[2]);
			dto.setProducers(movie[3]);
			Boolean winner = null;
			if (movie.length > 4 && Strings.isNotBlank(movie[4])) {
				if (movie[4].equalsIgnoreCase("yes")) {
					winner = true;
				} else {
					winner = Boolean.valueOf(movie[4]);
				}
			}
			dto.setWinner(winner);
			return dto;
		}
		return null;
	}

	private ProducerYear populateProducerYear(String producer, MovieDto movie) {
		if (Strings.isNotEmpty(producer)) {
			ProducerYear producerYear = new ProducerYear();
			producerYear.setYear(movie.getYear());
			producerYear.setName(producer.trim());
			producerYear.setWinner(movie.getWinner());
			return producerYear;
		}
		return null;
	}

}
