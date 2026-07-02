package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Candidat;
import edu.upc.models.dtos.CandidatDto;

public interface CandidatService {

	Candidat create(CandidatDto dto, BindingResult result);

	Candidat update(long id, CandidatDto dto, BindingResult result);

	List<Candidat> delete(long id);

	Candidat getById(long id);

	List<Candidat> get();

	List<Candidat> search(String keyword);

}
