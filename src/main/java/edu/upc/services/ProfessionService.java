package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Profession;
import edu.upc.models.dtos.ProfessionDto;

public interface ProfessionService {

	Profession create(ProfessionDto dto, BindingResult result);

	Profession update(long id, ProfessionDto dto, BindingResult result);

	List<Profession> delete(long id);

	Profession getById(long id);

	List<Profession> get();

}
