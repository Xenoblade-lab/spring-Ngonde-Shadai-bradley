package edu.upc.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profession implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String description;

}
