package edu.upc.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.models.CandidatFormation;
import edu.upc.models.dtos.CandidatFormationDto;
import edu.upc.services.CandidatFormationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidats-formations")
public class CandidatFormationRestController {

	@Autowired
	private CandidatFormationService service;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody CandidatFormationDto dto, BindingResult result) {
		CandidatFormation row = service.create(dto, result);
		return new ResponseEntity<>(row, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		List<CandidatFormation> data = service.delete(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> get() {
		return new ResponseEntity<>(service.get(), HttpStatus.OK);
	}

}
