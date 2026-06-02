package edu.upc.utils;

import org.springframework.stereotype.Component;

import edu.upc.models.Formation;
import edu.upc.models.dtos.FormationDto;

@Component
public class Mapper {
	
	public Formation mapToFormationDto(FormationDto dto) {
		return Formation.builder().description(dto.getDescription()).duree(dto.getDuree()).build();

	}

}
