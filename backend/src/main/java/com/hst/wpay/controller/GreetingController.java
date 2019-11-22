package com.hst.wpay.controller;

import com.hst.wpay.model.entity.User;
import com.hst.wpay.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dlgusrb0808@gmail.com
 */
@RestController
@RequestMapping("greeting")
public class GreetingController {

	private static Logger logger = LoggerFactory.getLogger(GreetingController.class);

	private final GreetingService greetingService;

	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GetMapping
	public ResponseEntity<List<User>> greeting() {
		return ResponseEntity.ok(greetingService.greeting());
	}

	@PostMapping("{name}")
	public ResponseEntity<String> create(@PathVariable String name) {
		logger.info(name);
		greetingService.create(name);
		return ResponseEntity.ok("update complete!");
	}

}
