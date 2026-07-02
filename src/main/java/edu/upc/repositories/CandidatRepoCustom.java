package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Candidat;
import edu.upc.models.Formation;

public interface CandidatRepoCustom {

	long create(Candidat entity);

	void update(long id, Candidat entity);

	void delete(long id);

	Candidat getById(long id);

	List<Candidat> getWithProfession();

	Candidat getWithProfessionById(long id);

	List<Formation> getFormations(long candidatId);

	List<Candidat> search(String keyword);

}
