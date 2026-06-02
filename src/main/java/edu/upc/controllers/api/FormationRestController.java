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
import org.springframework.web.bind.annotation.RestController;

import edu.upc.models.Formation;
import edu.upc.models.dtos.FormationDto;
import edu.upc.services.FormationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/formations")
public class FormationRestController {
	
	@Autowired
	private FormationService service;
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody FormationDto dto, BindingResult result,
			HttpServletRequest request) {

		Formation row = service.create(dto, result);
		
		return new ResponseEntity<>(row, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody FormationDto dto,
			BindingResult result, HttpServletRequest request) {

		Formation row = service.update(id, dto, result);

		return new ResponseEntity<>(row, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id, HttpServletRequest request) {

		List<Formation> data  = service.delete(id);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id, HttpServletRequest request) {

		Formation row = service.getById(id);
		
		return new ResponseEntity<>(row, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> get(HttpServletRequest request) {

		List<Formation> data = service.get();
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
}
