package ca.sheridancollege.khushi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	private Long id;
	private String name;

	public Role(String name) {
		this.name = name;
	}
}
