package com.example.personapi.service;

import com.example.personapi.model.Movie;
import com.example.personapi.model.Person;
import com.example.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public List<Person> getAllPersons() {
        return repository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return repository.findById(id);
    }

    public List<Person> getPersonsByName(String name) {
        return repository.findByName(name);
    }

    public Person createPerson(Person person) {
        return repository.save(person);
    }

    public void updatePerson(Long id, Person updatedPerson) {
        Optional<Person> existingPersonOpt = getPersonById(id);
        if (existingPersonOpt.isPresent()) {
            Person existingPerson = existingPersonOpt.get();

            // Actualizar los campos si se pasan en el request
            if (updatedPerson.getFirstName() != null) {
                existingPerson.setFirstName(updatedPerson.getFirstName());
            }
            if (updatedPerson.getLastName() != null) {
                existingPerson.setLastName(updatedPerson.getLastName());
            }
            if (updatedPerson.getBirthdate() != null) {
                existingPerson.setBirthdate(updatedPerson.getBirthdate());
            }
            if (updatedPerson.getHasInsurance() != null) {
                existingPerson.setHasInsurance(updatedPerson.getHasInsurance());
            }
            // Actualiza la lista de películas si es necesario
            // ...

            repository.save(existingPerson);
        } else {
            throw new RuntimeException("Person not found");
        }
    }

    public void deletePerson(Long id) {
        repository.delete(id);
    }

    // Métodos para manejar películas
    public List<Movie> getFavouriteMovies(Long personId) {
        return getPersonById(personId)
                .map(Person::getFavouriteMovies)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public void addFavouriteMovie(Long personId, Movie movie) {
        Person person = getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // Verificar el número máximo de películas si es necesario
        if (person.getFavouriteMovies().size() >= 5) {
            throw new RuntimeException("Maximum number of movies reached");
        }

        person.getFavouriteMovies().add(movie);
        repository.save(person);
    }

    public void removeFavouriteMovie(Long personId, String movieTitle) {
        Person person = getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.getFavouriteMovies().removeIf(movie -> movie.getTitle().equals(movieTitle));
        repository.save(person);
    }
}
