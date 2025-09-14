package br.com.andressltz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.andressltz.dto.IntervalDto;
import br.com.andressltz.dto.ParticipationDto;
import br.com.andressltz.repository.ProducerYearRepository;
import br.com.andressltz.service.ProducerYearService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProducerYearService producerYearService;

	@Autowired
	private ProducerYearRepository producerYearRepository;

	@Test
	public void shouldReadAndSaveProducerUsingRepositorySuccess() {
		// Act
		List<ParticipationDto> resultTrue = producerYearRepository.findProducersAndYearByWinnerIsTrue();

		// Assert
		assertFalse(resultTrue.isEmpty());
	}

	@Test
	public void shouldReadAndSaveProducerUsingServiceSuccess() {
		// Act
		List<ParticipationDto> result = producerYearService.findProducersAndYearByWinnerIsTrue();

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	public void getIntervalsTestSuccess() throws IOException {
		// Arrange
		String jsonExpectedPath = "src/test/resources/responseTest.json";
		IntervalDto intervalExpected = getIntervalExpectedDto(jsonExpectedPath);

		// Act
		ResponseEntity<IntervalDto> response = restTemplate.getForEntity("/intervals", IntervalDto.class);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(IntervalDto.class, response.getBody().getClass());
		assertEquals(intervalExpected, response.getBody());
	}

	private IntervalDto getIntervalExpectedDto(String jsonExpectedPath) throws IOException {
		String jsonExpected = Files.readString(Path.of(jsonExpectedPath));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonExpected, IntervalDto.class);
	}

}
