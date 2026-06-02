package edu.upc.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class FormationDto {
	
	@NotBlank(message = "Description invalide")
	@Size(min=1, max=45, message = "Description invalide")
	private String description;
	
	@Positive(message = "Duree invalide")
	private int duree;
	


}
