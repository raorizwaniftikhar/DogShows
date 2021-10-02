package ca.sheridancollege.khushi.service;

import ca.sheridancollege.khushi.bean.Breed;
import ca.sheridancollege.khushi.bean.CountResult;
import ca.sheridancollege.khushi.bean.Dog;
import ca.sheridancollege.khushi.bean.User;
import ca.sheridancollege.khushi.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DogService {

     List<Dog> findAllDog(long userId);

     void addUpdateDog(Dog dog);

     Breed findBreedById(long id);

     void deleteDogById(long id);

     CountResult totleDogsByBreedId(long id, String name);

     Long totleDogsByBreedIdAndGender(long id, String gender);

     Long totleDogsByBreedIdAndSpecialty(long id, String specialty);

     Dog findDogById(long id);

     List<Breed> findAllBreed();
}
