package ca.sheridancollege.khushi.service;

import ca.sheridancollege.khushi.bean.Breed;
import ca.sheridancollege.khushi.bean.CountResult;
import ca.sheridancollege.khushi.bean.Dog;
import ca.sheridancollege.khushi.database.DogRepository;
import ca.sheridancollege.khushi.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DogServiceImpl implements DogService {

    private DogRepository dogRepository;

    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public List<Dog> findAllDog(long userId) {
        return dogRepository.findAllDog(userId);
    }

    @Override
    public void addUpdateDog(Dog dog) {
        dogRepository.addUpdateDog(dog);
    }

    @Override
    public Breed findBreedById(long id) {
        return dogRepository.findBreedById(id);
    }

    @Override
    public void deleteDogById(long id) {
        dogRepository.deleteDogById(id);
    }

    @Override
    public CountResult totleDogsByBreedId(long id, String name) {
        return dogRepository.totleDogsByBreedId(id, name);
    }

    @Override
    public Long totleDogsByBreedIdAndGender(long id, String gender) {
        return dogRepository.totleDogsByBreedIdAndGender(id, gender);
    }

    @Override
    public List<Breed> findAllBreed() {
        return dogRepository.findAllBreed();
    }

    @Override
    public Long totleDogsByBreedIdAndSpecialty(long id, String specialty) {
        return dogRepository.totleDogsByBreedIdAndSpecialty(id, specialty);
    }

    @Override
    public Dog findDogById(long id) {
        return dogRepository.findDogById(id);
    }
}
