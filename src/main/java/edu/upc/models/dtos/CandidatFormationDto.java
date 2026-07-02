package edu.upc.models.dtos;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CandidatFormationDto {

	@Positive(message = "Candidat invalide")
	private long candidatPk;

	@Positive(message = "Formation invalide")
	private long formationPk;

}
