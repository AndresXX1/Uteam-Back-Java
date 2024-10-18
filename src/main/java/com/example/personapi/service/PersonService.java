package com.example.personapi.service;

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

    public void updatePerson(Long id, Person person) {
        repository.update(id, person);
    }

    public void deletePerson(Long id) {
        repository.delete(id);
    }
}
