package ca.sheridancollege.khushi.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog {

	private long id;
	private String name;
	private String gender;
	private String specialty;
	private Breed breedId;
	private User userId;

}
