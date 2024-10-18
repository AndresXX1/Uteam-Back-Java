package com.example.personapi.Controller;

import com.example.personapi.model.Movie;
import com.example.personapi.model.Person;
import com.example.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons/{personId}/movies")
public class MovieController {
    @Autowired
    private PersonService service;

    private static final int MAX_MOVIES = 5;

    @GetMapping
    public List<Movie> getMovies(@PathVariable Long personId) {
        return service.getPersonById(personId)
                .map(Person::getFavouriteMovies)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @PostMapping
    public ResponseEntity<Void> addMovie(@PathVariable Long personId, @RequestBody Movie movie) {
        Person person = service.getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // verifica si la persona ya tiene el número máximo de películas
        if (person.getFavouriteMovies().size() >= MAX_MOVIES) {
            return ResponseEntity.status(400).build();
        }

        // agrega la nueva película a la lista de películas favoritas
        person.getFavouriteMovies().add(movie);
        service.updatePerson(personId, person);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long personId, @PathVariable String movieTitle) {
        Person person = service.getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // elimina la película especificada
        person.getFavouriteMovies().removeIf(movie -> movie.getTitle().equals(movieTitle));
        service.updatePerson(personId, person); // actualiza la persona en el servicio
        return ResponseEntity.noContent().build(); // retorna un 204 No Content
    }
}
