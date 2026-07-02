package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Candidat;
import edu.upc.models.dtos.CandidatDto;
import edu.upc.repositories.CandidatRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class CandidatServiceImpl implements CandidatService {

	@Autowired
	private CandidatRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Candidat create(CandidatDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Candidat row = mapper.mapToCandidat(dto);
		long id = repo.create(row);
		return repo.getWithProfessionById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Candidat update(long id, CandidatDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Candidat row = mapper.mapToCandidat(dto);
		repo.update(id, row);
		return repo.getWithProfessionById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Candidat> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.getWithProfession();
	}

	@Override
	public Candidat getById(long id) {
		Candidat row = repo.getWithProfessionById(id);
		if (row == null) {
			throw new NotFoundException("Candidat introuvable");
		}
		return row;
	}

	@Override
	public List<Candidat> get() {
		return repo.getWithProfession();
	}

	@Override
	public List<Candidat> search(String keyword) {
		return repo.search(keyword);
	}

	private void requireExists(long id) {
		if (repo.getWithProfessionById(id) == null) {
			throw new NotFoundException("Candidat introuvable");
		}
	}

}
