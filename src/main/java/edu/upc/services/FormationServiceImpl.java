package edu.upc.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import edu.upc.models.Formation;
import edu.upc.models.dtos.FormationDto;
import edu.upc.repositories.FormationRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;

@Service
public class FormationServiceImpl implements FormationService {
	
	@Autowired
	private FormationRepoCustom repoC;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Formation create(FormationDto dto, BindingResult result) {
		libraryUtil.validate(result);
		
		Formation row = mapper.mapToFormationDto(dto);
		
		long id = repoC.create(row);
		
		return repoC.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Formation update(long id, FormationDto dto, BindingResult result) {
		libraryUtil.validate(result);
		
		Formation row = mapper.mapToFormationDto(dto);
		
		repoC.update(id, row);
		
		return repoC.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Formation> delete(long id) {
		repoC.delete(id);
		
		return repoC.get();
	}

	@Override
	public Formation getById(long id) {
		return repoC.getById(id);
	}

	@Override
	public List<Formation> get() {
		return repoC.get();
	}

}
