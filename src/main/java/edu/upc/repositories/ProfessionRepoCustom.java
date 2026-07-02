package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Profession;

public interface ProfessionRepoCustom {

	long create(Profession entity);

	void update(long id, Profession entity);

	void delete(long id);

	Profession getById(long id);

	List<Profession> get();

}
