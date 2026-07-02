package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Profession;
import edu.upc.models.dtos.ProfessionDto;
import edu.upc.repositories.ProfessionRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class ProfessionServiceImpl implements ProfessionService {

	@Autowired
	private ProfessionRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Profession create(ProfessionDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Profession row = mapper.mapToProfession(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Profession update(long id, ProfessionDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Profession row = mapper.mapToProfession(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Profession> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Profession getById(long id) {
		Profession row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Profession introuvable");
		}
		return row;
	}

	@Override
	public List<Profession> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Profession introuvable");
		}
	}

}
