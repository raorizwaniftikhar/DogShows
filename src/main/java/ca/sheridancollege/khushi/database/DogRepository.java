package ca.sheridancollege.khushi.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.sheridancollege.khushi.bean.CountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.khushi.bean.Breed;
import ca.sheridancollege.khushi.bean.Dog;

@Repository
public class DogRepository {

    @Autowired
    private NamedParameterJdbcTemplate dataSource;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("rawtypes")
    public List<Dog> findAllDog(long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        ArrayList<Dog> dogs = new ArrayList<>();
        String query = null;
        if (userId > 0) {
            query = "SELECT * FROM dog where user_id = :user_id";
            parameters.addValue("user_id", userId);
        } else {
            query = "SELECT * FROM dog";
        }

        List<Map<String, Object>> rows = dataSource.queryForList(query, parameters);
        for (Map row : rows) {
            Dog dog = new Dog();
            dog.setId((long) row.get("id"));
            dog.setName((String) row.get("name"));
            dog.setGender((String) row.get("gender"));
            dog.setSpecialty((String) row.get("specialty"));
            dog.setUserId(userRepository.findById((long) row.get("user_id")));
            dog.setBreedId(findBreedById((long) row.get("breed_id")));
            dogs.add(dog);
        }
        return dogs;

    }

    @SuppressWarnings("rawtypes")
    public List<Breed> findAllBreed() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        ArrayList<Breed> breeds = new ArrayList<>();
        String query = "SELECT * FROM breed";
        List<Map<String, Object>> rows = dataSource.queryForList(query, parameters);
        for (Map row : rows) {
            Breed breed = new Breed();
            breed.setId((long) row.get("id"));
            breed.setName((String) row.get("name"));
            breeds.add(breed);
        }
        return breeds;

    }

    public void addUpdateDog(Dog dog) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = null;
        if (dog.getId() > 0) {
            query = "UPDATE dog SET name= :name, gender= :gender, specialty= :specialty,  breed_id= :breedId where id= :id ";
        } else {
            query = "INSERT INTO dog (name, gender, specialty,user_id,breed_id) VALUES ( :name, :gender, :specialty , :userId, :breedId)";
        }
        if (dog.getId() > 0) {
            parameters.addValue("id", dog.getId());
        }
        parameters.addValue("name", dog.getName());
        parameters.addValue("specialty", dog.getSpecialty());
        parameters.addValue("gender", dog.getGender());
        if (dog.getId() < 1) {
            parameters.addValue("userId", dog.getUserId().getId());
        }
        parameters.addValue("breedId", dog.getBreedId().getId());
        dataSource.update(query, parameters);

    }

    @SuppressWarnings("rawtypes")
    public Dog findDogById(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        List<Map<String, Object>> rows = dataSource.queryForList("SELECT * FROM dog WHERE id = :id ", parameters);
        Dog dog = new Dog();
        for (Map row : rows) {
            dog.setId((long) row.get("id"));
            dog.setName((String) row.get("name"));
            dog.setGender((String) row.get("gender"));
            dog.setSpecialty((String) row.get("specialty"));
            dog.setUserId(userRepository.findById((long) row.get("user_id")));
            dog.setBreedId(findBreedById((long) row.get("breed_id")));
        }
        return dog;

    }

    @SuppressWarnings("rawtypes")
    public Breed findBreedById(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        List<Map<String, Object>> rows = dataSource.queryForList("SELECT * FROM breed WHERE id = :id ", parameters);
        Breed breed = new Breed();
        for (Map row : rows) {
            breed.setId((long) row.get("id"));
            breed.setName((String) row.get("name"));
        }

        return breed;

    }

    public void deleteDogById(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        dataSource.update("delete FROM dog WHERE id = :id ", parameters);
    }

    public CountResult totleDogsByBreedId(long id , String name) {
        String query = "SELECT a.breed_id," +
                "(SELECT COUNT(*) FROM dog WHERE gender='male' and breed_id = :breedId and  specialty = 'Class') as classMaleCount," +
                "(SELECT COUNT(*) FROM dog WHERE gender='female' and breed_id = :breedId and specialty = 'Class') as classFemaleCount," +
                "(SELECT COUNT(*) FROM dog WHERE gender='male' and breed_id = :breedId and  specialty = 'Specialty') as specialtyMaleCount," +
                "(SELECT COUNT(*) FROM dog WHERE gender='female' and breed_id = :breedId and specialty = 'Specialty') as specialtyFemaleCount," +
                "(SELECT COUNT(*) FROM dog WHERE breed_id= :breedId) as total " +
                "FROM (SELECT DISTINCT breed_id FROM dog) a";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("breedId", id);
        var rows = dataSource.queryForList(query, parameters);
        CountResult countResult = new CountResult();
        if (rows.size() > 0) {
            for (Map row : rows) {
                 countResult.setTotal((long) row.get("total"));
                countResult.setClassMaleCount((long) row.get("classMaleCount"));
                countResult.setClassFemaleCount((long) row.get("classFemaleCount"));
                countResult.setSpecialtyMaleCount((long) row.get("specialtyMaleCount"));
                countResult.setSpecialtyFemaleCount((long) row.get("specialtyFemaleCount"));
                countResult.setName(name);
            }
        } else {
            countResult.setTotal(0L);
            countResult.setClassMaleCount(0L);
            countResult.setClassFemaleCount(0L);
            countResult.setSpecialtyMaleCount(0L);
            countResult.setSpecialtyFemaleCount(0L);
            countResult.setName(name);
        }
        return countResult;
    }

    @SuppressWarnings("rawtypes")
    public Long totleDogsByBreedIdAndGender(long id, String gender) {
        String query = "SELECT COUNT(*) AS count FROM dog WHERE breed_id= :id AND gender= :gender";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        parameters.addValue("gender", gender);
        var rows = dataSource.queryForList(query, parameters);
        Long count = null;
        for (Map row : rows) {
            count = (long) row.get("count");
        }
        return count;
    }

    @SuppressWarnings("rawtypes")
    public Long totleDogsByBreedIdAndSpecialty(long id, String specialty) {
        String query = "SELECT COUNT(*) AS count FROM dog WHERE breed_id= :id AND specialty= :specialty";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        parameters.addValue("specialty", specialty);
        var rows = dataSource.queryForList(query, parameters);
        Long count = null;
        for (Map row : rows) {
            count = (long) row.get("count");
        }
        return count;
    }
}
