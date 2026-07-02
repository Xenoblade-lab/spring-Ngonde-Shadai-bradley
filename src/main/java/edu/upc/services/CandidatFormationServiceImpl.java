package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.CandidatFormation;
import edu.upc.models.dtos.CandidatFormationDto;
import edu.upc.repositories.CandidatFormationRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class CandidatFormationServiceImpl implements CandidatFormationService {

	@Autowired
	private CandidatFormationRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public CandidatFormation create(CandidatFormationDto dto, BindingResult result) {
		libraryUtil.validate(result);
		CandidatFormation row = mapper.mapToCandidatFormation(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<CandidatFormation> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public CandidatFormation getById(long id) {
		CandidatFormation row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Inscription introuvable");
		}
		return row;
	}

	@Override
	public List<CandidatFormation> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Inscription introuvable");
		}
	}

}
