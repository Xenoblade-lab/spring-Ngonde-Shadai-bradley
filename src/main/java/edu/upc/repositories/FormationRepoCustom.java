package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Formation;

public interface FormationRepoCustom {
	
	long create(Formation entity);
	
	void update(long id, Formation entity);
	
	void delete(long id);
	
	Formation getById(long id);
	
	List<Formation> get();

}
