package com.example.personapi.Controller;

import com.example.personapi.model.Movie;
import com.example.personapi.model.Person;
import com.example.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons/{personId}/movies")
public class MovieController {

    @Autowired
    private PersonService personService;

    private static final int MAX_MOVIES = 5;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(@PathVariable Long personId) {
        List<Movie> movies = personService.getPersonById(personId)
                .map(Person::getFavouriteMovies)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<String> addMovie(@PathVariable Long personId, @RequestBody Movie newMovie) {
        Person person = personService.getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        if (person.getFavouriteMovies().size() >= MAX_MOVIES) {
            return ResponseEntity.status(400).body("La persona ya tiene el número máximo de películas.");
        }

        // Validar propiedades del objeto Movie
        if (newMovie.getTitle() == null || newMovie.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("La propiedad 'title' es obligatoria.");
        }
        if (newMovie.getGenre() == null || newMovie.getGenre().isEmpty()) {
            return ResponseEntity.badRequest().body("La propiedad 'genre' es obligatoria.");
        }
        if (newMovie.getYear() <= 0) {
            return ResponseEntity.badRequest().body("La propiedad 'year' debe ser un número positivo.");
        }

        person.getFavouriteMovies().add(newMovie);
        personService.updatePerson(personId, person);
        return ResponseEntity.status(201).body("Tu película ha sido agregada con éxito.");
    }

    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<String> removeMovie(@PathVariable Long personId, @PathVariable String movieTitle) {
        Person person = personService.getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        boolean removed = person.getFavouriteMovies().removeIf(movie -> movie.getTitle().equals(movieTitle));
        personService.updatePerson(personId, person);

        if (removed) {
            return ResponseEntity.ok("La película ha sido eliminada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Película no encontrada.");
        }
    }

    @PutMapping("/{movieTitle}")
    public ResponseEntity<String> updateMovie(@PathVariable Long personId, @PathVariable String movieTitle, @RequestBody Movie updatedMovie) {
        Person person = personService.getPersonById(personId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        Optional<Movie> existingMovie = person.getFavouriteMovies().stream()
                .filter(movie -> movie.getTitle().equals(movieTitle))
                .findFirst();

        if (existingMovie.isPresent()) {
            // Validar propiedades del objeto Movie
            if (updatedMovie.getTitle() == null || updatedMovie.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().body("La propiedad 'title' es obligatoria.");
            }
            if (updatedMovie.getGenre() == null || updatedMovie.getGenre().isEmpty()) {
                return ResponseEntity.badRequest().body("La propiedad 'genre' es obligatoria.");
            }
            if (updatedMovie.getYear() <= 0) {
                return ResponseEntity.badRequest().body("La propiedad 'year' debe ser un número positivo.");
            }

            person.getFavouriteMovies().remove(existingMovie.get());
            person.getFavouriteMovies().add(updatedMovie);
            personService.updatePerson(personId, person);
            return ResponseEntity.ok("La película ha sido actualizada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Película no encontrada.");
        }
    }
}
