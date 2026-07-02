package edu.upc.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
public class Candidat implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long professionPk;

	private String noms, genre, etatCivil, lieuNais;

	private Date dateNais;

	private Profession profession;

	@Builder.Default
	private List<Formation> formations = new ArrayList<>();

}
