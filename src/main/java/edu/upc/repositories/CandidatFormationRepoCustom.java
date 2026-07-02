package edu.upc.repositories;

import java.util.List;

import edu.upc.models.CandidatFormation;

public interface CandidatFormationRepoCustom {

	long create(CandidatFormation entity);

	void delete(long id);

	CandidatFormation getById(long id);

	List<CandidatFormation> get();

}
