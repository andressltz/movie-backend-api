package br.com.andressltz.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.andressltz.model.Movie;

@Component
public class FileMoviesReader implements ApplicationRunner {

	@Value("${file.path:src/main/resources/import/}")
	private String path;

	@Value("${file.name:Movielist.csv}")
	private String fileName;

	@Value("${file.splitter:;}")
	private String splitter;

	@Value("${file.header:true}")
	private Boolean hasHeader;

	@Autowired
	private MovieService movieService;

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
			System.err.printf("Error reading file %s. Message: %s%n", path + fileName, ex.getMessage());
		}
	}

	private void saveMovie(Movie movie) {
		if (movie != null) {
			Movie saved = movieService.save(movie);
			System.out.printf("Movie %s saved.%n", saved.getTitle());
		}
	}

	private Movie convertLine(String[] movie) {
		if (movie != null) {
			Movie m = new Movie();
			m.setYear(movie[0]);
			m.setTitle(movie[1]);
			m.setStudios(movie[2]);
			m.setProducers(movie[3]);
			Boolean winner = null;
			if (movie.length > 4 && Strings.isNotBlank(movie[4])) {
				if (movie[4].equalsIgnoreCase("yes")) {
					winner = true;
				} else {
					winner = Boolean.valueOf(movie[4]);
				}
			}
			m.setWinner(winner);
			return m;
		}
		return null;
	}

}
