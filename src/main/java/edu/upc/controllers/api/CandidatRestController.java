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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.models.Candidat;
import edu.upc.models.dtos.CandidatDto;
import edu.upc.services.CandidatService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidats")
public class CandidatRestController {

	@Autowired
	private CandidatService service;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody CandidatDto dto, BindingResult result) {
		Candidat row = service.create(dto, result);
		return new ResponseEntity<>(row, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody CandidatDto dto,
			BindingResult result) {
		Candidat row = service.update(id, dto, result);
		return new ResponseEntity<>(row, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		List<Candidat> data = service.delete(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> get(@RequestParam(value = "keyword", required = false) String keyword) {
		List<Candidat> data = (keyword != null && !keyword.isBlank())
				? service.search(keyword)
				: service.get();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
