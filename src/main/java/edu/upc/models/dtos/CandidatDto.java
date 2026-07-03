package edu.upc.models.dtos;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CandidatDto {

	@Positive(message = "Profession invalide")
	private long professionPk;

	@NotBlank(message = "Noms invalides")
	@Size(min = 1, max = 100, message = "Noms invalides")
	private String noms;

	@NotBlank(message = "Genre invalide")
	@Size(min = 1, max = 1, message = "Genre invalide")
	private String genre;

	@NotBlank(message = "Etat civil invalide")
	@Size(min = 1, max = 20, message = "Etat civil invalide")
	private String etatCivil;

	@NotBlank(message = "Lieu de naissance invalide")
	@Size(min = 1, max = 45, message = "Lieu de naissance invalide")
	private String lieuNais;

	@NotNull(message = "Date de naissance invalide")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateNais;

}
