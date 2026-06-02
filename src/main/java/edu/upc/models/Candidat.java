package edu.upc.models;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Candidat implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private long professionPk;
	
	private String noms, genre, etatCivil, lieuNais;
	
	private Date dateNais;

}
