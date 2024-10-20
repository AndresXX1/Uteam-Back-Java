package com.example.personapi.repository;

import com.example.personapi.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {
    private final List<Person> persons = new ArrayList<>();
    private Long currentId = 1L;

    public List<Person> findAll() {
        return persons.stream()
                .sorted(Comparator.comparing(Person::getLastName)
                        .thenComparing(Person::getFirstName))
                .toList();
    }

    public Optional<Person> findById(Long id) {
        return persons.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    public List<Person> findByName(String name) {
        return persons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(name) ||
                        person.getLastName().equalsIgnoreCase(name))
                .toList();
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(currentId++);
        } else {
            // Update existing person logic can be handled here if needed
        }
        persons.add(person);
        return person;
    }

    public void delete(Long id) {
        persons.removeIf(person -> person.getId().equals(id));
    }

    public void update(Long id, Person updatedPerson) {
        findById(id).ifPresent(person -> {
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setBirthdate(updatedPerson.getBirthdate());
            person.setHasInsurance(updatedPerson.getHasInsurance()); // Cambia esto
            person.setFavouriteMovies(updatedPerson.getFavouriteMovies());
        });

    }
}
