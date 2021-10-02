package ca.sheridancollege.khushi.bean;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String picture;
	private Collection<Role> roles;

}
