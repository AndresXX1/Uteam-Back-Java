package com.example.personapi.Controller;

import com.example.personapi.model.Person;
import com.example.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    public ResponseEntity<?> getAllPersons() {
        List<Person> persons = service.getAllPersons();
        if (persons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Todavía no hay personas creadas.");
        }
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPersonById(@PathVariable Long id) {
        Optional<Person> person = service.getPersonById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se encontró una persona con ID " + id);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Person>> getPersonsByName(@RequestParam String name) {
        List<Person> persons = service.getPersonsByName(name);
        return ResponseEntity.ok(persons);
    }

    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        if (person.getFirstName() == null || person.getFirstName().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'first-name' es obligatorio");
        }
        if (person.getLastName() == null || person.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'last-name' es obligatorio");
        }

        Person createdPerson = service.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/persons/" + createdPerson.getId())
                .body("Persona creada con éxito. ID: " + createdPerson.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (!service.getPersonById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede actualizar una persona que no existe");
        }

        service.updatePerson(id, person);
        return ResponseEntity.ok("Persona actualizada con éxito. ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        if (!service.getPersonById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar una persona que no existe");
        }

        service.deletePerson(id);
        return ResponseEntity.ok("Persona eliminada con éxito. ID: " + id);
    }
}
