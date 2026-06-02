package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Formation;
import edu.upc.models.dtos.FormationDto;

public interface FormationService {
	
	Formation create(FormationDto dto, BindingResult result);
	
	Formation update(long id, FormationDto dto, BindingResult result);
	
	List<Formation> delete(long id);
	
	Formation getById(long id);
	
	List<Formation> get();

}
