package br.com.andressltz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andressltz.dto.IntervalDto;
import br.com.andressltz.service.IntervalService;

@RestController
@RequestMapping("/intervals")
public class IntervalController {

	@Autowired
	private IntervalService intervalService;

	@GetMapping()
	public IntervalDto getIntervals() {
		return intervalService.getIntervalWin();
	}

}
