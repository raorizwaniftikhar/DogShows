package ca.sheridancollege.khushi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountResult {
	private String name;
	private long total;
	private long classMaleCount;
	private long classFemaleCount;
	private long specialtyMaleCount;
	private long specialtyFemaleCount;
}
