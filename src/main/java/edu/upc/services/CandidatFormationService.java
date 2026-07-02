package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.CandidatFormation;
import edu.upc.models.dtos.CandidatFormationDto;

public interface CandidatFormationService {

	CandidatFormation create(CandidatFormationDto dto, BindingResult result);

	List<CandidatFormation> delete(long id);

	CandidatFormation getById(long id);

	List<CandidatFormation> get();

}
