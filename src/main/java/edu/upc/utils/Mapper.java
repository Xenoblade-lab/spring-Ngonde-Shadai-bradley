package edu.upc.utils;

import org.springframework.stereotype.Component;

import edu.upc.models.Candidat;
import edu.upc.models.CandidatFormation;
import edu.upc.models.Formation;
import edu.upc.models.Profession;
import edu.upc.models.dtos.CandidatDto;
import edu.upc.models.dtos.CandidatFormationDto;
import edu.upc.models.dtos.FormationDto;
import edu.upc.models.dtos.ProfessionDto;

@Component
public class Mapper {

	public Formation mapToFormation(FormationDto dto) {
		return Formation.builder().description(dto.getDescription()).duree(dto.getDuree()).build();
	}

	public Profession mapToProfession(ProfessionDto dto) {
		return Profession.builder().description(dto.getDescription()).build();
	}

	public Candidat mapToCandidat(CandidatDto dto) {
		return Candidat.builder()
				.professionPk(dto.getProfessionPk())
				.noms(dto.getNoms())
				.genre(dto.getGenre())
				.etatCivil(dto.getEtatCivil())
				.lieuNais(dto.getLieuNais())
				.dateNais(dto.getDateNais())
				.build();
	}

	public CandidatFormation mapToCandidatFormation(CandidatFormationDto dto) {
		return CandidatFormation.builder()
				.candidatPk(dto.getCandidatPk())
				.formationPk(dto.getFormationPk())
				.build();
	}

	public ProfessionDto toProfessionDto(Profession entity) {
		return ProfessionDto.builder().description(entity.getDescription()).build();
	}

	public FormationDto toFormationDto(Formation entity) {
		return FormationDto.builder().description(entity.getDescription()).duree(entity.getDuree()).build();
	}

	public CandidatDto toCandidatDto(Candidat entity) {
		return CandidatDto.builder()
				.professionPk(entity.getProfessionPk())
				.noms(entity.getNoms())
				.genre(entity.getGenre())
				.etatCivil(entity.getEtatCivil())
				.lieuNais(entity.getLieuNais())
				.dateNais(entity.getDateNais())
				.build();
	}

	public CandidatFormationDto toCandidatFormationDto(CandidatFormation entity) {
		return CandidatFormationDto.builder()
				.candidatPk(entity.getCandidatPk())
				.formationPk(entity.getFormationPk())
				.build();
	}

	/** @deprecated utiliser {@link #mapToFormation(FormationDto)} */
	@Deprecated
	public Formation mapToFormationDto(FormationDto dto) {
		return mapToFormation(dto);
	}

}
